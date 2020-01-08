package com.uc.lsy.member.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.uc.lsy.member.vo.MemberVo;

@Repository("memberDao")
public class MemberDao {
	private Connection conn=null;
	private PreparedStatement pstm=null;
	private ResultSet rs=null; //select문 수행후 결과를 저장하는 객체
	
	private MemberVo vo;
	
	//연결
	public void connectDB() {
		try {String dbURL = "jdbc:mysql://localhost:3306/sp5_1712121?serverTimezone=UTC";
			String dbID = "spring5";
			String dbPassword = "spring5";
			
			//jdbc 드라이브 로딩
			//Class.forName("com.mysql.jdbc.Driver"); //MySQL 5.xx 까지 사용
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//DB 연결
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
			
			System.out.println("DB 연결 OK");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("DB 연결 실패");
		}
	}
	
	//닫기
	public void closeDB() {
		try {
			if(pstm != null)pstm.close();
			if(rs != null)rs.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//아이디 비밀번호 일치
	public MemberVo checkMember(String id, String pwd) {
		connectDB();
		String SQL = "select * from member where id = ? and pwd = ?";
		vo = null;//결과를 담는 그릇 객체
			
		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setString(1, id);
			pstm.setString(2, pwd);
			
			
			rs=pstm.executeQuery(); //select 문 수행시 	
			//결과 출력
			if(rs.next() == true) { //결과가 있음 >>
				vo = new MemberVo();
				vo.setId(rs.getString("id"));
				vo.setNickname(rs.getString("nickname"));
			}
			else { //회원아님
				//System.out.println("그런 사람은 없음....다시 입력 하세요");
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		closeDB();
		return vo;
	}
	
	//회원가입
	public void join(String id, String pwd, String nickname) { //회원 1명 db등록(회원가입)
		
		connectDB();//
		
		String SQL = "insert into member(id, pwd, nickname, joindate) values(?,?,?, now())";
		
		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setString(1, id);
			pstm.setString(2, pwd);
			pstm.setString(3, nickname);
					
			pstm.executeUpdate();	//insert, update, delete 문에 사용		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeDB();
	}
	
	//아이디 체크
	public boolean IdCheck(String id) {
		boolean check = false;
		connectDB();
		
		String SQL = "select * from member where id = ?";
		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setString(1, id);
				
			rs = pstm.executeQuery(); //select�� ���
			while(rs.next() == true) {
				check = true;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
			
		closeDB();
		
		return check;
	}
	
	//닉네임 체크
	public boolean NicknameCheck(String nickname) {
		boolean check = false;
		connectDB();
		
		String SQL = "select * from member where nickname = ?";
		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setString(1, nickname);
				
			rs = pstm.executeQuery(); //select�� ���
			while(rs.next() == true) {
				check = true;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
			
		closeDB();
		
		return check;
	}
	
}
