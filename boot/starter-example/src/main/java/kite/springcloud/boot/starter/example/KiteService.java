package kite.springcloud.boot.starter.example;

import lombok.extern.slf4j.Slf4j;

/**
 * KiteService
 *
 * @author fengzheng
 * @date 2019/5/20
 */
@Slf4j
public class KiteService {

    private String host;

    private int port;

    public KiteService(KiteProperties kiteProperties){
        this.host = kiteProperties.getHost();
        this.port = kiteProperties.getPort();
    }

    public void print(){
        log.info(this.host + ":" +this.port);
    }
}
