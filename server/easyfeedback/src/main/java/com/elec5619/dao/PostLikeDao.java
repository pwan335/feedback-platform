package com.elec5619.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.elec5619.domain.PostComment;
import com.elec5619.domain.PostLike;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeDao extends BaseMapper<PostLike> {
}
