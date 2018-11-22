package cn.xyzs.api.worker.service;

import cn.xyzs.api.worker.mapper.XyStarEvaMainMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class XyStarEvaMainService {
    @Resource
    private XyStarEvaMainMapper xyStarEvaMainMapper;

    public Map<String,Object> addStarEva(String evaNo,String evaType,String level,String evaluation,String evaName){
        Map<String,Object> resultMap = new HashMap<>();
        String msg = "系统异常";
        String code = "500";
        try{
            xyStarEvaMainMapper.addEvaMain(evaNo,evaType);
            xyStarEvaMainMapper.addEvaList(evaNo,level,evaluation,evaName);
            code = "200";
            msg = "成功";
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            resultMap.put("code",code);
            resultMap.put("msg",msg);
        }
        return resultMap;
    }
}
