package com.wangyang.web.Service;

import com.wangyang.web.Dao.UserDao;
import com.wangyang.web.Model.User;
import com.wangyang.web.Util.MD5Util;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Override
    public User checkUser(String username, String password) {
        User user = userDao.findByUsernameAndPassword(username, MD5Util.code(password));
        return user;
    }

    //游客注册
	@Override
	public User saveUser(User user) {
		user.setId(null);//id自增
		String md5pwd = MD5Util.code(user.getPassword());
		user.setPassword(md5pwd);
		user.setAvatar("http://pic.yupoo.com/weikisa/3b98e7c8/6986ce35.jpeg");//设置一个默认头像
		user.setType("0");
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		
		return userDao.save(user);
	}
	
	
	@Override
	public User findUserById(Long id){
		return userDao.getOne(id);
	}
}
