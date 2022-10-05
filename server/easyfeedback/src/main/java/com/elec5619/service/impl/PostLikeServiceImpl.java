package com.elec5619.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.elec5619.dao.PostLikeDao;
import com.elec5619.domain.PostLike;
import com.elec5619.service.PostLikeService;
import org.springframework.stereotype.Service;

@Service
public class PostLikeServiceImpl extends ServiceImpl<PostLikeDao, PostLike>  implements PostLikeService {
}
