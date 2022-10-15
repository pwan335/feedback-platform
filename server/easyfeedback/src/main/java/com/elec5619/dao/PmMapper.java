package com.elec5619.dao;

import com.elec5619.domain.pm.AuthPmDto;
import com.elec5619.domain.pm.ProductManager;
import com.elec5619.domain.topic.PmTopic;
import com.elec5619.domain.topic.Topic;
import com.elec5619.domain.topic.TopicDetail;
import com.elec5619.domain.user.AuthUserDto;
import com.elec5619.domain.user.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PmMapper {

    @Select("select * from tbl_pm where pm_id = #{pmId}")
    ProductManager getById(Long pmId);

    @Select("select * from tbl_pm where email = #{email}")
    ProductManager getPmByEmail(String email);

    @Select("select * from tbl_pm where email = #{email} and password = #{password}")
    ProductManager getByEmailAndPass(String email, String password);

    @Select("select uid from tbl_pm where email = #{email}")
    Long getPmIdByEmail(String email);

    @Insert("insert into tbl_topic (pm_id, topic_name, content, create_time) values(#{pmId}, #{topicName}, #{content}, #{createTime})")
//    @SelectKey(statement = "call identity()", keyProperty="topicId", before=false, resultType=Integer.class)
    @Options(keyProperty = "topicId", useGeneratedKeys = true)
    Integer saveTopic(Topic topic);

    @Delete("delete from tbl_topic where topic_id = #{topicId}")
    Integer deleteTopic(Long topicId);

    @Select("select * from tbl_topic where pm_id = #{pmId}")
    List<PmTopic> getTopicByPmId(Long pmId);

    @Select("select count(*) from tbl_collects where topic_id = #{topicId}")
    int getCollectNumByTopic(Long topicId);

    @Select("select count(*) from tbl_likes where topic_id = #{topicId}")
    int getLikeNumByTopic(Long topicId);

    // 查询该话题comment和reply的数量和
    int getCommentNumByTopic(Long topicId);

    @Insert("insert into tbl_pm (pm_name, password, email, avatar, company, create_time, status) values(#{pmName}, #{password}, #{email}, #{avatar}, #{company}, #{createTime}, #{status})")
    int saveUser(AuthPmDto pmDto);

    @Update("update tbl_pm set status = 1 where pm_name = #{pmName}")
    int updateByName(String pmName);

    @Select("select status from tbl_pm where email = #{email}")
    int getPmStatus(String email);

    @Update("update tbl_pm set avatar = #{avatar} where email = #{email}")
    int saveAvatar(String email, String avatar);

    @Update("update tbl_pm set pm_name = #{pmName}, address = #{address}, phone_number = #{phoneNumber}, hobby=#{hobby}, company = #{company}, update_time = #{updateTime} where pm_id = #{pmId}")
    int updateProfile(ProductManager newPm);

    @Select("select * from tbl_pm where email = #{email} and password = #{password}")
    User verifyOldPwd(AuthPmDto pmDto);

    @Update("update tbl_pm set password = #{newPwd} where email = #{email}")
    int changePwd(AuthPmDto pmDto);

    @Select("select avatar from tbl_pm where pm_id = #{pmId}")
    String getAvatar(Long pmId);
}
