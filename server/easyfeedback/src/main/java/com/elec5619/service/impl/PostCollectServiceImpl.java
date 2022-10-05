package com.elec5619.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.elec5619.dao.PostCollectDao;
import com.elec5619.domain.PostCollect;
import com.elec5619.service.PostCollectService;
import org.springframework.stereotype.Service;

@Service
public class PostCollectServiceImpl extends ServiceImpl<PostCollectDao, PostCollect>  implements PostCollectService {
}
