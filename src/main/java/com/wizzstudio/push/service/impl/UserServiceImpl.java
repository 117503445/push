package com.wizzstudio.push.service.impl;

import com.wizzstudio.push.dao.UserDao;
import com.wizzstudio.push.exception.PushException;
import com.wizzstudio.push.exception.WxUserException;
import com.wizzstudio.push.service.UserService;
import com.wizzstudio.push.utils.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: TheR1sing3un
 * @Date: 2021/09/25/15:42
 * @Description: 用户逻辑处理的实现类
 */
@Service
public class UserServiceImpl implements UserService {

    //用户处于新增昵称的状态
    public static final Integer STATUS_ADD = 0;

    //用户处于启用昵称的状态
    public static final Integer STATUS_ENABLE = 1;

    //用户处于禁用昵称的状态
    public static final Integer STATUS_DISABLE = -1;

    @Autowired
    private UserDao userDao;

    /**
     * 初始化用户
     * @param userId
     * @return 用户初始昵称(也就是用户id)
     */
    @Override
    public String initUser(String userId) {
        //初始的昵称就用userId来表示,初始状态就是可用状态
        boolean success = userDao.saveNicknameAndId(userId, userId) && userDao.saveNicknameAndStatus(userId);
        if (!success) throw new WxUserException(WxUserException.USER_INIT_ERROR,"用户昵称初始化失败,请联系管理员",userId);
        return userId;
    }

    /**
     * 根据可用的昵称获取userId
     * @param nickname
     * @return 用户id,当该昵称不存在时和状态不可用的时候返回null
     */
    @Override
    public String getUserIdByAbleNickname(String nickname) {
        //验证昵称是否存在
        boolean exist = userDao.checkNicknameExist(nickname);
        if (!exist) throw new PushException(PushException.NICKNAME_NO_EXISTS,"该昵称不存在");
        String userId = userDao.getUserIdByNickname(nickname);
        //获取昵称可用状态
        boolean status = userDao.getStatusByNickname(nickname);
        if (!status) throw new PushException(PushException.NICKNAME_DISABLE,"该昵称已被用户禁用");
        return userId;
    }

    /**
     * 根据用户id获取该用户所有的昵称
     * @param userId
     * @return 用户所有昵称
     */
    @Override
    public List<String> listNicknames(String userId) {
        //构建昵称集合
        List<String> nicknames = new LinkedList<>();
        //分别获取昵称和id的hash表
        Map<String, String > nicknamesAndIds = userDao.listNicknamesAndIds();
        //获得迭代器
        Iterator<Map.Entry<String, String>> iterator = nicknamesAndIds.entrySet().iterator();
        //对昵称和id的关系表进行遍历
        while(iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            //获取当前遍历到的昵称(也就是key)
            String nicknameNow = entry.getKey();
            //获取当前遍历到的用户id(也就是value)
            String userIdNow = entry.getValue();
            //只有当当前的遍历到的用户id是我们要查的用户id时加入该用户的昵称集合
            if (userIdNow.equals(userId)){
                nicknames.add(nicknameNow);
            }
        }
        return nicknames;
    }

    /**
     * 根据用户id获取该用户所有的可用昵称
     * @param userId
     * @return 用户所有可用昵称
     */
    @Override
    public List<String> listNicknamesAble(String userId) {
        //构建可用昵称集合
        List<String> nicknames = new LinkedList<>();
        //分别获取昵称和id以及昵称和可用状态的hash表
        Map<String, String> nicknamesAndIds = userDao.listNicknamesAndIds();
        //之所以获得昵称和状态的集合是为了避免后续遍历过程中频繁的去redis里面查
        Map<String, Boolean> nicknamesAndStatus = userDao.listNicknamesAndStatus();
        //获得迭代器
        Iterator<Map.Entry<String, String>> iterator = nicknamesAndIds.entrySet().iterator();
        //对昵称和id的关系表进行遍历
        while(iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            //获取当前遍历到的昵称(也就是key)
            String nicknameNow = entry.getKey();
            //获取当前遍历到的用户id(也就是value)
            String userIdNow = entry.getValue();
            //只有当当前的遍历到的用户id是我们要查的用户id并且该昵称可用的时候,加入该用户的昵称集合
            if (userIdNow.equals(userId) && nicknamesAndStatus.get(nicknameNow)){
                nicknames.add(nicknameNow);
            }
        }
        return nicknames;
    }

    /**
     * 根据用户id获取该用户所有的禁用昵称
     * @param userId
     * @return 用户的禁用昵称
     */
    @Override
    public List<String> listNicknamesDisabled(String userId) {
        //构建不可用昵称集合
        List<String> nicknames = new LinkedList<>();
        //分别获取昵称和id以及昵称和可用状态的hash表
        Map<String, String> nicknamesAndIds = userDao.listNicknamesAndIds();
        //之所以获得昵称和状态的集合是为了避免后续遍历过程中频繁的去redis里面查
        Map<String, Boolean> nicknamesAndStatus = userDao.listNicknamesAndStatus();
        //获得迭代器
        Iterator<Map.Entry<String, String>> iterator = nicknamesAndIds.entrySet().iterator();
        //对昵称和id的关系表进行遍历
        while(iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            //获取当前遍历到的昵称(也就是key)
            String nicknameNow = entry.getKey();
            //获取当前遍历到的用户id(也就是value)
            String userIdNow = entry.getValue();
            //只有当当前的遍历到的用户id是我们要查的用户id并且该昵称不可用的时候,加入该用户的昵称集合
            if (userIdNow.equals(userId) && !nicknamesAndStatus.get(nicknameNow)){
                nicknames.add(nicknameNow);
            }
        }
        return nicknames;
    }

