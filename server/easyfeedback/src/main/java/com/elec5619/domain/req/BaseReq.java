package com.elec5619.domain.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class BaseReq implements Serializable {
    @NotBlank
    private String userId;
    @NotBlank
    private String token;
}
