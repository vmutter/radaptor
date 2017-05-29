package com.vmutter.radaptor.result;

import com.vmutter.radaptor.annotation.Alias;
import lombok.Data;

@Data
public class StudentAdapt {

    private String name;

    @Alias("lastName")
    private String surName;

    private String grade;

    private Integer age;

}
