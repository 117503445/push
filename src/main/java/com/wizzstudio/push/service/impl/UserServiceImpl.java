package com.wizzstudio.push.service.impl;

import com.wizzstudio.push.dao.UserDao;
import com.wizzstudio.push.exception.PushException;
import com.wizzstudio.push.exception.WxUserException;
import com.wizzstudio.push.service.UserService;
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
        return success ? userId : null;
    }

    /**
     * 根据可用的昵称获取userId
     * @param nickname
     * @return 用户id,当该昵称不存在时和状态不可用的时候返回null
     */
    @Override
    public String getUserIdByAbleNickname(String nickname) {
        boolean exist = userDao.checkNicknameExist(nickname);
        if (!exist) throw new PushException(PushException.NICKNAME_NO_EXISTS,"该昵称不存在");
        String userId = userDao.getUserIdByNickname(nickname);
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
        Map<String, Object> nicknamesAndIds = userDao.listNicknamesAndIds();
        //获得迭代器
        Iterator<Map.Entry<String, Object>> iterator = nicknamesAndIds.entrySet().iterator();
        //对昵称和id的关系表进行遍历
        while(iterator.hasNext()){
            Map.Entry<String, Object> entry = iterator.next();
            //获取当前遍历到的昵称(也就是key)
            String nicknameNow = entry.getKey();
            //获取当前遍历到的用户id(也就是value)
            String userIdNow =(String) entry.getValue();
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
        Map<String, Object> nicknamesAndIds = userDao.listNicknamesAndIds();
        //之所以获得昵称和状态的集合是为了避免后续遍历过程中频繁的去redis里面查
        Map<String, Object> nicknamesAndStatus = userDao.listNicknamesAndStatus();
        //获得迭代器
        Iterator<Map.Entry<String, Object>> iterator = nicknamesAndIds.entrySet().iterator();
        //对昵称和id的关系表进行遍历
        while(iterator.hasNext()){
            Map.Entry<String, Object> entry = iterator.next();
            //获取当前遍历到的昵称(也就是key)
            String nicknameNow = entry.getKey();
            //获取当前遍历到的用户id(也就是value)
            String userIdNow =(String) entry.getValue();
            //只有当当前的遍历到的用户id是我们要查的用户id并且该昵称可用的时候,加入该用户的昵称集合
            if (userIdNow.equals(userId) && ((boolean) nicknamesAndStatus.get(nicknameNow) == true)){
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
    public List<String> listNicknameDisabled(String userId) {
        //构建不可用昵称集合
        List<String> nicknames = new LinkedList<>();
        //分别获取昵称和id以及昵称和可用状态的hash表
        Map<String, Object> nicknamesAndIds = userDao.listNicknamesAndIds();
        //之所以获得昵称和状态的集合是为了避免后续遍历过程中频繁的去redis里面查
        Map<String, Object> nicknamesAndStatus = userDao.listNicknamesAndStatus();
        //获得迭代器
        Iterator<Map.Entry<String, Object>> iterator = nicknamesAndIds.entrySet().iterator();
        //对昵称和id的关系表进行遍历
        while(iterator.hasNext()){
            Map.Entry<String, Object> entry = iterator.next();
            //获取当前遍历到的昵称(也就是key)
            String nicknameNow = entry.getKey();
            //获取当前遍历到的用户id(也就是value)
            String userIdNow =(String) entry.getValue();
            //只有当当前的遍历到的用户id是我们要查的用户id并且该昵称不可用的时候,加入该用户的昵称集合
            if (userIdNow.equals(userId) && ((boolean) nicknamesAndStatus.get(nicknameNow) == false)){
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
        boolean exist = userDao.checkNicknameExist(nickname);
        if (exist) throw new WxUserException(WxUserException.NICKNAME_EXISTS,"昵称已被人使用");
        boolean success = userDao.saveNicknameAndId(nickname, userId) && userDao.saveNicknameAndStatus(nickname);
        if (!success) throw new WxUserException(WxUserException.NICKNAME_ADD_ERROR,"新增昵称失败");
        return nickname;
    }

    /**
     * 启用被禁用的昵称
     * @param userId
     * @param nickname
     * @return 启用的昵称
     */
    @Override
    public String enableNickname(String userId, String nickname) {
        return null;
    }

    /**
     * 禁用正在使用的昵称
     * @param userId
     * @param nickname
     * @return 禁用的昵称
     */
    @Override
    public String disableNickname(String userId, String nickname) {
        return null;
    }


}
