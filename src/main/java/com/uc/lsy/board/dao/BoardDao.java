package com.uc.lsy.board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import org.springframework.stereotype.Repository;

import com.uc.lsy.board.vo.BoardVo;
import com.uc.lsy.board.vo.GroupsVo;
import com.uc.lsy.board.vo.ReplyVo;
import com.uc.lsy.member.vo.MemberVo;


@Repository("boardDao")
public class BoardDao {
	private Connection conn=null;
	private PreparedStatement pstm=null;
	private ResultSet rs=null; //select문 수행후 결과를 저장하는 객체
	
	private BoardVo Bvo;
	private GroupsVo Gvo;
	private ReplyVo Rvo;
	
	DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	//연결
	public void connectDB() {
		try {String dbURL = "jdbc:mysql://localhost:3306/sp5_1712121?serverTimezone=Asia/Seoul";
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
	
	//모든 게시판 목록을 불러옴
	public ArrayList<BoardVo> getBoardListAll( ) {//board에 있는 목록을 가져옴
		ArrayList<BoardVo> botari = new ArrayList<BoardVo>();
		
		connectDB();
		String SQL = "select * from board, groups where board.gNo = groups.gNo order by boardNo desc";
		Bvo = null;//결과를 담는 그릇 객체
			
		try {
			pstm = conn.prepareStatement(SQL);
			rs=pstm.executeQuery(); //select 문 수행 결과가 rs 테이블에 다 담겨져 있음
			
			while(rs.next() == true) {
				Bvo = new BoardVo();
				Bvo.setBoardNo(rs.getInt("boardNo"));
				Bvo.setTitle(rs.getString("title"));
				Bvo.setWriter(rs.getString("writer"));
				Bvo.setContent(rs.getString("content"));
				Bvo.setgName(rs.getString("gName"));

				String time = Bvo.timeCalc(rs.getTimestamp("regdate"));
				Bvo.setRegdate(time);
				
				Bvo.setPoint(Bvo.point(rs.getInt("views"), rs.getTimestamp("regdate")));
				
				botari.add(Bvo); 
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		closeDB();
		return botari;
	}

	//id별 게시판 가져옴
	public ArrayList<BoardVo> getBoardListUser(String id) {//board에 있는 목록을 가져옴
		ArrayList<BoardVo> botari = new ArrayList<BoardVo>();
		
		connectDB();
		String SQL = "select * from board, groups "
				+ "where ( writer=(select id from member where id = ?) or board.gNo in (select gNo from follow where id = ?)) "
				+ "and board.gNo = groups.gNo "
				+ "order by boardNo desc";
		Bvo = null;//결과를 담는 그릇 객체
		
		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setString(1, id);
			pstm.setString(2, id);
			rs=pstm.executeQuery(); //select 문 수행 결과가 rs 테이블에 다 담겨져 있음
			
			while(rs.next() == true) {
				Bvo = new BoardVo();
				Bvo.setBoardNo(rs.getInt("boardNo"));
				Bvo.setTitle(rs.getString("title"));
				Bvo.setWriter(rs.getString("writer"));
				Bvo.setContent(rs.getString("content"));
				Bvo.setgName(rs.getString("gName"));
				
				String time = Bvo.timeCalc(rs.getTimestamp("regdate"));
				Bvo.setRegdate(time);
				
				Bvo.setPoint(Bvo.point(rs.getInt("views"), rs.getTimestamp("regdate")));
				
				botari.add(Bvo); 
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		closeDB();
		return botari;
	}
	
	
	//디테일한 게시글 detail post
	public BoardVo getContent(int no) {
		connectDB();
		String SQL = "select content, boardNo, board.gNo, views, title, writer, board.regdate, groups.gName, count(follow.id)" + 
				"from board, groups, follow where boardNo = ? and board.gNo = groups.gNo and follow.gNo=board.gNo;";
		Bvo = null;//결과를 담는 그릇 객체
		
			
		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setInt(1, no);
			
			
			rs=pstm.executeQuery(); //select 문 수행시 	
			//결과 출력
			if(rs.next() == true) { //결과가 있음 >> 회원이 맞다
				Bvo = new BoardVo();
				Bvo.setContent(rs.getString("content"));
				Bvo.setTitle(rs.getString("title"));
				Bvo.setWriter(rs.getString("writer"));
				Bvo.setRegdate(format.format(rs.getTimestamp("regdate")));
				Bvo.setBoardNo(rs.getInt("boardNo"));
				Bvo.setgNo(rs.getInt("gNo"));
				Bvo.setgName(rs.getString("gName"));
				Bvo.setViews(rs.getInt("views"));
				Bvo.setJoinMember(rs.getInt("count(follow.id)"));
				System.out.println("aa" + rs.getInt("count(follow.id)"));
			}
			else { //회원아님
				//System.out.println("그런 사람은 없음....다시 입력 하세요");
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		closeDB();
		return Bvo;
	}
	
	//아이디 별 그룹 목록 가져옴 get groups
	public ArrayList<GroupsVo> getIdGroups(String id) {//board에 있는 목록을 가져옴
		ArrayList<GroupsVo> botari = new ArrayList<GroupsVo>();
			
		connectDB();
		String SQL = "select * from groups, follow where groups.gNo = follow.gNo and id= ?";
		Gvo = null;//결과를 담는 그릇 객체
			
		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setString(1, id);
				
			rs=pstm.executeQuery(); //select 문 수행 결과가 rs 테이블에 다 담겨져 있음
			
			while(rs.next() == true) {
				Gvo = new GroupsVo();
				Gvo.setgName(rs.getString("gName"));
				Gvo.setgNo(rs.getInt("groups.gNo"));
					
				botari.add(Gvo); 
			}
				
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		closeDB();
		return botari;
	}
	
	//그룹별 게시판 group board
		public ArrayList<BoardVo> getGroupsBoard(int gNo) {//board에 있는 목록을 가져옴
			ArrayList<BoardVo> botari = new ArrayList<BoardVo>();
				
			connectDB();
			String SQL = "select * from groups, board where groups.gNo = board.gNo and board.gNo = ? order by boardNo desc";
			Bvo = null;//결과를 담는 그릇 객체
				
			try {
				pstm = conn.prepareStatement(SQL);
				pstm.setInt(1, gNo);
					
				rs=pstm.executeQuery(); //select 문 수행 결과가 rs 테이블에 다 담겨져 있음
				
				while(rs.next() == true) {
					Bvo = new BoardVo();
					Bvo.setBoardNo(rs.getInt("boardNo"));
					Bvo.setTitle(rs.getString("title"));
					Bvo.setWriter(rs.getString("writer"));
					Bvo.setContent(rs.getString("content"));
					Bvo.setgName(rs.getString("gName"));
					Bvo.setgNo(rs.getInt("groups.gNo"));
					
					String time = Bvo.timeCalc(rs.getTimestamp("regdate"));
					Bvo.setRegdate(time);
					
					botari.add(Bvo); 
				}
					
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
				
			closeDB();
			return botari;
		}
		
	//그룹 별 팔로우
		public BoardVo getFollow(int gNo) {
			connectDB();
			String SQL = "select groups.gNo, gName, count(follow.id), regdate, id  from follow, groups where follow.gNo = groups.gNo and groups.gNo = ?";
			Bvo = null;//결과를 담는 그릇 객체
			
			format.setTimeZone(TimeZone.getTimeZone("KST"));	
				
			try {
				pstm = conn.prepareStatement(SQL);
				pstm.setInt(1, gNo);
				
				
				rs=pstm.executeQuery(); //select 문 수행시 	
				//결과 출력
				if(rs.next() == true) { //결과가 있음 >> 회원이 맞다
					Bvo = new BoardVo();
					Bvo.setgNo(rs.getInt("groups.gNo"));
					Bvo.setgName(rs.getString("gName"));
					Bvo.setJoinMember(rs.getInt("count(follow.id)"));
					Bvo.setRegdate(rs.getDate("regdate").toString());
					
					
				}
				else { //회원아님
					//System.out.println("그런 사람은 없음....다시 입력 하세요");
					
				}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			closeDB();
			return Bvo;
		}	
		
		
		
	
	//게시판 수정 update
	public void UpdatePost(int bNo, String title, String content) { 
		
		connectDB();
		String SQL = "update board set title = ?, content = ? where boardNo = ?";

		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setString(1, title);
			pstm.setString(2, content);
			pstm.setInt(3, bNo);

			pstm.executeUpdate();	//insert, update, delete 문에 사용		

		} catch (SQLException e) {
			e.printStackTrace();
		}

		closeDB();

	}
	
	//게시삭제 delete
	public void DeletePost(int bNo) { 
		
		connectDB();
		String SQL = "delete from board where boardNo = ?";
		
		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setInt(1, bNo);
			
			pstm.executeUpdate();	//insert, update, delete 문에 사용		
			} catch (SQLException e) {
				e.printStackTrace();
			}

		closeDB();

	}
	
	//조회수 증가
	public void ViewCount(int bNo) { 
		
		connectDB();
		String SQL = "update board set views = views + 1 where boardNo = ?";
		
		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setInt(1, bNo);
			
			pstm.executeUpdate();	//insert, update, delete 문에 사용		
			} catch (SQLException e) {
				e.printStackTrace();
			}

		closeDB();

	}
	
	//검색 >> 게시판
	public ArrayList<BoardVo> getBoardSearch(String search) {//board에 있는 목록을 가져옴
		ArrayList<BoardVo> botari = new ArrayList<BoardVo>();
		
		connectDB();
		String SQL = "select * from board, groups where title like ? or content like ? && board.gNo = groups.gNo";
		Bvo = null;//결과를 담는 그릇 객체
		
		format.setTimeZone(TimeZone.getTimeZone("KST"));	
			
		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setString(1, "%" + search + "%");
			pstm.setString(2, "%" + search + "%");
			
			rs=pstm.executeQuery(); //select 문 수행 결과가 rs 테이블에 다 담겨져 있음
			
			while(rs.next() == true) {
				Bvo = new BoardVo();
				Bvo.setgName(rs.getString("gName"));
				Bvo.setTitle(rs.getString("title"));
				Bvo.setWriter(rs.getString("writer"));
				Bvo.setContent(rs.getString("content"));
				Bvo.setRegdate(format.format(rs.getTimestamp("regdate")));
				Bvo.setBoardNo(rs.getInt("boardNo"));
				
				botari.add(Bvo); 
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		closeDB();
		return botari;
	}
	
	//검색 >> 그룹
	public ArrayList<GroupsVo> getGroupsSearch(String search) {//board에 있는 목록을 가져옴
		ArrayList<GroupsVo> botari = new ArrayList<GroupsVo>();
		
		connectDB();
		String SQL = "select * from groups where gName like ?";
		Gvo = null;//결과를 담는 그릇 객체
			
		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setString(1, "%" + search + "%");
			
			rs=pstm.executeQuery(); //select 문 수행 결과가 rs 테이블에 다 담겨져 있음
			
			while(rs.next() == true) {
				Gvo = new GroupsVo();
				Gvo.setgName(rs.getString("gName"));
				Gvo.setgNo(rs.getInt("gNo"));
				
				botari.add(Gvo); 
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		closeDB();
		return botari;
	}
	
	
	
	//글쓰기 write
	public void boardPost(String id, String title, String content, int no) { 
		
		connectDB();
		String SQL = "insert into board (title, writer, content, regdate, gNo) values(?,?,?, now(), ?)";

		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setString(1, title);
			pstm.setString(2, id);
			pstm.setString(3, content);
			pstm.setInt(4, no);

			pstm.executeUpdate();	//insert, update, delete 문에 사용		

		} catch (SQLException e) {
			e.printStackTrace();
		}

		closeDB();

	}
	
	//댓글 reply
	public void ReplyInsert(String id, String content, int bNo) { 
			
		connectDB();
		String SQL = "insert into reply (id, content, bNo, regdate) values(?,?,?,now())";
		
		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setString(1, id);
			pstm.setString(2, content);
			pstm.setInt(3, bNo);
			pstm.executeUpdate();	//insert, update, delete 문에 사용		
			} catch (SQLException e) {
				
				
				e.printStackTrace();
			}

		closeDB();

	}
	
	//댓글 불러옴 reply 
	public ArrayList<ReplyVo> getReply(int bNo) {
		ArrayList<ReplyVo> botari = new ArrayList<ReplyVo>();
			
		connectDB();
		String SQL = "select * from reply,member where bNo = ? and reply.id = member.id";
		Gvo = null;//결과를 담는 그릇 객체
		
		format.setTimeZone(TimeZone.getTimeZone("KST"));	
			
		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setInt(1, bNo);
				
			rs=pstm.executeQuery(); //select 문 수행 결과가 rs 테이블에 다 담겨져 있음
			
			while(rs.next() == true) {
				Rvo = new ReplyVo();
				Rvo.setName(rs.getString("nickname"));
				Rvo.setContent(rs.getString("content"));
				Rvo.setRegdate(format.format(rs.getTimestamp("regdate")));
				Rvo.setbNo(rs.getInt("reply.bNo"));
					
				botari.add(Rvo); 
			}
				
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		closeDB();
		return botari;
	}
	
	//댓글 reply delete
		public void DeleteReply(int bNo, String id) { 
			
			connectDB();
			String SQL = "delete from reply where bNo = ? and id = ?";
			
			try {
				pstm = conn.prepareStatement(SQL);
				pstm.setInt(1, bNo);
				pstm.setString(2, id);
				
				pstm.executeUpdate();	//insert, update, delete 문에 사용		
				} catch (SQLException e) {
					e.printStackTrace();
				}

			closeDB();

		}
		
	//아이디 비밀번호 일치
	public BoardVo FollowCheck(String id, int gno) {
		connectDB();
		String SQL = "select * from member,follow where follow.id = ? and follow.gno = ?  and follow.id = member.id";
		Bvo = null;//결과를 담는 그릇 객체
						
		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setString(1, id);
			pstm.setInt(2, gno);
						
			rs=pstm.executeQuery(); //select 문 수행시 	
			//결과 출력
			if(rs.next() == true) { //결과가 있음 >>
				Bvo = new BoardVo();
				Bvo.setId(rs.getString("id"));
			}
			else { 
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
					
		closeDB();
		return Bvo;
	}
	
	//팔로우 follow
	public void follow(String id, int gNo) { 
				
		connectDB();
			String SQL = "insert into follow values( ? , ?, now());";
			
			try {
				pstm = conn.prepareStatement(SQL);
				pstm.setString(1, id);
				pstm.setInt(2, gNo);
				pstm.executeUpdate();	//insert, update, delete 문에 사용		
				} catch (SQLException e) {
					
					e.printStackTrace();
				}

			closeDB();

		}
		
	//unfollow
	public void Unfollow(String id, int gNo) { 
					
		connectDB();
		String SQL = "delete from follow where gno = ? and id = ?";
					
		try {
			pstm = conn.prepareStatement(SQL);
			pstm.setInt(1, gNo);
			pstm.setString(2, id);
						
			pstm.executeUpdate();	//insert, update, delete 문에 사용		
		} catch (SQLException e) {
			e.printStackTrace();
		}

		closeDB();

		}

}
