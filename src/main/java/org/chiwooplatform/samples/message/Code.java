package org.chiwooplatform.samples.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;
import lombok.Data;

@ApiModel(value = "Code Schema", description = "Code is model of code")
@Data
public class Code {

    @ApiModelProperty(value = "code id", example = "1", required = true)
    private Integer cdid;

    @ApiModelProperty(value = "parent code id", example = "101", accessMode = AccessMode.READ_ONLY, required = true)
    private Integer parentId;

    @ApiModelProperty(value = "code name", example = "System environment")
    private String cdname;

    @ApiModelProperty(value = "code description", example = "description for system environment", required = true)
    private String descr;

    @ApiModelProperty(value = "enable or not", example = "false", required = true)
    private Boolean enabled;

    @ApiModelProperty(value = "timestamp for registration", example = "8678463244")
    private Long regdtm;

}
