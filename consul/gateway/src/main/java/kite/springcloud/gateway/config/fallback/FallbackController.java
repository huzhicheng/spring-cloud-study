package kite.springcloud.gateway.config.fallback;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

/**
 * FallbackController
 *
 * @author fengzheng
 * @date 2019/9/26
 */
@RestController
public class FallbackController {

    @RequestMapping("/hystrixfallback")
    public String hystrixfallback() {
        Calendar.getInstance().get(Calendar.MONTH);
        return "已超时，不用等了";
    }
}
