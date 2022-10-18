package com.dao;

import com.domain.User;

import java.sql.*;
import java.util.Map;

public class UserDao {
    private ConnectionMaker connectionMaker; //인터페이스

    public UserDao() {
        this.connectionMaker = new AwsConnectionMaker();
    }
    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
    //생성자 생성될 때 파라미터로 인터페이스 가 들어간다.
    //ConnectionMaker connectionMaker = new AwsConnectionMaker() <-업캐스팅

    //AwsConnectionMaker awsConnectionMaker = new AwsConnectionMaker();
    public void add(User user) throws SQLException, ClassNotFoundException {

        Connection conn = connectionMaker.makeConnection();



        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO users(id,name,password) VALUES(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3,user.getPassword());


        ps.executeUpdate();
        ps.close();
        conn.close();

    }

    public User get(String id) throws ClassNotFoundException, SQLException {

        Connection conn = connectionMaker.makeConnection();


        PreparedStatement ps = conn.prepareStatement(
                "SELECT id, name, password FROM users WHERE id = ?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User(rs.getString("id"),
                rs.getString("name"), rs.getString("password"));



        rs.close();
        ps.close();
        conn.close();
        return user;

    }





    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        UserDao userDao = new UserDao();
        userDao.add(new User("7","fcsa","12345"));
        //User user = userDao.get("1");
        //System.out.println(user.getName());

    }
}
