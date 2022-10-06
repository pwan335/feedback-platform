package com.elec5619.domain;

import com.elec5619.domain.req.BaseReq;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SetPmReq extends BaseReq {
    @NotBlank
    private String modifyId;
}
