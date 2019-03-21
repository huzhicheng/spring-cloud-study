package kite.springcloud.consul.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ConfigController
 *
 * @author fengzheng
 * @date 2019/1/4
 */
@RestController
@Slf4j
public class ConfigController {

    @Autowired
    private MySqlConfig mySqlConfig;

    @Autowired
    private MySqlComplexConfig mySqlComplexConfig;

    @Value("${host}")
    private String host;

    @GetMapping(value = "host")
    public String getHost(){
        return host;
    }

    @GetMapping(value = "mysqlhost")
    public String getMysqlHost(){
        return mySqlConfig.getHost();
    }

    @GetMapping(value = "mysqluser")
    public String getMysqlUser(){
        log.info(mySqlComplexConfig.getHost());
        MySqlComplexConfig.UserInfo userInfo = mySqlComplexConfig.getUser();
        return userInfo.toString();
    }

}
