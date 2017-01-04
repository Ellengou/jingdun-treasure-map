package com.jd.common.exception;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * 类ErrorCode.java的实现描述：错误代码常量定义类
 * 
 * @author Starty 2015年6月30日 下午2:27:39
 */
public enum ErrorContent implements Serializable {

    /** ----------------------公用异常信息(1000-1999)---------------------- */
    /** 成功 */
    Success("1000", "成功"),
    /** 失败 */
    Failure("1001", "失败"),
    /** 缺少必要参数[{0}] */
    Params_Lost("1002", "缺少必要参数[{0}]"),
    /** 参数[{0}]无效 */
    Params_Invalid("1003", "参数[{0}]无效"),
    /** 数据不存在或已取消 */
    No_Data("1004", "数据不存在或已取消"),
    /** 错误的请求参数 */
    Undefine_Params("1005", "错误的请求参数"),
    /** 操作过期 */
    Operation_expire("1006", "操作过期"),
    /** 无操作权限 */
    No_Permission("1007", "无操作权限"),
    /** 查询失败，没有符合条件的数据 */
    Query_Failured("1008", "查询失败，没有符合条件的数据"),
    /** 查询数据出错 */
    Query_Error("1009", "查询数据出错"),
    /** 系统错误 */
    Sys_Error("1010", "系统错误"),
    /** 数据库操作失败 */
    DB_error("1011", "数据库操作失败"),

    PARM_ERROR("1012", "参数传入异常!"),

    PARM_SUBCODE_ERROR("1014", "交易字码传入错误!请检查!"),

    USER_ACCOUNT_NOT_EXIST("1015", "用户账户不存在!"), USER_NO_DEFAULT_BANKNO("1016", "用户没有默认银行卡号"),
    UNKNOW_CALC_WAY("1017", "未配置该费用计算方式，无法插入流水"), UNKNOW_BANK_INFO("1018", "未查询到银行卡信息"),
    /** Session失效 */
    Session_Invalid("1019", "登录超时，请重新登录"),
    /** 当前用户未登录 */
    User_Not_Login("1020", "当前用户未登录"),
    /** 当前用户未通过实名认证 */
    User_Not_Real_Status("1021", "当前用户未通过实名认证"),
    /** 当前用户未通过实名认证 */
    User_Not_Mobile_Status("1023", "当前用户未通过手机认证"),
    /** 申请金额不能小于0 */
    Apply_Amount_Must_Over_Zero("1022", "申请金额不能小于0"),
    /** 对象没有实现序列化接口 */
    Object_Not_Serializable("1099", "对象没有实现序列化接口"), UPDATE_BY_OTHERS("1100", "该账户正在被其他用户操作！"),

    ACC_FORBIDDEN("3002", "账户被禁用"),
    ACC_LOGOUT("3003", "用户被注销"),
    ACC_NOT_EXIST("3004", "用户账户未开户");

    String code;
    String msg;

    private ErrorContent(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 追加信息
     * 
     * @param msg
     * @return ErrorContent
     */
    public ErrorContent addMsg(String msg) {
        this.setMsg(this.getMsg() + msg);
        return this;
    }

    public String getFullMsg(String... arg) {
        if (null == arg || arg.length == 0) return this.msg;
        else return MessageFormat.format(this.msg, arg);
    }

    public static String getMsgName(String code) {
        for (ErrorContent c : ErrorContent.values()) {
            if (c.getCode().equals(code)) {
                return c.msg;
            }
        }
        return null;
    }

    public static String getTypeName(String msg) {
        for (ErrorContent c : ErrorContent.values()) {
            if (c.getMsg().equals(msg)) {
                return c.code;
            }
        }
        return null;
    }

}
