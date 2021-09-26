package com.wizzstudio.push.dao.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.wizzstudio.push.dao.UserDao;
import com.wizzstudio.push.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: TheR1sing3un
 * @Date: 2021/09/25/19:28
 * @Description: 用户数据交互层的实现类
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private RedisUtils redisUtils;

    //存储nickname和userId映射的key
    private final String NAME_ID = "name-id";

    //存储nickname和able(是否可用)映射的key
    private final String NAME_ABLE = "name-able";

    //存储userId和用户流程状态的key
    private final String ID_STATUS = "id-status";

    /**
     * 查询是否有该昵称
     * @param nickname
     * @return 是否存在该昵称
     */
    @Override
    public boolean checkNicknameExist(String nickname) {
        return redisUtils.hHasKey(NAME_ID,nickname);
    }

    /**
     * 获取所有昵称和用户id的map
     * @return 所有昵称和用户id的map
     */
    @Override
    public Map<String,String> listNicknamesAndIds() {
        return redisUtils.hKeys(NAME_ID);
    }

    /**
     * 获取所有昵称和可用状态的map
     * @return 所有昵称和可用状态的map
     */
    @Override
    public Map<String,Boolean> listNicknamesAndStatus() {
        return redisUtils.hKeys(NAME_ABLE);
    }

    /**
     * 根据昵称查用户id
     * @param nickname
     * @return 用户id
     */
    @Override
    public String getUserIdByNickname(String nickname) {
        String userId = (String) redisUtils.hget(NAME_ID, nickname);
        return userId;
    }

    /**
     * 获取该昵称的可用状态
     * @param nickname
     * @return 是否可用
     */
    @Override
    public boolean getStatusByNickname(String nickname) {
        return (boolean) redisUtils.hget(NAME_ABLE,nickname);
    }

    /**
     * 更改该昵称可用状态
     * @param nickname
     * @param status
     * @return 更改是否成功
     */
    @Override
    public boolean updateStatusByNickname(String nickname, boolean status) {
        return redisUtils.hset(NAME_ABLE, nickname,status);
    }

    /**
     * 插入一条新昵称和用户id的关系
     * @param nickname
     * @param userId
     * @return 是否成功插入
     */
    @Override
    public boolean saveNicknameAndId(String nickname, String userId) {
        return redisUtils.hset(NAME_ID, nickname, userId);
    }

    /**
     * 插入一条新昵称和可用状态的关系
     * @param nickname
     * @return 是否成功插入
     */
    @Override
    public boolean saveNicknameAndStatus(String nickname){
        return redisUtils.hset(NAME_ABLE, nickname, true);
    }

    /**
     * 检查改用户是否有状态
     * @param userId
     * @return 用户是否有状态
     */
    @Override
    public boolean checkUserStatusExist(String userId) {
        return redisUtils.hHasKey(ID_STATUS,userId);
    }

    /**
     * 获得用户的状态
     * @param userId
     * @return 用户的状态(0===表示用户处于添加流程中,1===表示用户处于启用昵称流程中,-1===表示用户处于禁用昵称流程中)
     */
    @Override
    public int getUserStatus(String userId) {
        return (int)redisUtils.hget(ID_STATUS,userId);
    }

    /**
     * 设置用户的流程中状态
     * @param userId
     * @param status
     * @return 是否成功设置
     */
    @Override
    public boolean setUserStatus(String userId, int status) {
        return redisUtils.hset(ID_STATUS,userId,status);
    }

    /**
     * 删除用户状态
     * @param userId
     * @return 是否成功删除
     */
    @Override
    public void deleteUserStatus(String userId) {
        redisUtils.hdel(ID_STATUS,userId);
    }
}
