package com.example.test.entity;

import java.util.function.Function;

public class ResponseObj<T> {

    /**
     * 操作成功；
     */
    public final static String SUCCESSFUL = "1000";
    /**
     * 操作失败；
     */
    public final static String FAILED = "1001";
    /**
     * 登录失败
     */
    public final static String UNAUTHORIZED = "1002";
    /**
     * HTTP异常
     */
    public final static String HTTP_ERROE = "1003";
    /**
     * token无效
     */
    public final static String INVALID_TOKEN = "1004";
    /**
     * 参数有误
     */
    public final static String INVALID_PARAM = "1005";
    /**
     * 验证手机号-无效手机号
     */
    public final static String INVALID_PHONE = "1006";
    /**
     * 验证手机验证码-无效
     */
    public final static String INVALID_PHONE_CODE = "1007";
    /**
     * 封禁code
     */
    public final static String BAN_USER_CODE = "1008";
    /**
     * 错误的状态
     */
    public final static String ILLEGAL_STATE = "1009";
    /**
     * 未找到
     */
    public final static String NOT_FOUND = "1010";
    /**
     * 重复提交
     */
    public final static String REPEAT_SUBMIT = "1011";
    /**
     * 已签到
     */
    public final static String SIGNED = "1700";
    /**
     * 优惠券已领取
     */
    public final static String COUPON_RECEIVED = "1710";
    /**
     * 无需展示津贴按钮
     */
    public final static String LADDER_NO_DISPLAY = "1720";
    /**
     * 1800 -- 1900 提现错误code
     * 不能提现
     */
    public final static String WITHDRAW_FROZEN = "1800";


    private String code;
    private String message;
    private T data;

    public static <T> ResponseObj<T> success() {
        ResponseObj responseObj = new ResponseObj();
        responseObj.code = SUCCESSFUL;
        return responseObj;
    }

    public static <T> ResponseObj<T> success(T data) {
        ResponseObj responseObj = new ResponseObj();
        responseObj.code = SUCCESSFUL;
        responseObj.data = data;
        return responseObj;
    }

    public static <T> ResponseObj fail() {
        ResponseObj<T> responseObj = new ResponseObj();
        responseObj.code = FAILED;
        return responseObj;
    }

    public static <T> ResponseObj fail(String message) {
        ResponseObj<T> responseObj = new ResponseObj();
        responseObj.code = FAILED;
        responseObj.message = message;
        return responseObj;
    }

    public static <T> ResponseObj fail(String code, String message) {
        ResponseObj<T> responseObj = new ResponseObj();
        responseObj.code = code;
        responseObj.message = message;
        return responseObj;
    }

    public static <T> ResponseObj invalidParam(String message) {
        ResponseObj<T> responseObj = new ResponseObj();
        responseObj.code = INVALID_PARAM;
        responseObj.message = message;
        return responseObj;
    }

    public static <T> ResponseObj illegalState(String message) {
        ResponseObj<T> responseObj = new ResponseObj();
        responseObj.code = ILLEGAL_STATE;
        responseObj.message = message;
        return responseObj;
    }

    public static <T> ResponseObj notFound(String message) {
        ResponseObj<T> responseObj = new ResponseObj();
        responseObj.code = NOT_FOUND;
        responseObj.message = message;
        return responseObj;
    }

    public static <T> ResponseObj unauthorized(String message) {
        ResponseObj<T> responseObj = new ResponseObj();
        responseObj.code = UNAUTHORIZED;
        responseObj.message = message;
        return responseObj;
    }

    public static <T> ResponseObj build(ErrorCode errorCode, T data) {
        ResponseObj<T> responseObj = new ResponseObj();
        responseObj.code = errorCode.getResultCode();
        responseObj.message = errorCode.getResultDesc();
        responseObj.data = data;
        return responseObj;
    }

    public static <T> ResponseObj build(ErrorCode errorCode) {
        ResponseObj<T> responseObj = new ResponseObj();
        responseObj.code = errorCode.getResultCode();
        responseObj.message = errorCode.getResultDesc();
        return responseObj;
    }

    public boolean wasSuccessful() {
        return SUCCESSFUL.equals(code) || ErrorCode.SUCCESS.equalsCode(code);
    }

    public <TT> ResponseObj<TT> transfer() {
        ResponseObj<TT> target = new ResponseObj<>();
        target.setCode(this.code);
        target.setMessage(this.message);
        return target;
    }

    public <TT> ResponseObj<TT> transfer(Class<TT> clazz) {
        ResponseObj<TT> target = transfer();
        T data0;
        if(clazz != null && (data0 = this.data) != null && clazz.isInstance(data0)) {
            target.setData((TT)data0);
        }
        return target;
    }

    public <TT> ResponseObj<TT> transfer(Function<T, TT> delegate) {
        ResponseObj<TT> target = transfer();
        T data0;
        if(delegate != null && (data0 = this.data) != null) {
            target.setData(delegate.apply(data0));
        }
        return target;
    }

    public static <T> ResponseObj<T> copy(ResponseObj<? extends T> source) {
        if(source == null) {
            throw new IllegalArgumentException();
        }
        ResponseObj<T> target = new ResponseObj<>();
        target.setCode(source.getCode());
        target.setMessage(source.getMessage());
        T data = source.getData();
        if(data != null) {
            try {
                target.setData(data);
            } catch(ClassCastException e) {
                //do nothing.
            }
        }
        return target;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
