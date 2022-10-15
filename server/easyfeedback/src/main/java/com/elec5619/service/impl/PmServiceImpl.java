package com.elec5619.service.impl;

import com.elec5619.dao.PmMapper;
import com.elec5619.dao.TopicMapper;
import com.elec5619.dao.UserMapper;
import com.elec5619.domain.SimpleUser;
import com.elec5619.domain.pm.AuthPmDto;
import com.elec5619.domain.pm.ProductManager;
import com.elec5619.domain.topic.PmTopic;
import com.elec5619.domain.topic.Topic;
import com.elec5619.domain.topic.TopicDetail;
import com.elec5619.domain.user.AuthUserDto;
import com.elec5619.service.PmService;
import com.elec5619.utils.RandomPwd;
import com.elec5619.utils.SendCloudAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PmServiceImpl implements PmService {

    @Autowired
    private PmMapper pmMapper;
    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private UserMapper userMapper;

    public List<PmTopic> setTopicDetail(List<PmTopic> topicDetailList, SimpleUser user){
        Long uid = null;
        if(user != null){
            uid = userMapper.getUidByEmail(user.getEmail());
        }
        for (PmTopic topicDetail : topicDetailList) {
            Long topicId = topicDetail.getTopicId();

            String pmAvatar = "/" + pmMapper.getAvatar(topicDetail.getPmId());
            topicDetail.setPmAvatar(pmAvatar);
            topicDetail.setPmName(pmMapper.getById(topicDetail.getPmId()).getPmName());
            topicDetail.setCollectNum(pmMapper.getCollectNumByTopic(topicId));
            topicDetail.setLikeNum(pmMapper.getLikeNumByTopic(topicId));
            topicDetail.setCommentNum(pmMapper.getCommentNumByTopic(topicId));

            List<String> images = topicMapper.getTopicImgById(topicId);
            List<String> imagePathList = new ArrayList<>();
            for (String image : images) {
                imagePathList.add("/" + image);
            }
            topicDetail.setImages(imagePathList);

//            if (uid != null){
//                topicDetail.setCollectState(topicMapper.collectedByUid(topicId, uid) != null);
//                topicDetail.setLikeState(topicMapper.likedByUid(topicId, uid) != null);
//            }
        }
        return topicDetailList;
    }

    @Override
    public boolean saveUser(AuthPmDto pmDto) {
        boolean flag = pmMapper.getPmByEmail(pmDto.getEmail()) == null ? true : false;
        if(flag){
            return pmMapper.saveUser(pmDto) > 0;
        }
        return flag;
    }

    @Override
    public boolean updateByName(String pmName) {
        return pmMapper.updateByName(pmName) > 0;
    }

    @Override
    public boolean getPmStatus(String email) {
        return pmMapper.getPmStatus(email) != 0 ? true : false;
    }

    @Override
    public boolean validPmLogin(SimpleUser pm) {
        ProductManager pmDetail = pmMapper.getByEmailAndPass(pm.getEmail(), pm.getPassword());
        return pmDetail != null ? true : false;
    }

    @Override
    public ProductManager getById(Long id) {
        return pmMapper.getById(id);
    }

    @Override
    public Long getPmIdByEmail(String email) {
        return pmMapper.getPmIdByEmail(email);
    }

    @Override
    public ProductManager getPmByEmail(String email) {
        return pmMapper.getPmByEmail(email);
    }

    @Override
    public Long saveTopic(Topic topic) {
        int row = pmMapper.saveTopic(topic);
        Long topicId = topic.getTopicId();
        return topicId;
    }

    @Override
    public boolean deleteTopic(Long topicId) {
        return pmMapper.deleteTopic(topicId) > 0;
    }

    @Override
    public List<PmTopic> getTopicDataByPm(Long pmId) {
        List<PmTopic> topicList = pmMapper.getTopicByPmId(pmId);
        topicList = this.setTopicDetail(topicList, null);

//        for (PmTopic topic : topicList) {
////            PmTopic pmTopic = new PmTopic();
//            Long topicId = topic.getTopicId();
//
//            int collectNum = pmMapper.getCollectNumByTopic(topicId);
//            int likeNum = pmMapper.getLikeNumByTopic(topicId);
//            int commentNum = pmMapper.getCommentNumByTopic(topicId);
//
//
//            pmTopic.setTopicId(topicId);
//            pmTopic.setTopicName(topic.getTopicName());
//            pmTopic.setCreateTime(topic.getCreateTime());
//            pmTopic.setCollectNum(collectNum);
//            pmTopic.setLikeNum(likeNum);
//            pmTopic.setCommentNum(commentNum);
//            pmTopicList.add(pmTopic);
//        }
        return topicList;
    }

    @Override
    public boolean forgetPwd(AuthPmDto pmDto) throws IOException {
        boolean flag = pmMapper.getPmByEmail(pmDto.getEmail()) != null;
        if(!flag) {
            return false;
        }
        String newPwd = RandomPwd.getPassWord();
        String md5Password = DigestUtils.md5DigestAsHex(newPwd.getBytes());

        pmDto.setNewPwd(md5Password);
        flag = pmMapper.changePwd(pmDto) > 0;
        if(flag){
            pmDto.setPassword(newPwd);
            SendCloudAPI.sendPwd(null, pmDto);
        }
        return flag ? true : false;
    }

    @Override
    public boolean saveAvatar(SimpleUser user, String fileName) {
        return pmMapper.saveAvatar(user.getEmail(), fileName) > 0;
    }

    @Override
    public boolean updateProfile(ProductManager newPm) {
        return pmMapper.updateProfile(newPm) > 0;
    }

    @Override
    public boolean verifyOldPwd(AuthPmDto pmDto) {
        return pmMapper.verifyOldPwd(pmDto) != null;
    }

    @Override
    public boolean changePwd(AuthPmDto pmDto) {
        boolean flag = pmMapper.verifyOldPwd(pmDto) != null;
        if (!flag){
            return false;
        }
        return pmMapper.changePwd(pmDto) > 0;
    }
}
