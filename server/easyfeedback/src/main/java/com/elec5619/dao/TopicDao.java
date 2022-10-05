package com.elec5619.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elec5619.domain.Topic;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicDao extends BaseMapper<Topic> {

}
