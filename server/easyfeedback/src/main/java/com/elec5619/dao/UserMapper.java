package com.elec5619.dao;

import com.elec5619.domain.pm.ProductManager;
import com.elec5619.domain.user.AuthUserDto;
import com.elec5619.domain.topic.TopicDetail;
import com.elec5619.domain.user.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    @Select("select * from tbl_user where uid = #{uid}")
    User getById(Long uid);

    @Select("select * from tbl_user where email = #{email}")
    User getByEmail(String email);

    @Select("select * from tbl_user where email = #{email} and password = #{password}")
    User getByEmailAndPass(String email, String password);

    @Select("select uid from tbl_user where email = #{email}")
    Long getUidByEmail(String email);

    @Select("select * from tbl_user where email = #{email}")
    User getUserByEmail(String email);

    @Select("select * from tbl_topic where topic_id in (select topic_id from tbl_collects where uid = #{uid})")
    List<TopicDetail> getTopicByCollected(Long uid);

    @Select("select * from tbl_topic where topic_id in (select topic_id from tbl_likes where uid = #{uid})")
    List<TopicDetail> getTopicByLiked(Long uid);

//    @Select("select distinct(topic_id) from tbl_comment where (" +
//            "id in (select comment_id from tbl_reply where from_uid = 1 and from_role = 'user')) " +
//            "or (uid = 1 and role = 'user')")
    List<TopicDetail> getTopicByComment(Long uid, String role);

    @Insert("insert into tbl_user (user_name, password, email, avatar, create_time, status) values(#{userName}, #{password}, #{email}, #{avatar}, #{createTime}, #{status})")
    int saveUser(AuthUserDto userDto);

    @Update("update tbl_user set status = 1 where user_name = #{userName}")
    int updateByName(String userName);

    @Select("select status from tbl_user where email = #{email}")
    int getUserStatus(String email);

    @Select("select avatar from tbl_user where uid = #{uid}")
    String getAvatar(Long uid);

    @Update("update tbl_user set avatar = #{avatar} where email = #{email}")
    int saveAvatar(String email, String avatar);

    @Update("update tbl_user set user_name = #{userName}, address = #{address}, phone_number = #{phoneNumber}, hobby=#{hobby}, update_time = #{updateTime} where uid = #{uid}")
    int updateProfile(User newUser);

    @Select("select * from tbl_user where email = #{email} and password = #{password}")
    User verifyOldPwd(AuthUserDto userDto);

    @Update("update tbl_user set password = #{newPwd} where email = #{email}")
    int changePwd(AuthUserDto userDto);


}
