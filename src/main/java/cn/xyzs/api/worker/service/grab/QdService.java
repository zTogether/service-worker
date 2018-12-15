package cn.xyzs.api.worker.service.grab;

import cn.xyzs.api.worker.mapper.VwXyPgWaiterMapper;
import cn.xyzs.api.worker.mapper.XyGcbGrxxMapper;
import cn.xyzs.api.worker.mapper.XyPgMapper;
import cn.xyzs.api.worker.mapper.XyPgWaiterMapper;
import cn.xyzs.common.pojo.XyGcbGrxx;
import cn.xyzs.common.pojo.XyPg;
import cn.xyzs.common.pojo.XyPgWaiter;
import com.codingapi.tx.annotation.TxTransaction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class QdService {


    @Resource
    private VwXyPgWaiterMapper vwXyPgWaiterMapper;

    @Resource
    private XyPgWaiterMapper xyPgWaiterMapper;

    @Resource
    private XyPgMapper xyPgMapper;

    @Resource
    private XyGcbGrxxMapper xyGcbGrxxMapper;

    //抢单关键概念

    /**
     * 添加单据
     * 入参：单据详情，工种列表，补贴工资默认0,
     */
    @TxTransaction
    @Transactional
    public void addSheet(){
        //1.新增单据到数据库
        //2.启动定时器 12小时后执行分单
    }

    /**
     * 报名阶段
     * @param userId
     * @param pgId
     */
    @Transactional
    public synchronized String planSheet(String userId,String pgId,String endDate,String ctrCode) throws Exception {
        XyGcbGrxx gcbGrxx = xyGcbGrxxMapper.selectByPrimaryKey(userId);
        XyPg xyPg = xyPgMapper.selectByPrimaryKey(pgId);
        //时间限定
        //单人报名数量判定()
//        gcbGrxx
        //1.根据sheetId，判断报名是否满20人
            //是
            //报名失败，项目已开标
            //否
            //执行步骤2
        XyPgWaiter pgWaiter = new XyPgWaiter();
        pgWaiter.setPgId(pgId);
        pgWaiter.setZt("已报名");
        int num = xyPgWaiterMapper.selectCount(pgWaiter);
        if (num>=20){
            throw new Exception("报名失败，当前项目已开标");
        }
        //2.根据userId，判断是否是特权
            //是
            //抢单成功，特权-1   （结束）
            //否
            //报名成功 （进入步骤3）
        if(gcbGrxx==null){
            throw new Exception("请核实当前工人信息");
        }
        if(gcbGrxx.getGrPriv()>0){
            //减特权数
            int grpriv = gcbGrxx.getGrPriv()-1;
            gcbGrxx = new XyGcbGrxx();
            gcbGrxx.setGrId(userId);
            gcbGrxx.setGrPriv(grpriv);
            xyGcbGrxxMapper.updateByPrimaryKeySelective(gcbGrxx);
            String result = this.update(xyPg,pgId,userId);
            xyPgWaiterMapper.addXyPgWaiterInfo(userId,pgId,endDate,ctrCode,"抢单成功");
            return result;
        }
        //3.判断报名人数是否达到20
        if (num==20){
            //进入派单阶段（调用截单方法，清除定时器）
            userId = this.closeSheet(pgId);
            this.update(xyPg,pgId,userId);
            xyPgWaiterMapper.addXyPgWaiterInfo(userId,pgId,endDate,ctrCode,"抢单成功");
            return "start";
        }
        //报名成功
        xyPgWaiterMapper.addXyPgWaiterInfo(userId,pgId,endDate,ctrCode,"已报名");
        return  "ok";
    }
    /**
     * 截单方法（前提：添加单据12小时，或者报名工人达到20人调用）
     */
    public String closeSheet(String pgId){
            //等级校验
        List<Map<String,Object>> grs = vwXyPgWaiterMapper.getGrs(pgId);
        //调用百度地图API,获取两地之间距离
        String userId = null;
        Double distance = 0D;
        for (Map<String,Object> gr : grs) {
            //获取经度,获取纬度
            //计算距离
            //获取最小距离,赋值userId
            userId = String.valueOf(gr.get("GR_ID"));
        }
        XyGcbGrxx gcbGrxx = xyGcbGrxxMapper.selectByPrimaryKey(userId);
        return userId;
    }
    public String update(XyPg xyPg,String pgId,String userId){
        //修改单据工人
        String pgStage = xyPg.getPgStage();
        xyPg = new XyPg();
        xyPg.setPgId(pgId);
        xyPg.setPgGr(userId);
        xyPg.setPgStage(pgStage);
        xyPgMapper.updateByPrimaryKeySelective(xyPg);
        //修改之前报名的工人状态 抢单失败或抢单成功
        vwXyPgWaiterMapper.checkPg(pgId,userId);
        //删除当前工人的其他报名
        XyPgWaiter pgWaiter = new XyPgWaiter();
        pgWaiter.setZt("已报名");
        pgWaiter.setGrId(userId);
        //非基础、水工中标后清除该工人其他报名的工地
        if("10".equals(xyPg.getPgStage())||"21".equals(xyPg.getPgStage())){
            xyPgWaiterMapper.delete(pgWaiter);
        }else {
            //基础、水工中标后，如果满5家在手清除其他报名工地
            if (xyPgWaiterMapper.getPdCount(pgId) > 4) {
                xyPgWaiterMapper.delete(pgWaiter);
            }
        }
        xyGcbGrxxMapper.addMVlevel(pgId,0.2);
        return "success";
    }


//    @Scheduled(cron = "0 0/2 * * * ?")
//    public void pushDataScheduled(){
//        List<String> tasks = vwXyPgWaiterMapper.checkD();
//        if(tasks==null){
//            return;
//        }
//        for (String pgId:tasks) {
//            this.closeSheet(pgId);
//        }
//        System.err.println("启动定时任务:"+System.currentTimeMillis());
//    }


    /**
     * 评价阶段
     * 入参：工人，单据，星级(默认4星)
     */
    public void assSheet(String userId,String sheetId,String remark,Integer level){
        //判断特权模式
            //是
            //1.判断好评
                //五星
                //工资级别 +0.5
                //4星
                //不变
                //等于或低于3星
                //工资级别 -X，最低限度（0）
            //否
            //2.不变
    }
}
