package com.elec5619.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elec5619.domain.Topic;
import com.elec5619.domain.TopicPhoto;
import com.elec5619.domain.UserPhoto;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicPhotoDao extends BaseMapper<TopicPhoto> {
}
