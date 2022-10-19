package com;

import com.dao.AWSUserDaoImpl;
import com.dao.AwsConnectionMaker;
import com.dao.UserDao;
import com.dao.UserDaoFactory;
import com.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class UserDaoTest {
    @Test
    void addAndSelect() throws Exception {
        //UserDao userDao = new UserDao(new AwsConnectionMaker());
        UserDao userDao = new UserDaoFactory().awsUserDao();
        String id = "15";
        //userDao.add(new User(id,"raddar","1111"));
        //User user = userDao.get(id);
        //Assertions.assertEquals("raddar",user.getName());
        Assertions.assertEquals("raar",userDao.get("11").getName());

    }



}