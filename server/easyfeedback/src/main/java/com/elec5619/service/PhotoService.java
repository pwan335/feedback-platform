package com.elec5619.service;

import com.elec5619.domain.PmPhoto;
import com.elec5619.domain.TopicPhoto;
import com.elec5619.domain.UserPhoto;

public interface PhotoService {
    int saveUserPhoto(UserPhoto userPhoto);
    int saveTopicPhoto(TopicPhoto topicPhoto);
    int savePmPhoto(PmPhoto pmPhoto);
    UserPhoto getUserPic(String uid);
    int saveOrUpdateUserPhoto(UserPhoto photo);

    PmPhoto getPmPic(String s);

    int saveOrUpdatePmPhoto(PmPhoto pmPhoto);
}
