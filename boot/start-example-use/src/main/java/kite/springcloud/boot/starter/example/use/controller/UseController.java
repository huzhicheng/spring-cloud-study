package kite.springcloud.boot.starter.example.use.controller;

import kite.springcloud.boot.starter.example.KiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UseController
 *
 * @author fengzheng
 * @date 2019/5/20
 */
@RestController
@RequestMapping(value = "use")
public class UseController {

    @Autowired
    private KiteService kiteService;

    @GetMapping(value = "print")
    public void print(){
        kiteService.print();
    }
}
