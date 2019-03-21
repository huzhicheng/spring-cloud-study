package kite.springcloud.consul.customer.controller;

import kite.springcloud.consul.customer.service.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * ConsumerController
 *
 * @author fengzheng
 * @date 2018/9/25
 */
@RestController
public class ConsumerController {

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private DiscoveryClient discoveryClient;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IHelloService helloService;

    private final static String SERVICE_NAME = "consul-provider";


    /**
     * 使用普通的 RestTemplate 方法访问服务
     *
     * @return
     */
    @GetMapping(value = "test")
    public Object test() {
        String result = restTemplate.getForObject("http://"+SERVICE_NAME + "/test", String.class);
        System.out.println(result);
        return result;
    }

    /**
     * 使用 openFeign 方式访问服务
     *
     * @return
     */
    @GetMapping(value = "feign")
    public Object feign() {
        String s = helloService.nice();
        return s;
    }


    /**
     * 获取所有服务实例
     *
     * @return
     */
    @GetMapping(value = "/services")
    public Object services() {
        return discoveryClient.getInstances(SERVICE_NAME);
    }

    /**
     * 从所有服务中选择一个服务（轮询）
     */
    @GetMapping(value = "/choose")
    public Object choose() {
        return loadBalancer.choose(SERVICE_NAME).getUri().toString();
    }

}
