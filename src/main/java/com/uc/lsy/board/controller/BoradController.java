package com.uc.lsy.board.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;
import com.uc.lsy.board.dao.BoardDao;
import com.uc.lsy.board.vo.BoardVo;
import com.uc.lsy.board.vo.GroupsVo;
import com.uc.lsy.board.vo.ReplyVo;

@Controller
public class BoradController {
	
	HttpSession session;
	
	@Resource(name="boardDao")
	private BoardDao boardDao = new BoardDao();
	
	private BoardVo boardvo = new BoardVo();
	private GroupsVo groupsvo = new GroupsVo();
	private ReplyVo replyvo = new ReplyVo();
	
	/*게시판*/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String boardList(HttpServletRequest request, Model model) { // 여기에 모델 써줘야 넘기기 가능
		
		session = request.getSession();
		String id = (String)session.getAttribute("id");
		ArrayList<BoardVo> boardBotari;
		if(id == null) {
			boardBotari = boardDao.getBoardListAll(); // 보더테이ㅍ블에서 모든 내용 가져옴
		}
		else {
			boardBotari = boardDao.getBoardListUser(id);
		}
		model.addAttribute("boardList", boardBotari); //결과를 모델이 담은고임

		return "main/main-main";

	}
	
	/*게시판 상세보기 post detail*/
	@RequestMapping(value = "/post.do")
	public String boardDetail(HttpServletRequest request, Model model) { // 여기에 모델 써줘야 넘기기 가능
		
		int bno = Integer.parseInt(request.getParameter("no"));
		session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		boardDao.ViewCount(bno); //조회수 증가
		boardvo = boardDao.getContent(bno); //목록 불러옴
		
		ArrayList<ReplyVo> boardBotari = boardDao.getReply(bno);
		
		
		model.addAttribute("vo", boardvo);
		model.addAttribute("Rvo", boardBotari);
		
		int gno = boardvo.getgNo();
		
		if(id != null){
			boardvo = boardDao.FollowCheck(id, bno);
			model.addAttribute("checkfollow", boardvo);

		}
		
		
		
		
		return "post/post-main";

	}
	
	/*게시판 수정 update*/
	@RequestMapping(value = "/update.do")
	public String update(HttpServletRequest request, Model model) { // 여기에 모델 써줘야 넘기기 가능
		
		int bno = Integer.parseInt(request.getParameter("no"));
		
		boardvo = boardDao.getContent(bno); //목록 불러옴
		
		ArrayList<ReplyVo> boardBotari = boardDao.getReply(bno);
		
		
		model.addAttribute("vo", boardvo);
		model.addAttribute("Rvo", boardBotari);

		
		return "post/create-main";

	}
	
	/*글쓰기 입력 input board*/
	@RequestMapping(value = "updatePost")
	@ResponseBody
	public void updateOk(BoardVo vo, HttpServletRequest request) { // 여기에 모델 써줘야 넘기기 가능
		
		int bNo = vo.getBoardNo();
		String title = vo.getTitle();
		String content = vo.getContent();
		
		content = content.replace("\r\n", "<br>");
		
		boardDao.UpdatePost(bNo, title, content);
		
		//return "redirect:/";
	}
	
	/*글 삭제 delete*/
	@RequestMapping(value = "/delete.do")
	public String delete(HttpServletRequest request, Model model) { // 여기에 모델 써줘야 넘기기 가능
		
		int bno = Integer.parseInt(request.getParameter("no"));
		
		System.out.println("aa" + bno);
		
		boardDao.DeletePost(bno);
		
		return "redirect:/";

	}
	
	/*
	 * 
	 * 
	 * sort
	 * 
	 * 
	 * */
	
	/*모든 게시판 정렬 sort*/
	@RequestMapping(value = "/SortAll.do")
	public String SortAll(HttpServletRequest request, Model model) { // 여기에 모델 써줘야 넘기기 가능
		
		ArrayList<BoardVo> boardBotari;
		
		boardBotari = boardDao.getBoardListAll(); // 보더테이ㅍ블에서 모든 내용 가져옴
		model.addAttribute("boardList", boardBotari);

		return "main/main-main";

	}
	
	/*새로운 게시판 정렬 sort*/
	@RequestMapping(value = "/SortNew.do")
	public String SortNew(HttpServletRequest request, Model model) { // 여기에 모델 써줘야 넘기기 가능
		
		return "redirect:/";

	}
	
	/*게시판 정렬 인기글 sortHot*/
	@RequestMapping(value = "/SortHot.do")
	public String SortHot(HttpServletRequest request, Model model) { // 여기에 모델 써줘야 넘기기 가능
		
		session = request.getSession();
		String id = (String)session.getAttribute("id");
		ArrayList<BoardVo> boardBotari;
		if(id == null) {
			boardBotari = boardDao.getBoardListAll(); // 보더테이ㅍ블에서 모든 내용 가져옴
			Collections.sort(boardBotari);
			Collections.reverse(boardBotari);
		}
		else {
			boardBotari = boardDao.getBoardListUser(id);
			Collections.sort(boardBotari);
			Collections.reverse(boardBotari);
		}
		model.addAttribute("boardList", boardBotari); //결과를 모델이 담은고임

		
		return "main/main-main";

	}
	
	
	
