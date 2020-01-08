package com.uc.lsy.member.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uc.lsy.member.dao.MemberDao;
import com.uc.lsy.member.vo.MemberVo;

@Controller
public class MemberController {
	
	@Resource(name="memberDao")
	private MemberDao memberDao = new MemberDao();
	
	private MemberVo memberVo = new MemberVo();
	HttpSession session;
	
	//로그아웃
	@RequestMapping("/logout.do")
	public String logOut(HttpServletRequest req) {
		
		 HttpSession session = req.getSession();
		 if(session!=null) {
		     try{session.invalidate();}
		     catch(Exception e){
		    	 session.removeAttribute("id");
			     session.removeAttribute("name");
		     } // <-- !!!
		     
		 }
		
		
		System.out.println("로그아웃");
		
		return "redirect:/";
	}
	
	//로그인
	@RequestMapping(value = "login", method=RequestMethod.POST)
	@ResponseBody
	public int loginOk(MemberVo vo, HttpServletRequest request) {
		
		StringBuffer url = request.getRequestURL();
		System.out.println(url);
		
		String id = vo.getId();
		String pwd = vo.getPwd();
		
		vo = memberDao.checkMember(id, pwd);
		
		if(vo != null) {
			String nickname = vo.getNickname();
			session = request.getSession();
			session.setAttribute("id", id);
			session.setAttribute("name", nickname);
			return 1;
		}else {
			System.out.println("로그인실패");
			return 0;
		}
		
	}
	
	//회원가입
	@RequestMapping(value = "join", method=RequestMethod.POST)
	@ResponseBody
	public int Join(MemberVo vo, HttpServletRequest request) {
			
		String id = vo.getId();
		String pwd = vo.getPwd();
		String nickname = vo.getNickname();
		
		boolean idcheck = memberDao.IdCheck(id);
		boolean nicknamecheck = memberDao.NicknameCheck(nickname);
		
		if(idcheck == true) {
			return 0;
		}
		else if(nicknamecheck == true) {
			return 1;
		}
		else {
			memberDao.join(id, pwd, nickname);
			return 2;
		}
	}//join end
	
	//내정보
	@RequestMapping("/myProfile.do")
	public String profile() {
		
		
		return "infoManagement/profile-main";
	}
	
}
