package cn.xyzs.api.worker.service;

import cn.xyzs.api.worker.mapper.MvSysConfigMapper;
import cn.xyzs.api.worker.util.PropertiesUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class AppSystemUpdateService {

    @Resource
    private MvSysConfigMapper mvSysConfigMapper;

    /**
     * 获取app当前最新版本
     * @Description:
     * @author: zheng shuai
     * @date: 2018/9/15 15:13
     * @param: []
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String ,Object> getAppSystemVersion(){
        Map<String,Object> resultMap = new HashMap<>();
        Map<String,Object> obj = new HashMap<>();
        String code = "500";
        String msg = "系统异常";
        try{
            String appSystemVersion = PropertiesUtil.getSourcingValueBykey("APP_SYSTEM_VERSION");
            obj.put("appSystemVersion",appSystemVersion);
            code = "200";
            msg = "成功";
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            resultMap.put("code",code);
            resultMap.put("msg",msg);
            resultMap.put("resultData",obj);
        }
        return resultMap;
    }
}
