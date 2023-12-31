package com.metting.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class englishWritingException extends RuntimeException {

    @ApiModelProperty(value = "状态码")
    private Integer code;

    private String msg;
}
