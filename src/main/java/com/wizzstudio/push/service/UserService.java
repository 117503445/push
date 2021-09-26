package com.wizzstudio.push.service;

import java.util.List;

/**
 * @Author: TheR1sing3un
 * @Date: 2021/09/25/15:42
 * @Description: 用户业务逻辑接口
 */
public interface UserService {

    String initUser(String userId);

    String getUserIdByAbleNickname(String nickname);

    List<String> listNicknames(String userId);

    List<String> listNicknamesAble(String userId);

    List<String> listNicknamesDisabled(String userId);

    String addNickname(String userId,String nickname);

    String enableNickname(String userId,String nickname);

    String disableNickname(String userId,String nickname);

    boolean setUserStatusAdd(String userId);

    boolean setUserStatusEnable(String userId);

    boolean setUserStatusDisable(String userId);

    int getUserStatus(String userId);

    boolean userStatusExists(String userId);

    boolean deleteUserStatus(String userId);
}
