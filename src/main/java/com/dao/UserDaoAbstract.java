package com.dao;

import com.domain.User;

import java.sql.*;
import java.util.Map;

public abstract class UserDaoAbstract {

    public abstract Connection makeConnection() throws Exception;
    public void add(User user) throws Exception {


        Connection conn = makeConnection();

        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO users(id,name,password) VALUES(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3,user.getPassword());


        ps.executeUpdate();
        ps.close();
        conn.close();

    }

    public User get(String id) throws Exception {

        Connection conn = makeConnection();
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

       /* UserDaoAbstract userDao = new UserDaoAbstract();
        userDao.add(new User("7","fcsa","12345"));
        //User user = userDao.get("1");
        //System.out.println(user.getName());*/

    }
}
