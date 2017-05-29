package com.vmutter.radaptor.result;

import com.vmutter.radaptor.annotation.Alias;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class StudentBuilder {

    private String name;

    @Alias("lastName")
    private String surName;

    private String grade;

    private Integer age;

}
