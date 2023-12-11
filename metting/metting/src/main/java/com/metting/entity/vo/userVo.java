package com.metting.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="条件查询", description="")
public class userVo {

    @ApiModelProperty(value = "账号")
    private String loginName;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "角色类型")
    private Integer role;
}
