package com.wizzstudio.push.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: TheR1sing3un
 * @Date: 2021/09/25/19:27
 * @Description: 用户数据交互层的接口
 */
public interface UserDao {

    boolean checkNicknameExist(String nickname);

    Map<String,Object> listNicknamesAndIds();

    Map<String,Object> listNicknamesAndStatus();

    String getUserIdByNickname(String nickname);

    boolean getStatusByNickname(String nickname);

    boolean updateStatusByNickname(String nickname,boolean status);

    boolean saveNicknameAndId(String nickname,String userId);

    boolean saveNicknameAndStatus(String nickname);
}
