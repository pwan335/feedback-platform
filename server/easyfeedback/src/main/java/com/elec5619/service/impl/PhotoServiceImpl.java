package com.elec5619.service.impl;

import com.elec5619.dao.PhotoDao;
import com.elec5619.domain.PmPhoto;
import com.elec5619.domain.TopicPhoto;
import com.elec5619.domain.UserPhoto;
import com.elec5619.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoServiceImpl implements PhotoService {
    @Autowired
    private PhotoDao photoDao;
    @Override
    public int saveUserPhoto(UserPhoto userPhoto) {
        return photoDao.saveUserPhoto(userPhoto);
    }

    @Override
    public int saveTopicPhoto(TopicPhoto topicPhoto) {
        return photoDao.saveTopicPhoto(topicPhoto);
    }

    @Override
    public int savePmPhoto(PmPhoto pmPhoto) {
        return photoDao.savePmPhoto(pmPhoto);
    }

    @Override
    public UserPhoto getUserPic(String uid) {
        return photoDao.getUserPic( uid);
    }

    @Override
    public int saveOrUpdateUserPhoto(UserPhoto photo) {
        if(photo.getId()==null){
            return photoDao.saveUserPhoto(photo);
        }else{
            return photoDao.updateUserPhoto(photo);
        }

    }

    @Override
    public PmPhoto getPmPic(String pid) {
        return photoDao.getPmPic(pid);
    }

    @Override
    public int saveOrUpdatePmPhoto(PmPhoto pmPhoto) {
        if(pmPhoto.getId()==null){
            return photoDao.savePmPhoto(pmPhoto);
        }else{
            return photoDao.updatePmPhoto(pmPhoto);
        }
    }
}
