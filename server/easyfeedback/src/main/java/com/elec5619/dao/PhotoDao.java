package com.elec5619.dao;


import com.elec5619.domain.PmPhoto;
import com.elec5619.domain.TopicPhoto;
import com.elec5619.domain.UserPhoto;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoDao {
    int saveUserPhoto(UserPhoto userPhoto);
    int saveTopicPhoto(TopicPhoto topicPhoto);
    int savePmPhoto(PmPhoto pmPhoto);

    UserPhoto getUserPic(String uid);

    int updateUserPhoto(UserPhoto photo);

    PmPhoto getPmPic(String pid);

    int updatePmPhoto(PmPhoto pmPhoto);
}
