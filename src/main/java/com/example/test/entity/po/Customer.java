package com.example.test.entity.po;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.test.entity.RespPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author jie
 * @since 2021-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_customer")
@ApiModel(value="Customer对象", description="用户表")
public class Customer implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String name;

    private String phone;

    @ApiModelProperty(value = "电话区号")
    private String phoneAreaCode;

    @ApiModelProperty(value = "用户头像")
    private String img;

    @ApiModelProperty(value = "昵称修改剩余次数")
    private Integer nameModifyLimit;

    @ApiModelProperty(value = "用户状态(0.正常，1.冻结)")
    private String status;

    private String authStatus;

    @ApiModelProperty(value = "登录方式，wx：微信or微信小程序，phone：手机，qq：腾讯qq")
    private String loginWay;

    @ApiModelProperty(value = "绑定第三方，0未绑定，1微信，2QQ，3微信+QQ")
    private Integer bindThird;

    private String type;

    @ApiModelProperty(value = "网易账号accid")
    private String wyAccid;

    @ApiModelProperty(value = "网易IM账号密码")
    private String wyToken;

    @ApiModelProperty(value = "邀请人ID")
    private Integer recommenderId;

    @ApiModelProperty(value = "邀请码")
    private String invitationCode;

    @ApiModelProperty(value = "设备imei")
    private String imei;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "手机号绑定时间")
    private LocalDateTime phoneBindTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "设备追踪标识（ios: idfa）")
    private String deviceTraceId;

    @ApiModelProperty(value = "是否接受推荐（0:是，1:否）")
    private Integer isAcceptRecommend;

    @ApiModelProperty(value = "数美用户风险等级")
    private String riskLevel;

    @ApiModelProperty(value = "数美设备指纹标识")
    private String smDeviceId;

    @ApiModelProperty(value = "用户当前APP版本")
    private String appVersion;

    @ApiModelProperty(value = "是否支付过 0-否 1-是")
    private Integer isHasPayed;

    @ApiModelProperty(value = "是否是海外app")
    private Integer appSource;


    public static void main(String[] args) {
        List list = new ArrayList<>();
        final ArrayList arrayList = new ArrayList();
        arrayList.add(new RespPage<>());
        System.out.println(JSON.toJSONString(arrayList));


    }
}
