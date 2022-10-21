package com.dao;

import com.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.*;

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

    public void jdbcContextWithStatementStrategy(StatementStrategy strategy){
        Connection conn = null;
        PreparedStatement pstmt= null;
        try {
            conn = connectionMaker.makeConnection();
            pstmt = strategy.makePreparedStatement(conn);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {//error가 나도 실행되는 블럭
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public void add(User user) throws SQLException, ClassNotFoundException {
        jdbcContextWithStatementStrategy(new AddStatement(user));
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
        jdbcContextWithStatementStrategy(new DeleteAllStatement());
    }

    public int getCount() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = connectionMaker.makeConnection();
            pstmt = conn.prepareStatement("SELECT count(*) from users");
            rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1);// '1'은 select문에서 첫 번째 항목을 가져오라는 의미
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }





    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        UserDao userDao = new UserDao();
        userDao.add(new User("7","fcsa","12345"));
        //User user = userDao.get("1");
        //System.out.println(user.getName());

    }
}
