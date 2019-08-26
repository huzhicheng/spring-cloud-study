package kite.springcloud.jxm.config;

import lombok.extern.slf4j.Slf4j;
import sun.misc.VMSupport;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * JmxConnectorInstance
 *
 * @author fengzheng
 * @date 2019/7/16
 */
@Slf4j
public enum JmxConnectorInstance {

    INSTANCE;

    private static JMXConnector jmxConnector;

    JmxConnectorInstance(){

    }

    public JMXConnector getJmxConnector(){
        if(jmxConnector!=null){
            return jmxConnector;
        }else{
            buildJmxConnector();
            return jmxConnector;
        }
    }

    private void buildJmxConnector(){
        String addr = (String) VMSupport.getAgentProperties().get("com.sun.management.jmxremote.localConnectorAddress");

        if (addr == null) {
            try {
                sun.management.Agent.premain("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        addr = (String) VMSupport.getAgentProperties().get("com.sun.management.jmxremote.localConnectorAddress");

        try {
            JMXServiceURL jmxServiceURL = new JMXServiceURL(addr);
            JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxServiceURL, null);
            JmxConnectorInstance.jmxConnector = jmxConnector;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
