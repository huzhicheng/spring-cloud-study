package kite.springcloud.common.stream;

import lombok.Data;

import java.util.Date;


/**
 * LogInfo
 *
 * @author fengzheng
 * @date 2018/12/26
 */
@Data
public class LogInfo {

    private String clientVersion;

    private String userId;

    private String clientIp;

    private Date time;

    @Override
    public String toString() {
        return "LogInfo{" +
                "clientVersion='" + clientVersion + '\'' +
                ", userId='" + userId + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", time=" + time +
                '}';
    }
}
