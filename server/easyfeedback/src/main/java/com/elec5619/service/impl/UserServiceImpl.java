package com.elec5619.service.impl;


import com.elec5619.dao.PmMapper;
import com.elec5619.dao.TopicMapper;
import com.elec5619.domain.user.AuthUserDto;
import com.elec5619.domain.SimpleUser;
import com.elec5619.domain.topic.TopicDetail;
import com.elec5619.domain.user.User;
import com.elec5619.dao.UserMapper;
import com.elec5619.service.UserService;
import com.elec5619.utils.RandomPwd;
import com.elec5619.utils.SendCloudAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PmMapper pmMapper;

    public User getById(Long id){
        return userMapper.getById(id);
    }

    public List<TopicDetail> setTopicDetail(List<TopicDetail> topicDetailList, SimpleUser user){
        Long uid = null;
        if(user != null){
            uid = userMapper.getUidByEmail(user.getEmail());
        }
        for (TopicDetail topicDetail : topicDetailList) {
            Long topicId = topicDetail.getTopicId();

            String pmAvatar = "/" + pmMapper.getAvatar(topicDetail.getPmId());
            topicDetail.setPmAvatar(pmAvatar);
            topicDetail.setPmName(pmMapper.getById(topicDetail.getPmId()).getPmName());
            topicDetail.setCollectNum(topicMapper.getCollectNumByTopicId(topicId));
            topicDetail.setLikeNum(topicMapper.getLikeNumByTopicId(topicId));
            topicDetail.setCommentNum(topicMapper.getCommentNum(topicId));

            List<String> images = topicMapper.getTopicImgById(topicId);
            List<String> imagePathList = new ArrayList<>();
            for (String image : images) {
                imagePathList.add("/" + image);
            }
            topicDetail.setImages(imagePathList);

            if (uid != null){
                topicDetail.setCollectState(topicMapper.collectedByUid(topicId, uid) != null);
                topicDetail.setLikeState(topicMapper.likedByUid(topicId, uid) != null);
            }
        }
        return topicDetailList;
    }

    @Override
    public User getByEmail(String email) {
        return null;
    }

    @Override
    public boolean validUserLogin(SimpleUser user) {
        User userDetail = userMapper.getByEmailAndPass(user.getEmail(), user.getPassword());
        return userDetail != null ? true : false;
    }

    @Override
    public List<TopicDetail> getTopicByCollected(SimpleUser user) {
        Long uid = userMapper.getUidByEmail(user.getEmail());
        List<TopicDetail> topicDetailList = userMapper.getTopicByCollected(uid);
        topicDetailList = this.setTopicDetail(topicDetailList, user);
        return topicDetailList;
    }

    @Override
    public List<TopicDetail> getTopicByLiked(SimpleUser user) {
        Long uid = userMapper.getUidByEmail(user.getEmail());
        List<TopicDetail> topicDetailList = userMapper.getTopicByLiked(uid);
        topicDetailList = this.setTopicDetail(topicDetailList, user);
        return topicDetailList;
    }

    @Override
    public List<TopicDetail> getTopicByComment(SimpleUser user) {
        Long uid = userMapper.getUidByEmail(user.getEmail());
        List<TopicDetail> topicDetailList = userMapper.getTopicByComment(uid, user.getRole());
        topicDetailList = this.setTopicDetail(topicDetailList, user);
        return topicDetailList;
    }

    @Override
    public boolean saveUser(AuthUserDto userDto) {
        boolean flag = userMapper.getUserByEmail(userDto.getEmail()) == null ? true : false;
        if(flag){
            return userMapper.saveUser(userDto) > 0;
        }
        return flag;
    }

    @Override
    public boolean updateByName(String userName) {
        return userMapper.updateByName(userName) > 0;
    }

    @Override
    public boolean getUserStatus(String email) {
        return userMapper.getUserStatus(email) != 0 ? true : false;
    }

    @Override
    public boolean saveAvatar(SimpleUser user, String fileNmae) {
        return userMapper.saveAvatar(user.getEmail(), fileNmae) > 0;
    }

    @Override
    public boolean updateProfile(User newUser) {
        return userMapper.updateProfile(newUser) > 0;
    }

    @Override
    public boolean verifyOldPwd(AuthUserDto userDto) {
        return userMapper.verifyOldPwd(userDto) != null;
    }

    @Override
    public boolean changePwd(AuthUserDto userDto) {
//        String oldPwd = userDto.getPassword();
//        String newPwd = userDto.getNewPwd();
        boolean flag = userMapper.verifyOldPwd(userDto) != null;
        if (!flag){
            return false;
        }
        return userMapper.changePwd(userDto) > 0;
    }

    @Override
    public boolean forgetPwd(AuthUserDto userDto) throws IOException {
        boolean flag = userMapper.getUserByEmail(userDto.getEmail()) != null;
        if(!flag) {
            return false;
        }
        String newPwd = RandomPwd.getPassWord();
        String md5Password = DigestUtils.md5DigestAsHex(newPwd.getBytes());

        userDto.setNewPwd(md5Password);
//        System.out.println(userDto);
        flag = userMapper.changePwd(userDto) > 0;
        if(flag){
            userDto.setPassword(newPwd);
            SendCloudAPI.sendPwd(userDto, null);
        }
        return flag ? true : false;
    }

    @Override
    public Long getUidByEmail(String email) {
        return userMapper.getUidByEmail(email);
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

}
