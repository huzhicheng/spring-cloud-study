package kite.springcloud.gateway;


import kite.springcloud.gateway.config.IpResolver;
import kite.springcloud.gateway.config.filter.CustomerFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author fengzheng
 * @date 2019-08-01
 * gateway 启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Autowired
//    private KeyResolver ipResolver;

    @Bean
    public RouteLocator kiteRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("userRouter", r -> r.path("/user-service/**")
                        .filters(f ->
                                f.addResponseHeader("X-CustomerHeader", "kite"))
                        .uri("lb://consul-user")
                )
                .route("orderRouter", r -> r.path("/order-service/**")
                        .filters(f -> f.stripPrefix(1)
                        )
                        .uri("lb://consul-order")
                )
                .route("hystrixRouter", r -> r.path("/hystrix/**")
                        .filters(f -> f.stripPrefix(1)
                                .hystrix(h -> h.setName("second2Command").setFallbackUri("forward:/hystrixfallback"))
                        )
                        .uri("lb://consul-user")
                )
                .route("limit_route", r -> r.path("/limiter/**")
                        .filters(f -> f.stripPrefix(1)
                                .requestRateLimiter(
                                        c -> c.setKeyResolver(ipResolver())
                                                .setRateLimiter(redisRateLimiter())
                                )
                        )
                        .uri("lb://consul-user"))
                .route("oauth_server", r -> r.path("/oauth-service/**")
                        .filters(f -> f.filter(new CustomerFilter()))
                        .uri("http://localhost:5010"))
                .route("oauth_client", r -> r.path("/consul-oauth-client/**")
                        .uri("http://localhost:5012"))
                .build();
    }

    @Bean
    IpResolver ipResolver() {
        return new IpResolver();
    }

    @Bean
    RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(1, 1);
    }


    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
        return http.csrf().disable().authorizeExchange()
                .anyExchange().permitAll()
                .and()
                .build();
//        return http.httpBasic().and()
//                .csrf().disable()
//                .authorizeExchange()
//                .pathMatchers("/limiter/**").authenticated()
//                .anyExchange().permitAll()
//                .and()
//                .build();
    }

    @Bean
    public MapReactiveUserDetailsService reactiveUserDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build();
        return new MapReactiveUserDetailsService(user);
    }
}
