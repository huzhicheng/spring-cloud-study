package kite.springcloud.gateway.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * CustomerFilterFactory
 *
 * @author fengzheng
 * @date 2019/11/26
 */
@Slf4j
public class CustomerFilter implements GatewayFilter,Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    HttpHeaders headers = exchange.getRequest().getHeaders();
                    Iterator<Map.Entry<String,List<String>>> iterator = headers.entrySet().iterator();
                    while (iterator.hasNext()){
                        Map.Entry<String,List<String>> entry = iterator.next();
                        log.info(entry.getKey());
                        for(String s : entry.getValue()){
                            log.info(s);
                        }
                    }
                })
        );
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
