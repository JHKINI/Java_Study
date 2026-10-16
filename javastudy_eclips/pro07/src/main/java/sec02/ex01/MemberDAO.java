package sec02.ex01;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	
	private Connection con;
	private Statement stmt;
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
	
	public List listMembers() {
		List list = new ArrayList();
		
		try {
			connDB();
			
			String query = "select * from t_member";
			System.out.println(query);
			
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				java.sql.Date joinDate = rs.getDate("joinDate");
				
				MemberVO vo = new MemberVO();
				vo.setId(id);
				vo.setPwd(pwd);
				vo.setName(name);
				vo.setEmail(email);
				vo.setJoinDate(joinDate);
				
				list.add(vo);
			}
			
			rs.close();
			stmt.close();
			con.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	private void connDB() {
		try {
			con = dataFactory.getConnection();
			System.out.println("커넥션 풀에서 connection 얻기 성공");
			
			stmt = con.createStatement();
			System.out.println("statement 생성 성공");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}