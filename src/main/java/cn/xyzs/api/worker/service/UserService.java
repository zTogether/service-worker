package cn.xyzs.api.worker.service;

import cn.xyzs.api.worker.mapper.UserMapper;
import cn.xyzs.common.util.MD5Util;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    public PageInfo<Map<String,Object>> getUsers(int index,int size,Map<String,Object> map){
        PageHelper.startPage(index,size);
        List<Map<String,Object>> users = userMapper.selectByCondition(map);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(users);
        return pageInfo;
    }
    /***
     *
     * @Description:修改密码
     * @author: GeWeiliang
     * @date: 2018\8\16 0016 14:37
     * @param:
     * @return:
     */
    public Map<String,Object> resetPassword(String userTel,String password){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        String code = "500";
        String msg = "系统异常";
        try {
            userMapper.changePassword(userTel, MD5Util.md5Password(password));
            code = "200";
            msg = "修改成功";
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            resultMap.put("code",code);
            resultMap.put("msg",msg);
        }
        return resultMap;
    }

    /**
     *
     * @Description:修改个人资料
     * @author: GeWeiliang
     * @date: 2018\8\22 0022 9:55
     * @param: [userTel, userSex, userBthd, idCard, bankIdBc, bankIdIcbc, bankIdCmbc]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> changePersonalInfo(String userCode,String userTel,String userSex, String userBthd,String idCard,
                                                 String bankIdBc, String bankIdIcbc, String bankIdCmbc){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        String code = "500";
        String msg = "修改失败";
        try{
            userMapper.changePersonalInfo(userCode,userTel,userSex,userBthd,idCard,bankIdBc,bankIdIcbc,bankIdCmbc);
            code = "200";
            msg = "修改成功";
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            resultMap.put("code",code);
            resultMap.put("msg",msg);
        }

        return resultMap;
    }

    public Map<String,Object> getUserInfo(String userTel){
         Map<String,Object> map = userMapper.getUserInfo(userTel);
         return map;
    }
}
