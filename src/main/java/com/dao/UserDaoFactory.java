package com.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //스프링은 configuration에서 시작함(스프링 설정을 담당하는 클래스)
public class UserDaoFactory {
    @Bean
    public UserDao awsUserDao(){
        UserDao userDao = new UserDao(new AwsConnectionMaker());
        return userDao;
    }
    @Bean
    public UserDao localUserDao(){
        UserDao userDao = new UserDao(new LocalConnectionMaker());
        return userDao;
    }
}
