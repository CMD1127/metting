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
public class FanwenkuVo {

    @ApiModelProperty(value = "范文库名称")
    private String fanwenkuName;

}
