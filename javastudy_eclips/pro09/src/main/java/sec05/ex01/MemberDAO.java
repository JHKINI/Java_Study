package sec05.ex01;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {

    private Connection con;
    private Statement stmt;
    private PreparedStatement pstmt;
    private DataSource dataFactory;

    public MemberDAO() {
        try {
            Context ctx = new InitialContext();
            Context envContext = (Context) ctx.lookup("java:/comp/env");
            dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean isExisted(MemberVO memberVO) {
    	boolean result =false;
    	String id = memberVO.getId();
    	String pwd = memberVO.getPwd();
    	
    	try {
    		con = dataFactory.getConnection();
    		String query = "select decode(count(*), 1, 'true', 'false') as result from t_member";
    			query += " where id=? and pwd=?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, id);
            pstmt.setString(2, pwd);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            result = Boolean.parseBoolean(rs.getString("result"));
            System.out.println("result =" + result);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public void delMember(String id) {
    	try{
    		con = dataFactory.getConnection();
    		String query = "delete from t_member where id=?";
    		System.out.println("prepateStatement:"+query);
    		pstmt = con.prepareStatement(query);
    		pstmt.setString(1, id);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }
    private void connDB() {
        try {
            con = dataFactory.getConnection();
            System.out.println("커넥션 풀에서 connection 얻기 성공");

            stmt = con.createStatement();
            System.out.println("statement 생성 성공");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}