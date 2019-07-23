package kite.springcloud.jxm.controller;

import kite.springcloud.jxm.config.JmxConnectorInstance;
import kite.springcloud.jxm.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import java.util.Arrays;
import java.util.List;

/**
 * MBeanController
 *
 * @author fengzheng
 * @date 2019/7/15
 */
@RestController
@RequestMapping(value = "jmx")
public class MBeanController {

    @GetMapping(value = "beans")
    public Result<List<String>> getBeans() throws Exception{
        JmxConnectorInstance commonConfig = JmxConnectorInstance.INSTANCE;
        JMXConnector connector = commonConfig.getJmxConnector();
        if(connector!=null){
            MBeanServerConnection msc = connector.getMBeanServerConnection();
            String[] domains = msc.getDomains();
            return Result.success(Arrays.asList(domains));
        }
        return Result.success();
    }


}
