package com.mysite.sbb.user;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.answer.AnswerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	private final AnswerService answerService;
	
	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {		
		return "signup_form";				
	}
	
	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "signup_form";
		}
		
		if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2","passwordInCorrect" , "패스워드가 일치하지 않습니다.");
			return "signup_form";
		}
		
		try {
		userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
				
		}
		//DataIntegrityViolationExceptio 중복된 값오류를 의미
		catch(DataIntegrityViolationException e){
			e.printStackTrace();
			bindingResult.reject("signupFailed","이미등록된 사용자입니다");
			return"signup_form";
		}
		catch(Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed",e.getMessage());
			return"signup_form";
		}	
		
		return "redirect:/";
	}
	
	 @GetMapping("/login")
	 public String login() {
		 return "login_form";
	    }
	 
	 @PreAuthorize("isAuthenticated()")
	 @PostMapping("/modify/{id}")
	 public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
	        @PathVariable("id") Integer id, Principal principal) {
		 if (bindingResult.hasErrors()) {
			 return "answer_form";
	     }
	        Answer answer = this.answerService.getAnswer(id);
	        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
	        }
	        this.answerService.modify(answer, answerForm.getContent());
	        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
	    }

}
