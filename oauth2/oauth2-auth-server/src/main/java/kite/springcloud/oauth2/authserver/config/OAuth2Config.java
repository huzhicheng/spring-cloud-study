package kite.springcloud.oauth2.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.JdbcClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailsService kiteUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

//    @Autowired
//    private TokenStore redisTokenStore;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private TokenStore jwtTokenStore;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private TokenEnhancer jwtTokenEnhancer;

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        /**
         * 普通 jwt 模式
         */
//         endpoints.tokenStore(jwtTokenStore)
//                .accessTokenConverter(jwtAccessTokenConverter)
//                .userDetailsService(kiteUserDetailsService)
//                /**
//                 * 支持 password 模式
//                 */
//                .authenticationManager(authenticationManager);

        /**
         * jwt 增强模式
         */
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancerList = new ArrayList<>();
        enhancerList.add(jwtTokenEnhancer);
        enhancerList.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(enhancerList);
        endpoints.tokenStore(jwtTokenStore)
                .userDetailsService(kiteUserDetailsService)
                /**
                 * 支持 password 模式
                 */
                .authenticationManager(authenticationManager)
                .tokenEnhancer(enhancerChain)
                .accessTokenConverter(jwtAccessTokenConverter);

        /**
         * redis token 方式
         */
//        endpoints.authenticationManager(authenticationManager)
//                .tokenStore(redisTokenStore)
//                .userDetailsService(kiteUserDetailsService);

    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);

//        clients.inMemory()
//                .withClient("order-client")
//                .secret(passwordEncoder.encode("order-secret-8888"))
//                .authorizedGrantTypes("refresh_token", "authorization_code", "password")
//                .accessTokenValiditySeconds(3600)
//                .scopes("all")
//                .and()
//                .withClient("user-client")
//                .secret(passwordEncoder.encode("user-secret-8888"))
//                .authorizedGrantTypes("refresh_token", "authorization_code", "password")
//                .accessTokenValiditySeconds(3600)
//                .scopes("all");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
        security.checkTokenAccess("isAuthenticated()");
        security.tokenKeyAccess("isAuthenticated()");
    }
}