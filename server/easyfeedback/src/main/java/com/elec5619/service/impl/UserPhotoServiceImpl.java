package com.elec5619.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.elec5619.dao.UserPhotoDao;
import com.elec5619.domain.UserPhoto;
import com.elec5619.service.UserPhotoService;
import org.springframework.stereotype.Service;

@Service
public class UserPhotoServiceImpl extends ServiceImpl<UserPhotoDao, UserPhoto> implements UserPhotoService {
}
