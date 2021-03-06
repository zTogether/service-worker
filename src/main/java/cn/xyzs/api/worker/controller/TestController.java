package cn.xyzs.api.worker.controller;

import cn.xyzs.common.pojo.TRole;
import cn.xyzs.api.worker.service.RoleService;
import cn.xyzs.api.worker.service.UserService;
import com.github.pagehelper.PageInfo;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/App")
public class TestController {

    private static final String FAR_SERVICE_DIR = "http://127.0.0.1/xyzs";//远程服务器接受文件的路由
    private static final long yourMaxRequestSize = 10000000;


    private String port;

    @Resource
    private RoleService roleService;
    @Resource
    private UserService userService;
    @RequestMapping("index.do")
    public PageInfo<TRole> index(){
        return roleService.getRole();
    }
    @RequestMapping("index1.do")
    public String index1(){
        return "aa:";
    }
    @RequestMapping("roles.do")
    public PageInfo<Map<String,Object>> getRoles(){
        return  roleService.getMap();
    }

    @RequestMapping("users.do")
    public PageInfo<Map<String,Object>> getUsers(@RequestParam Map<String,Object> map){
        return userService.getUsers(1,10,map);
    }

    @RequestMapping("upload.do")
    public String uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws  Exception{
        Map<String,String> map = new HashMap<String,String>();
        map.put("a","b");
        return "success";
    }


}
