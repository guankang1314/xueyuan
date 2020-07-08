package com.guli.teacher.entity.query;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TeacherQuery implements Serializable {

    private String name;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "创建时间", example = "2019-01-01 10:10:10")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间", example = "2019-12-01 10:10:10")
    private Date gmtModified;

}
