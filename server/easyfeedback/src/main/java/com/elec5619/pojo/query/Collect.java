package com.elec5619.pojo.query;

import lombok.Data;

import java.sql.Date;


@Data
public class Collect {

    private Integer id;
    private Integer topicId;
    private Integer uid;
    private Date date;

}
