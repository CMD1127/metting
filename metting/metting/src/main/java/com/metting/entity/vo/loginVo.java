package com.metting.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="用户登录对象", description="")
public class loginVo {
    @ApiModelProperty(value = "账号")
    private String loginName;

    @ApiModelProperty(value = "密码")
    private String password;
}
