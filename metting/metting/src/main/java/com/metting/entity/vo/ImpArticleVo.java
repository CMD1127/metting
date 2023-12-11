package com.metting.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author wcc
 * @since 2023-09-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ImpArticle对象", description="")
public class ImpArticleVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "待改进文章名称")
    private String impArticleName;
    @ApiModelProperty(value = "文章内容")
    private String content;



}
