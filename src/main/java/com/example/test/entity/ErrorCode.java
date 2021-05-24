package com.example.test.entity;

public enum ErrorCode {


	/***************************** 公共返回码 *****************************/
	/**
	 * 枚举SUCCESS：resultCode： 00000，resultDesc：成功，status：0
	 */
	SUCCESS("00000", "成功", 0),
	/**
	 * 枚举SYSTEM_ERROR：resultCode： 99999，resultDesc：系统内部异常，status：-1
	 */
	SYSTEM_ERROR("99999", "系统忙", -1),

	/***************************** 数据验证错误 *****************************/
	/**
	 * 枚举UNLAWFUL_PERMISSION_OPERATION：resultCode：10001，resultDesc：非法操作，status：1
	 */
	UNLAWFUL_PERMISSION_OPERATION("10001", "非法操作", 1),
	/**
	 * 枚举PARAM_EMPTY：resultCode： 10002，resultDesc：接口参数为空，status：1
	 */
	PARAM_EMPTY("10002", "接口参数为空", 1),
	/**
	 * 枚举PARAM_ERROR：resultCode： 10003，resultDesc：接口参数错误，status：1
	 */
	PARAM_ERROR("10003", "接口参数错误", 1),
	/**
	 * 枚举DATA_DUPLICATION：resultCode： 10004，resultDesc：数据重复，status：1
	 */
	DATA_DUPLICATION("10004", "数据重复", 1),

	/***************************** 业务错误 *****************************/

	ORDER_TYPE_EMPTY("20001", "订单类型不能为空", 1),

	ORDER_TYPE_NOT_CONFIG("20002","该订单类型未配置分润规则", 1),

	DATA_ERROR("20003","数据不完整", 1),

	MONEY_NOT_ENOUGH("20004","账户余额不足", 1),

	RESULT_IS_EMPTY("20005","查询结果数据为空", 1);

	

	private String resultCode;
	private String resultDesc;
	private Integer status;

	ErrorCode(String resultCode, String resultDesc, Integer status) {
		this.resultCode = resultCode;
		this.resultDesc = resultDesc;
		this.status = status;
	}

	public String getResultCode() {
		return resultCode;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public boolean equalsCode(String code) {
		return this.resultCode.equals(code);
	}
}
