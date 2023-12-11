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
public class articleVo {

    @ApiModelProperty(value = "范文库编码")
    private String fanwenkuId;

    @ApiModelProperty(value = "内容")
    private String content;

}
