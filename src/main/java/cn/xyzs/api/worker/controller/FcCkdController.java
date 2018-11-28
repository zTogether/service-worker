package cn.xyzs.api.worker.controller;

import cn.xyzs.common.pojo.XyClbFcCkdMain;
import cn.xyzs.api.worker.service.FcCkdService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/App/fcCkd")
public class FcCkdController {

    @Resource
    private FcCkdService fcCkdService;

    /**
     * 根据材料分类获取辅材商品
     * @Description:
     * @author: zheng shuai
     * @date: 2018/11/12 10:14
     * @param: [ctrCode, fcType]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/getFcGoodByFcType")
    public Map<String ,Object> getFcGoodByFcType(String ctrCode , String fcType){
        return fcCkdService.getFcGoodByFcType(ctrCode,fcType);
    }

    /**
     * 添加出库单
     * @Description:
     * @author: zheng shuai
     * @date: 2018/11/12 15:31
     * @param: [xyClbFcCkdMain, fcPriceCodeArray, fcQtyArray, fcPriceArray, fcMarkArray]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/addFcCkd")
    public Map<String ,Object> addFcCkd(XyClbFcCkdMain xyClbFcCkdMain  , String[] fcPriceCodeArray , String[] fcQtyArray , String[] fcPriceArray , String[] fcMarkArray){
        return fcCkdService.addFcCkd(xyClbFcCkdMain,fcPriceCodeArray,fcQtyArray,fcPriceArray,fcMarkArray);
    }

    /**
     * 获取退库商品
     * @Description:
     * @author: zheng shuai
     * @date: 2018/11/12 18:14
     * @param: [ctrCode, ckdFcType]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/getTKFcGood")
    public Map<String ,Object> getTKFcGood(String ctrCode ,String ckdFcType){
        return fcCkdService.getTKFcGood(ctrCode,ckdFcType);
    }

    /** 添加出库单
     * @Description:
     * @author: zheng shuai
     * @date: 2018/11/12 15:31
     * @param: [xyClbFcCkdMain, fcPriceCodeArray, fcQtyArray, fcPriceArray, fcMarkArray]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/addFcTkd")
    public Map<String ,Object> addFcTkd(XyClbFcCkdMain xyClbFcCkdMain  ,String[] fcPriceCodeArray ,String[] fcQtyArray ,String[] fcPriceArray ){
        return fcCkdService.addFcTkd(xyClbFcCkdMain,fcPriceCodeArray,fcQtyArray,fcPriceArray);
    }

    /**
     * 根据ctrCode获取当前的报价单金额与出库单金额
     * @Description:
     * @author: zheng shuai
     * @date: 2018/11/14 12:03
     * @param: [ctrCode]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/getNowBjdjeAndCldjeByCtrCode")
    public Map<String ,Object> getNowBjdjeAndCldjeByCtrCode(String ctrCode){
        return fcCkdService.getNowBjdjeAndCldjeByCtrCode(ctrCode);
    }

    /**
     * 根据ckdCode删除出库单
     * @Description:
     * @author: zheng shuai
     * @date: 2018/11/14 14:06
     * @param: [ckdCode]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/deleteCkdByCkdCode")
    public Map<String ,Object> deleteCkdByCkdCode(String ckdCode){
        return fcCkdService.deleteCkdByCkdCode(ckdCode);
    }

    /**
     * 判断是否为首次开单
     * @Description:
     * @author: zheng shuai
     * @date: 2018/11/15 15:17
     * @param: [ctrCode, ckdFcType]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/isFristKd")
    public Map<String ,Object> isFristKd(String ctrCode ,String ckdFcType){
        return fcCkdService.isFristKd(ctrCode,ckdFcType);
    }

    /**
     * 获取本材料大类所选择的品牌
     * @Description:
     * @author: zheng shuai
     * @date: 2018/11/15 16:34
     * @param: [ctrCode, rgStage]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/getNameAndVal")
    public Map<String ,Object> getNameAndVal(String ctrCode ,String rgStage){
        return fcCkdService.getNameAndVal(ctrCode,rgStage);
    }

    /**
     * 一键开单
     * @Description:
     * @author: zheng shuai
     * @date: 2018/11/16 16:51
     * @param: [xyClbFcCkdMain]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/autoOpenOrder")
    public Map<String ,Object> autoOpenOrder(XyClbFcCkdMain xyClbFcCkdMain){
        return fcCkdService.autoOpenOrder(xyClbFcCkdMain);
    }
}
