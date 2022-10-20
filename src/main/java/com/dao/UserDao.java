package com.dao;

import com.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

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
        User user= null;
        if(rs.next()){//다음 행이 존재하면 rs.next()는 true를 리턴
            user = new User(rs.getString("id"),
                    rs.getString("name"), rs.getString("password"));
        }
        rs.close();
        ps.close();
        conn.close();

        //없으면 execption
        if(user==null) throw new EmptyResultDataAccessException(1);
        return user;
    }

    public void deleteAll() throws SQLException {
        Connection conn = connectionMaker.makeConnection();
        PreparedStatement pstmt=conn.prepareStatement("DELETE FROM users");
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }

    public int getCount() throws SQLException {
        Connection conn = connectionMaker.makeConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT count(*) from users");
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);// '1'은 select문에서 첫 번째 항목을 가져오라는 의미
        rs.close();
        pstmt.close();
        conn.close();
        return count;
    }





    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        UserDao userDao = new UserDao();
        userDao.add(new User("7","fcsa","12345"));
        //User user = userDao.get("1");
        //System.out.println(user.getName());

    }
}
