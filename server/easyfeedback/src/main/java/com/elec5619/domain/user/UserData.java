package com.elec5619.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    private Integer collectNum;
    private Integer commentNum;
    private Integer likeNum;
}
