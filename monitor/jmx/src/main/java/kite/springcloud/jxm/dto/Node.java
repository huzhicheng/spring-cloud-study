package kite.springcloud.jxm.dto;

import lombok.Data;

import java.util.List;

/**
 * Node
 *
 * @author fengzheng
 * @date 2019/7/25
 */
@Data
public class Node {

    private String key;

    private String fullName;

    private String title;

    private String nodeType;

    private List<Node> children;
}
