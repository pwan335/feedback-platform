package com.elec5619.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.elec5619.dao.PostCommentDao;
import com.elec5619.domain.PostComment;
import com.elec5619.service.PostCommentService;
import org.springframework.stereotype.Service;

@Service
public class PostCommentServiceImpl extends ServiceImpl<PostCommentDao, PostComment>  implements PostCommentService {
}
