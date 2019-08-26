package kite.springcloud.jxm.controller;

import kite.springcloud.jxm.dto.BeanAttributeInfo;
import kite.springcloud.jxm.dto.BeanInfo;
import kite.springcloud.jxm.dto.Node;
import kite.springcloud.jxm.service.JmxService;
import kite.springcloud.jxm.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * MBeanController
 *
 * @author fengzheng
 * @date 2019/7/15
 */
@RestController
@RequestMapping(value = "bean")
public class MbeanController {

    @Autowired
    private JmxService jmxService;

    @GetMapping(value = "domains")
    public Result<List<Node>> getDomains() throws Exception {
        List<Node> domains = jmxService.getDomains();
        return Result.success(domains);
    }

    @GetMapping(value = "properties")
    public Result<BeanInfo> getPropertyList(String fullName){
        try {
            BeanInfo beanInfo = jmxService.getObjectNameProperties(fullName);
            return Result.success(beanInfo);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }
    }

    @GetMapping(value = "run")
    public Result<Boolean> runMethod(String fullName,String methodName){
        try {
            jmxService.invokeMethod(fullName,methodName);
            return Result.success();
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }
    }
}
