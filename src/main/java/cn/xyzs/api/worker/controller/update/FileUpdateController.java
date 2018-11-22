package cn.xyzs.api.worker.controller.update;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pack")
public class FileUpdateController {

    @RequestMapping("update.do")
    public String index(){
        return "1.0";
    };
}
