package com.elec5619.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.elec5619.dao.TopicPhotoDao;
import com.elec5619.domain.TopicPhoto;
import com.elec5619.service.TopicPhotoService;
import org.springframework.stereotype.Service;

@Service
public class TopicPhotoServiceImpl extends ServiceImpl<TopicPhotoDao, TopicPhoto> implements TopicPhotoService {
}
