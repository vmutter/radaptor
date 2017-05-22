package com.vmutter.result;

import com.vmutter.annotation.Alias;
import lombok.Data;

@Data
public class PersonAdapt {

    private String name;

    @Alias("lastName")
    private String surName;

}