    /**
     * 给该用户id下添加一个昵称
     * @param userId
     * @param nickname
     * @return 添加的昵称
     */
    @Override
    public String addNickname(String userId, String nickname) {
        //去掉用户传来的空白,转化成无空白字符串后再检验是否合法并且返回真正的昵称
        String namePattern = TextUtils.verifyNamePattern(nickname);
        //验证后为空则不合法
        if(namePattern == null) throw new WxUserException(WxUserException.NICKNAME_ILLEGAL,"昵称不合法,请按照规则重新输入(2-12位英文数字组成)",userId);
        //检验该昵称是否已存在
        boolean exist = userDao.checkNicknameExist(namePattern);
        if (exist) throw new WxUserException(WxUserException.NICKNAME_EXISTS,"昵称已被人使用,请重新输入您需要添加的昵称",userId);
        //插入昵称用户id表和昵称可用状态表
        boolean success = userDao.saveNicknameAndId(namePattern, userId) && userDao.saveNicknameAndStatus(namePattern);
        if (!success) throw new WxUserException(WxUserException.NICKNAME_ADD_ERROR,"新增昵称失败",userId);
        //成功添加后删除用户流程状态
        userDao.deleteUserStatus(userId);
        return namePattern;
    }

    /**
     * 启用被禁用的昵称
     * @param userId
     * @param nickname
     * @return 启用的昵称
     */
    @Override
    public String enableNickname(String userId, String nickname) {
        //去掉用户传来的空白,转化成无空白字符串后再检验是否合法并且返回真正的昵称
        String namePattern = TextUtils.verifyNamePattern(nickname);
        //判断昵称是否合法、是否存在并且属于该用户
        boolean nicknameRight = namePattern !=null && userDao.checkNicknameExist(namePattern) && userId.equals(userDao.getUserIdByNickname(namePattern));
        //当该昵称不存在或者该昵称不属于该用户时,抛出异常
        if (!nicknameRight) throw new WxUserException(WxUserException.NICKNAME_ERROR_BELONG,"该昵称不存在,请重新输入您需要启用的昵称",userId);
        //更新昵称的可用状态
        boolean updateStatusByNickname = userDao.updateStatusByNickname(namePattern, true);
        if (!updateStatusByNickname) throw new WxUserException(WxUserException.NICKNAME_STATUS_SET_ERROR,"修改昵称状态失败",userId);
        //删除用户流程状态
        userDao.deleteUserStatus(userId);
        return namePattern;
    }

    /**
     * 禁用正在使用的昵称
     * @param userId
     * @param nickname
     * @return 禁用的昵称
     */
    @Override
    public String disableNickname(String userId, String nickname) {
        //去掉用户传来的空白,转化成无空白字符串后再检验是否合法并且返回真正的昵称
        String namePattern = TextUtils.verifyNamePattern(nickname);
        //判断昵称是否合法、是否存在并且属于该用户
        boolean nicknameRight = namePattern != null && userDao.checkNicknameExist(namePattern) && userId.equals(userDao.getUserIdByNickname(namePattern));
        //当该昵称不存在或者该昵称不属于该用户时,抛出异常
        if (!nicknameRight) throw new WxUserException(WxUserException.NICKNAME_ERROR_BELONG,"该昵称不存在,请重新输入您需要禁用的昵称",userId);
        //更新昵称的可用状态
        boolean updateStatusByNickname = userDao.updateStatusByNickname(namePattern, false);
        if (!updateStatusByNickname) throw new WxUserException(WxUserException.NICKNAME_STATUS_SET_ERROR,"修改昵称状态失败",userId);
        //删除用户流程状态
        userDao.deleteUserStatus(userId);
        return namePattern;
    }

    /**
     * 设置当前用户流程状态为正在添加昵称
     * @param userId
     * @return 是否成功
     */
    @Override
    public boolean setUserStatusAdd(String userId) {
        return setUserStatus(userId,STATUS_ADD);
    }

    /**
     * 设置当前用户流程状态为正在启用昵称
     * @param userId
     * @return 是否成功
     */
    @Override
    public boolean setUserStatusEnable(String userId) {
        return setUserStatus(userId,STATUS_ENABLE);
    }

    /**
     * 设置当前用户流程状态为禁用昵称
     * @param userId
     * @return 是否成功
     */
    @Override
    public boolean setUserStatusDisable(String userId) {
        return setUserStatus(userId,STATUS_DISABLE);
    }

    /**
     * 获取当前用户的流程状态
     * @param userId
     * @return 用户的流程状态(注意:为null的时候表示当前用户无状态)
     */
    @Override
    public int getUserStatus(String userId) {
        return userDao.getUserStatus(userId);
    }

    /**
     * 检查当前用户是否处于流程中
     * @param userId
     * @return 是否处于流程中
     */
    @Override
    public boolean userStatusExists(String userId) {
        return userDao.checkUserStatusExist(userId);
    }

    /**
     * 删除当前用户流程状态
     * @param userId
     * @return 是否成功删除
     */
    @Override
    public boolean deleteUserStatus(String userId) {
        if (!userDao.checkUserStatusExist(userId)) return true;
        userDao.deleteUserStatus(userId);
        return true;
    }

    /**
     * 设置用户流程状态
     * @param userId
     * @param status
     * @return 是否成功
     */
    public boolean setUserStatus(String userId, int status) {
        return userDao.setUserStatus(userId, status);
    }


}
