package cn.xyzs.api.worker.controller;

import cn.xyzs.api.worker.service.FieldSupervisionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/App/fieldSupervision")
public class FieldSupervisionController {

    @Resource
    private FieldSupervisionService fieldSupervisionService;

    /**
     * 收款情况
     * @Description:
     * @author: zheng shuai
     * @date: 2018/9/22 11:20
     * @param: [ctrCode]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/getAccountCollection")
    public Map<String ,Object> getAccountCollection(String ctrCode){
        return fieldSupervisionService.getAccountCollection(ctrCode);
    }

    /**
     * 获取报价清单
     * @Description:
     * @author: zheng shuai
     * @date: 2018/9/22 14:15
     * @param: [ctrCode]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/getXyBjdList")
    public Map<String ,Object> getXyBjdList(String ctrCode){
        return fieldSupervisionService.getXyBjdList(ctrCode);
    }

    /**
     * 获取工程清单
     * @Description:
     * @author: zheng shuai
     * @date: 2018/9/22 15:37
     * @param: [ctrCode]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/getEngineeringListList")
    public Map<String ,Object> getEngineeringListList(String ctrCode){
        return fieldSupervisionService.getEngineeringListList(ctrCode);
    }

    /**
     * 获取收款明细
     * @Description:
     * @author: zheng shuai
     * @date: 2018/9/22 16:12
     * @param: [ctrCode]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/getSkList")
    public Map<String ,Object> getSkList(String ctrCode){
        return fieldSupervisionService.getSkList(ctrCode);
    }

    /**
     * 获取辅材清单
     * @Description:
     * @author: zheng shuai
     * @date: 2018/9/22 17:01
     * @param: [ctrCode]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/getFcList")
    public Map<String ,Object> getFcList(String ctrCode){
        return fieldSupervisionService.getFcList(ctrCode);
    }

    /**
     * 获取派单明细
     * @Description:
     * @author: zheng shuai
     * @date: 2018/9/23 9:28
     * @param: [pgId]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/getPgList")
    public Map<String ,Object> getPgList(String pgId){
        return fieldSupervisionService.getPgList(pgId);
    }

    /**
     * 获取辅材出库明细
     * @Description:
     * @author: zheng shuai
     * @date: 2018/9/23 10:30
     * @param: [ckdCode]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/getFcCkList")
    public Map<String ,Object> getFcCkList(String ckdCode){
        return fieldSupervisionService.getFcCkList(ckdCode);
    }


    @ResponseBody
    @RequestMapping("/rgList")
    public Map<String,Object> getRgList(String ctrCode,String bjdCode){
        return fieldSupervisionService.getRgList(ctrCode,bjdCode);
    }

    /**
     * 根据项目code获取辅材详情
     * @Description:
     * @author: zheng shuai
     * @date: 2018/9/27 11:09
     * @param: [bjdCode, fcStage, startNum, endNum]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/getBjdFcList")
    public Map<String ,Object> getBjdFcList(String bjdCode, String fcStage, String startNum, String endNum){
        return fieldSupervisionService.getBjdFcList(bjdCode,fcStage,startNum,endNum);
    }

    /**
     * 获取总计
     * @Description:
     * @author: zheng shuai
     * @date: 2018/9/27 12:12
     * @param: [bjdCode, fcStage]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/getZj")
    public Map<String ,Object> getZj(String bjdCode){
        return fieldSupervisionService.getZj(bjdCode);
    }

}
