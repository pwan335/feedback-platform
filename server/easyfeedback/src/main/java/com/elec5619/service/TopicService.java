package com.elec5619.service;

import com.elec5619.domain.Page;
import com.elec5619.domain.SimpleUser;
import com.elec5619.domain.TopicQuery;
import com.elec5619.domain.topic.Collect;
import com.elec5619.domain.topic.Like;
import com.elec5619.domain.topic.TopicDetail;
import com.elec5619.domain.user.UserData;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Transactional
public interface TopicService {

    /**
     * 根据用户获取所有收藏、点赞、评论数量
     * @param uid
     * @return
     */
    UserData getUserData(Long uid , String role);

    /**
     * 按topicName进行话题搜索
     * @param topicQuery
     * @return
     */
    PageInfo<TopicDetail> getByTopicName(TopicQuery topicQuery, SimpleUser user);

    /**
     * 查询该用户是否收藏指定话题
     * @param topicId
     * @param uid
     * @return
     */
    Collect collectedByUid(Long topicId, Long uid);

    /**
     * 查询该用户是否点赞指定话题
     * @param topicId
     * @param uid
     * @return
     */
    Like likedByUid(Long topicId, Long uid);

    /**
     * 获取根据最新时间排序的话题列表
     * @return
     */
    PageInfo<TopicDetail> getLatestTopic(SimpleUser user, Page page);

    /**
     * 根据点赞数、收藏数、评论数，从高到低获取热门话题
     * @return
     */
    PageInfo<TopicDetail> getHotTopic(SimpleUser user, Page page);

    /**
     * 用户点赞话题
     * @param like
     * @return
     */
    boolean saveLike(Like like);

    /**
     * 用户收藏话题
     * @param collect
     * @return
     */
    boolean saveCollect(Collect collect);

    /**
     * 用户取消收藏话题
     * @param collect
     * @return
     */
    boolean delectCollect(Collect collect);

    /**
     * 用户取消点赞话题
     * @param like
     * @return
     */
    boolean delectLike(Like like);

    List<TopicDetail> getByTopicId(Long topicId, SimpleUser user);

    /**
     * pm上传topicImg
     * @param topicId
     * @param files
     * @return
     */
    List<String> uploadImg(Long topicId, MultipartFile[] files) throws IOException;
}
