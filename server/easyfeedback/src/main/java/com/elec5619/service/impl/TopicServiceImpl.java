package com.elec5619.service.impl;

import com.elec5619.controller.response.Code;
import com.elec5619.controller.response.Result;
import com.elec5619.dao.PmMapper;
import com.elec5619.dao.TopicMapper;
import com.elec5619.dao.UserMapper;
import com.elec5619.domain.Page;
import com.elec5619.domain.SimpleUser;
import com.elec5619.domain.TopicQuery;
import com.elec5619.domain.topic.Collect;
import com.elec5619.domain.topic.Like;
import com.elec5619.domain.topic.TopicDetail;
import com.elec5619.domain.user.UserData;
import com.elec5619.service.TopicService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class TopicServiceImpl implements TopicService{

    @Value(("${web.topic-img-path}"))
    private String uploadPath;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PmMapper pmMapper;

//    public Integer getCollectNumByTopicId(Integer topicId){
//        return topicMapper.getCollectNumByTopicId(topicId) != null ? topicMapper.getCollectNumByTopicId(topicId) : 0;
//    }
//
//    public Integer getLikeNumByTopicId(Integer topicId){
//        return topicMapper.getLikeNumByTopicId(topicId) != null ? topicMapper.getLikeNumByTopicId(topicId) : 0;
//    }
//
//    public Integer getCommentNum(Integer topicId){
//        return topicMapper.getCommentNum(topicId) != null ? topicMapper.getCommentNum(topicId) : 0;
//    }

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
    public UserData getUserData(Long uid, String role) {
        UserData userData = new UserData();
        int collectNum = topicMapper.getCollectNumByUser(uid);
        int likeNum = topicMapper.getLikeNumByUser(uid);
        int commentNum = topicMapper.getCommentNumByUser(uid, role);
        int replyNum = topicMapper.getReplyNumByUser(uid, role);
        userData.setCollectNum(collectNum);
        userData.setLikeNum(likeNum);
        userData.setCommentNum(commentNum + replyNum);
        return userData;
    }

    @Override
    public List<TopicDetail> getByTopicId(Long topicId, SimpleUser user) {
        List<TopicDetail> topicDetailList = topicMapper.getByTopicId(topicId);
        System.out.println(topicDetailList);
        if (topicDetailList == null || topicDetailList.size() == 0){
            return null;
        }
        topicDetailList = this.setTopicDetail(topicDetailList, user);
        return topicDetailList;
    }

    @Override
    public List<String> uploadImg(Long topicId, MultipartFile[] files) throws IOException{
        List<String> visibleUriList = new ArrayList<>();
        List<String> saveUriList = new ArrayList<>();
        int affectedRow = 0;

        if (files.length == 0 || files == null){
            return null;
        }

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename(); //获取文件原名
            String visibleUri = "/" + fileName;
            String saveUri = uploadPath + "/" + fileName;

//            String suffixName= fileName.substring(fileName.lastIndexOf('.'));
//            //使用UUID
//            fileName= UUID.randomUUID().toString()+suffixName;

            log.info("图片原文件名={} 图片访问地址={} 图片保存真实地址={}", fileName, visibleUri, saveUri);
            File saveFile = new File(saveUri);

            if (!saveFile.exists()) {
                saveFile.mkdir();
            }
            try {
                file.transferTo(saveFile);
                visibleUriList.add(visibleUri);
                saveUriList.add(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        for (String uri : saveUriList) {
            topicMapper.saveImg(topicId, uri);
        }
        return visibleUriList;
    }

    @Override
    public PageInfo<TopicDetail> getByTopicName(TopicQuery topicQuery, SimpleUser user){
        PageHelper.startPage(topicQuery.getPageNum(), topicQuery.getPageSize());
        String topicName = "%" + topicQuery.getTopicName() + "%";
        List<TopicDetail> topicDetailList = topicMapper.getByTopicName(topicName);
        if (topicDetailList == null || topicDetailList.size() == 0){
            return null;
        }
        topicDetailList = this.setTopicDetail(topicDetailList, user);
        return new PageInfo<>(topicDetailList);
    }

    @Override
    public PageInfo<TopicDetail> getLatestTopic(SimpleUser user, Page page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<TopicDetail> topicDetailList = topicMapper.getLatestTopic();
        topicDetailList = this.setTopicDetail(topicDetailList, user);
        return new PageInfo<>(topicDetailList);
    }

    @Override
    public PageInfo<TopicDetail> getHotTopic(SimpleUser user, Page page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<TopicDetail> topicDetailList = topicMapper.getAllTopic();
        List<TopicDetail> resultList = new ArrayList<>();
        // 将topicId和总数(评论、点赞、收藏)，按键值对形式存储
        Map<Long, Integer> map = new TreeMap<Long, Integer>();

        for (TopicDetail topicDetail : topicDetailList) {
            Long topicId = topicDetail.getTopicId();
            int collectNum = topicMapper.getCollectNumByTopicId(topicId);
            int likeNum = topicMapper.getLikeNumByTopicId(topicId);
            int commentNum = topicMapper.getCommentNum(topicId);
            int total = collectNum + likeNum + commentNum;
            map.put(topicId, total);
        }
        // 将map.entrySet()转换成list{1=100, 2=150}
        List<Map.Entry<Long, Integer>> list = new ArrayList<>(map.entrySet());
        // 通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<Long, Integer>>() {
            @Override
            public int compare(Map.Entry<Long, Integer> o1, Map.Entry<Long, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        // 遍历集合
        for (Map.Entry<Long, Integer> mapping : list){
            Long topicId = mapping.getKey();
            Long uid = null;
            if(user != null){
                uid = userMapper.getUidByEmail(user.getEmail());
            }
            TopicDetail topicDetail = topicMapper.getTopicById(topicId);

            String pmAvatar = "/" + pmMapper.getAvatar(topicDetail.getPmId());
            topicDetail.setPmAvatar(pmAvatar);
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
            resultList.add(topicDetail);
        }
        return new PageInfo<>(resultList);
    }

    @Override
    public boolean saveLike(Like like) {
        return topicMapper.saveLike(like) > 0;
    }

    @Override
    public boolean saveCollect(Collect collect) {
        return topicMapper.saveCollect(collect) > 0;
    }

    @Override
    public boolean delectCollect(Collect collect) {
        return topicMapper.delectCollect(collect) > 0;
    }

    @Override
    public boolean delectLike(Like like) {
        return topicMapper.delectLike(like) > 0;
    }

    @Override
    public Collect collectedByUid(Long topicId, Long uid) {
        return topicMapper.collectedByUid(topicId, uid);
    }

    @Override
    public Like likedByUid(Long topicId, Long uid) {
        return topicMapper.likedByUid(topicId, uid);
    }

}
