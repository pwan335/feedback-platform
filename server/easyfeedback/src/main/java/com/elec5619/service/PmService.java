package com.elec5619.service;


import com.elec5619.domain.SimpleUser;
import com.elec5619.domain.pm.AuthPmDto;
import com.elec5619.domain.pm.ProductManager;
import com.elec5619.domain.topic.PmTopic;
import com.elec5619.domain.topic.Topic;
import com.elec5619.domain.user.AuthUserDto;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Transactional
public interface PmService {

    /**
     * 验证pm登陆信息，并返回pm具体信息
     * @param user
     * @return
     */
    boolean validPmLogin(SimpleUser user);

    /**
     * 按id查询用户信息
     * @param id
     * @return
     */
    ProductManager getById(Long id);

    /**
     * 登陆后获取用户id
     * @param email
     * @return
     */
    Long getPmIdByEmail(String email);

    /**
     * 登陆后获取用户信息
     * @param email
     * @return
     */
    ProductManager getPmByEmail(String email);

    /**
     * 创建话题
     * @param topic
     * @return
     */
    Long saveTopic(Topic topic);

    /**
     * 删除话题
     * @param topicId
     * @return
     */
    boolean deleteTopic(Long topicId);

    /**
     * 获取pm相关话题数据
     * @param pmId
     * @return
     */
    List<PmTopic> getTopicDataByPm(Long pmId);

    /**
     * 将注册信息插入数据库
     * @param pmDto
     * @return
     */
    boolean saveUser(AuthPmDto pmDto);

    /**
     * 激活用户状态
     * @param pmName
     * @return
     */
    boolean updateByName(String pmName);

    /**
     * 返回激活状态
     * @param email
     * @return
     */
    boolean getPmStatus(String email);

    /**
     * 用户保存头像
     * @param user
     * @param fileNmae
     * @return
     */
    boolean saveAvatar(SimpleUser user, String fileNmae);

    /**
     * 修改个人信息
     * @param newPm
     * @return
     */
    boolean updateProfile(ProductManager newPm);

    /**
     * 验证旧密码
     * @param pmDto
     * @return
     */
    boolean verifyOldPwd(AuthPmDto pmDto);

    /**
     * 修改密码
     * @param pmDto
     * @return
     */
    boolean changePwd(AuthPmDto pmDto);

    /**
     * 获取新密码
     * @param pmDto
     * @return
     */
    boolean forgetPwd(AuthPmDto pmDto) throws IOException;
}
