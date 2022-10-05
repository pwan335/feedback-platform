package com.elec5619.domain.req;

import lombok.Data;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Data
public class PageInfo extends BaseReq {
    @Min(1)
    private Integer pageNum;
    @Max(999)
    private Integer pageSize;
}
