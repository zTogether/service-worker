package cn.xyzs.api.worker.controller.grab;

import cn.xyzs.api.worker.service.grab.QdService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/App/grab")
public class QdController {
    @Resource
    private QdService qdService;

    @RequestMapping("test.do")
    public Map<String,Object> test(String grId, String pgId, String endDate, String ctrCode){
        Map<String,Object> result = new HashMap<>();
        try {
            qdService.planSheet(grId,pgId,endDate,ctrCode);
            result.put("code","200");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("msg",e.getMessage());
        }
        return result;
    }
}
