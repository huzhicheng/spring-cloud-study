package kite.springcloud.jxm.utils;

import lombok.Data;

/**
 * Result
 *
 * @author fengzheng
 * @date 2019/7/17
 */
@Data
public class Result<T> {

    private boolean success;

    private T data;

    private String errorMessage;

    private Result(){}

    public static <T> Result<T> success(){
        Result result = new Result();
        result.success = true;
        return result;
    }

    public static <T> Result<T> success(T data){
        Result result = new Result();
        result.success = true;
        result.data = data;
        return result;
    }

    public static <T> Result<T> fail(String errorMessage){
        Result result = new Result();
        result.errorMessage = errorMessage;
        result.success = false;
        return result;
    }

}
