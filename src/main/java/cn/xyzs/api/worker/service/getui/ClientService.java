package cn.xyzs.api.worker.service.getui;

import cn.xyzs.api.worker.mapper.API.ClientAPIMapper;
import cn.xyzs.api.worker.pojo.API.ClientAPI;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ClientService {
    @Resource
    private ClientAPIMapper clientAPIMapper;


    /**
     * 启动APP(保存clientId)
     */
    public int saveClientId(ClientAPI clientAPI){
        //1.检测设备是否已存在
        ClientAPI clientAPI1 = clientAPIMapper.selectByPrimaryKey(clientAPI.getClientId());
        clientAPI.setLastLoginTime(System.currentTimeMillis());
        //2.如果不存在 直接新增
        if(clientAPI1==null){
            int r = clientAPIMapper.insertSelective(clientAPI);
            return 1;
        }
        //3.如果存在，判断用户
        //4.设备上不存在用户，直接修改
        if(clientAPI1.getUserId()==null){
            int r = clientAPIMapper.updateByPrimaryKeySelective(clientAPI);
            return 1;
        }
        //5.设备上存在用户，检测用户id是否一致
        //6.用户id如果一致,不必修改
        if(clientAPI1.getUserId().equals(clientAPI.getUserId())){
            return 1;
        }
        //7.用户id不一致，强行登陆
        clientAPIMapper.updateByPrimaryKeySelective(clientAPI);
        return 0;
    }


}
