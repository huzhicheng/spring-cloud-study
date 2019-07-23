package kite.springcloud.jxm.custom;


/**
 * UserMBean
 *
 * @author fengzheng
 * @date 2019/6/25
 */
public interface UserMBean {

    String getName();

    String getPassword();

    String getPhone();

    void say();
}
