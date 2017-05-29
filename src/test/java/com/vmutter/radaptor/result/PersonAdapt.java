package com.vmutter.radaptor.result;

import com.vmutter.radaptor.annotation.Alias;
import lombok.Data;

@Data
public class PersonAdapt {

    private String name;

    @Alias("lastName")
    private String surName;

}
