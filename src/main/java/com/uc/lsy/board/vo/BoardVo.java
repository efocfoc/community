package com.uc.lsy.board.vo;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class BoardVo implements Comparable<BoardVo> {
	private int boardNo;
	private String title;
	private String id;
	private String writer;
	private String content;
	private String regdate;
	private int gNo;
	private String gName;
	private int joinMember;
	private int views;
	private Integer point;
	
	long diff = 0;//차이 비교 
	long sec = 1000;
	long min = 1000 * 60;
	long hour = 1000 * 60 * 60;
	long day = 1000 * 60 * 60 * 24;
	long week = day * 7;
	long year = 1000 * 60 * 60 * 24 * 365;
	
	String time = null;
	
	DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public int compareTo(BoardVo vo) {
		return point.compareTo(vo.getPoint());
	}
	
	public BoardVo(String title, String writer, String content, String regdate, String gName) {
		super();
		this.title = title;
		this.writer = writer;
		this.content = content;
		this.setRegdate(regdate);
		this.gName = gName;
	}
	
	public BoardVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getgNo() {
		return gNo;
	}

	public void setgNo(int gNo) {
		this.gNo = gNo;
	}

	public String getgName() {
		return gName;
	}

	public void setgName(String gName) {
		this.gName = gName;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public int getJoinMember() {
		return joinMember;
	}

	public void setJoinMember(int joinMember) {
		this.joinMember = joinMember;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}
	
	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
	
	public String timeCalc(Timestamp date ) { //시간 계산
		
		format.setTimeZone(TimeZone.getTimeZone("KST"));
		
		String now =  format.format(new Date()); //현재 시간
		String dateTime = format.format(date);	//db에서 받아온 값
		
		try {
			Date today = format.parse(now);
			Date test_date = format.parse(dateTime);
			
			diff = today.getTime() - test_date.getTime();
			
			if(diff < min) {
				time = diff / sec + "초";
			}
			else if(diff < hour) {
				time = diff / min + "분";
			}
			else if(diff < day) {
				time = diff / hour + "시간";
			}
			else if(diff < week) {
				time = diff / day + "일";
			}
			else if(diff < year) {
				time = diff / week + "주";
			}
			else {
				time = (diff / day) / 365 + "일";
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return time;
	}
	
	public int point(int view, Timestamp date ) { //인기글 포인트 계산
		int point = 0;
		
		String now =  format.format(new Date()); //현재 시간
		String dateTime = format.format(date);	//db에서 받아온 값
		
		try { //시간따라 포인트
			Date today = format.parse(now);
			Date test_date = format.parse(dateTime);
			
			diff = today.getTime() - test_date.getTime();
			
			if(diff < day) {
				point += 10;
			}
			else if(diff < week) {
				point += 5;
			}
			else {
				point += 2;
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		point += view * 0.5;
		
		return point;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