	/*
	 * 
	 *  Search
	 * 
	 * */
	
	/*검색*/
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String Search(HttpServletRequest request, Model model) throws UnsupportedEncodingException { // 여기에 모델 써줘야 넘기기 가능
		
		String search = request.getParameter("search");
		String encode = URLEncoder.encode(search,"UTF-8");
		System.out.println(search);
		
		if(!search.equals("")) {
			//arraylist에 담아줌
			ArrayList<BoardVo> BboardSearch = boardDao.getBoardSearch(search);
			ArrayList<GroupsVo> GboardSearch = boardDao.getGroupsSearch(search);
			
			model.addAttribute("search", search);
			model.addAttribute("encoding", encode);
			
			model.addAttribute("BboardSearch", BboardSearch); //board
			model.addAttribute("GboardSearch", GboardSearch); //groups
			
		}
		
		return "search/search-main";

	}
	
	
	/*
	 * 
	 *  Create
	 * 
	 * */
	
	/*글쓰기창으로 이동  move create-main*/
	@RequestMapping(value = "/submit.do")
	public String Submit(HttpServletRequest request, Model model) { // 여기에 모델 써줘야 넘기기 가능
		
		session = request.getSession();
		String id = (String)session.getAttribute("id");
		ArrayList<GroupsVo> getGroups = boardDao.getIdGroups(id);
		
		model.addAttribute("getGroups", getGroups);
		
		return "post/create-main";

	}
	
	/*글쓰기 입력 input board*/
	@RequestMapping(value = "submitPost")
	@ResponseBody
	public void SubmitOk(BoardVo vo, HttpServletRequest request) { // 여기에 모델 써줘야 넘기기 가능
		
		session = request.getSession();
		String id = (String)session.getAttribute("id");
		String title = vo.getTitle();
		String content = vo.getContent();
		int no = vo.getgNo();
		content = content.replace("\r\n", "<br>");
		
		System.out.println(no);
		
		boardDao.boardPost(id, title, content, no);
		
		//return "redirect:/";
	}
	
	/*댓글 reply*/
	@RequestMapping(value = "/reply.do")
	public String  Reply(HttpServletRequest request) { // 여기에 모델 써줘야 넘기기 가능
		int bNo = Integer.parseInt(request.getParameter("no"));
		String id = (String)session.getAttribute("id");
		String content = request.getParameter("reply");
		
		boardDao.ReplyInsert(id, content, bNo);
		
		return "redirect:/post.do?no=" + bNo;
	}

	/*댓글삭제 reply delete*/
	@RequestMapping(value = "/deletereply.do")
	public String deletereply(HttpServletRequest request, Model model) { // 여기에 모델 써줘야 넘기기 가능
		
		session = request.getSession();
		String id = (String)session.getAttribute("id");
		int bno = Integer.parseInt(request.getParameter("no"));
		
		boardDao.DeleteReply(bno, id);
		
		return "redirect:/post.do?no=" + bno;

	}
	
	/*
	 * 
	 * groups
	 * 
	 * */
	@RequestMapping(value = "/groups.do")
	public String groups(HttpServletRequest request, Model model) { // 여기에 모델 써줘야 넘기기 가능
		
		int gno = Integer.parseInt(request.getParameter("no"));
		String id = (String)session.getAttribute("id");
		
		ArrayList<BoardVo> boardBotari;
		
		boardBotari = boardDao.getGroupsBoard(gno); // 보더테이ㅍ블에서 모든 내용 가져옴
		model.addAttribute("GboardList", boardBotari); //결과를 모델이 담은고임

		boardvo = boardDao.getFollow(gno);
		model.addAttribute("follow", boardvo);
		
		if(id != null){
			boardvo = boardDao.FollowCheck(id, gno);
			model.addAttribute("checkfollow", boardvo);
		}

		
		
		return "groups/groups-main";

	}
	
	/*
	 * 
	 * follow
	 * 
	 * */
	
	@RequestMapping(value = "/follow.do")
	public String Follow(HttpServletRequest request) {
		int gno = Integer.parseInt(request.getParameter("no"));
		String id = (String)session.getAttribute("id");
		
		if(id != null){
			boardDao.follow(id, gno);
		}
		
		
		return "redirect:/groups.do?no=" + gno;
	}
	
	@RequestMapping(value = "/unfollow.do")
	public String unFollow(HttpServletRequest request) { 
		int gno = Integer.parseInt(request.getParameter("no"));
		String id = (String)session.getAttribute("id");
		
		if(id != null){
			boardDao.Unfollow(id, gno);
		}
		
		return "redirect:/groups.do?no=" + gno;
	}
}
