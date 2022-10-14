package com.elec5619.service;

import com.elec5619.domain.user.AuthUserDto;
import com.elec5619.domain.SimpleUser;
import com.elec5619.domain.topic.TopicDetail;
import com.elec5619.domain.user.User;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Transactional
public interface UserService {

    /**
     * 登陆后获取用户id
     * @param email
     * @return
     */
    Long getUidByEmail(String email);

    /**
     * 登陆后获取用户信息
     * @param email
     * @return
     */
    User getUserByEmail(String email);

    /**
     * 按id查询用户信息
     * @param id
     * @return
     */
    User getById(Long id);

    /**
     * 用户登陆根据email和密码进行验证
     * @param email
     * @return
     */
    User getByEmail(String email);

    /**
     * 验证用户登陆信，并返回用户具体信息
     * @param user
     * @return
     */
    boolean validUserLogin(SimpleUser user);

    /**
     * 获取用户收藏的话题
     * @param user
     * @return
     */
    List<TopicDetail> getTopicByCollected(SimpleUser user);

    /**
     * 获取用户点赞的话题
     * @param user
     * @return
     */
    List<TopicDetail> getTopicByLiked(SimpleUser user);

    /**
     * 获取用户评论过的话题
     * @param user
     * @return
     */
    List<TopicDetail> getTopicByComment(SimpleUser user);

    /**
     * 将注册信息插入数据库
     * @param userDto
     * @return
     */
    boolean saveUser(AuthUserDto userDto);

    /**
     * 激活用户状态
     * @param userName
     * @return
     */
    boolean updateByName(String userName);

    /**
     * 返回激活状态
     * @param email
     * @return
     */
    boolean getUserStatus(String email);

    /**
     * 用户保存头像
     * @param user
     * @param fileNmae
     * @return
     */
    boolean saveAvatar(SimpleUser user, String fileNmae);

    /**
     * 修改用户信息
     * @param newUser
     * @return
     */
    boolean updateProfile(User newUser);

    /**
     * 验证旧密码
     * @param userDto
     * @return
     */
    boolean verifyOldPwd(AuthUserDto userDto);

    /**
     * 修改密码
     * @param userDto
     * @return
     */
    boolean changePwd(AuthUserDto userDto);

    /**
     * 获取新密码
     * @param userDto
     * @return
     */
    boolean forgetPwd(AuthUserDto userDto) throws IOException;
}
