package cn.xyzs.api.worker.controller;

import cn.xyzs.api.worker.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/App/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    /**
     * 保存常用菜单
     * @Description:
     * @author: zheng shuai
     * @date: 2018/8/18 15:05
     * @param: [userId, roleId, compoIds]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/editCommoMenu")
    public Map<String, Object> editCommoMenu(String userId,String roleId,String[] compoIds){
        return menuService.editCommoMenu(userId,roleId,compoIds);
    }

    /**
     * 获取常用菜单
     * @Description:
     * @author: zheng shuai
     * @date: 2018/8/18 15:05
     * @param: [userId, roleId]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/getCommoMenu")
    public Map<String, Object> getCommoMenu(String userId,String roleId){
        return menuService.getCommoMenu(userId,roleId);
    }

    /**
     * 添加常用菜单
     * @Description:
     * @author: zheng shuai
     * @date: 2018/8/18 15:06
     * @param: [userId, roleId, compoIds]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/addCommoMenu")
    public Map<String, Object> addCommoMenu(String userId,String roleId,String[] compoIds){
        return menuService.addCommoMenu(userId,roleId,compoIds);
    }

    /***
     *
     * @Description: 获取工人信息
     * @author: GeWeiliang
     * @date: 2018\9\8 0008 14:58
     * @param: [grTel]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/getGrInfo")
    public Map<String,Object> getGrInfo(String grTel){
        return menuService.getGrInfo(grTel);
    }

    /***
     *
     * @Description: 修改工人密码
     * @author: GeWeiliang
     * @date: 2018\9\14 0014 17:37
     * @param: [grTel, password]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/changeGrPassword")
    public  Map<String,Object> changeGrPassword(String grTel,String password){
        return menuService.changeGrPassword(grTel,password);
    }

    /**
     *
     * @Description: 修改工人信息
     * @author: GeWeiliang
     * @date: 2018\9\14 0014 17:38
     * @param: [name, idCard, grTel, grBankId, grAdd, grId]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @ResponseBody
    @RequestMapping("/changeGrInfo")
    public Map<String,Object> updateGrInfo(String name,String idCard, String grTel, String grBankId,
                                           String grAdd,String grId){
        return menuService.updateGrInfo(name,idCard,grTel,grBankId,grAdd,grId);
    }
}
