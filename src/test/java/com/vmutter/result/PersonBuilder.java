package com.vmutter.result;

import com.vmutter.annotation.Alias;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PersonBuilder {

    private String name;

    @Alias("lastName")
    private String surName;

}
