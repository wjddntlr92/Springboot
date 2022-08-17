package com.mysite.sbb.comment;

import java.security.Principal;

import javax.validation.Valid;

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
import com.mysite.sbb.answer.AnswerService;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import groovyjarjarpicocli.CommandLine.Model;
import lombok.RequiredArgsConstructor;


@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {
	
	private final CommentService commentService;
	private final AnswerService answerService;
	private final UserService userService;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{id}")
	public String createComment (
			Model model,
			@PathVariable("id") Integer id,
			@Valid CommentForm commentForm,
			BindingResult bindingResult,
			Principal principal
			) {
		Answer answer = this.answerService.getAnswer(id);
		Question question = answer.getQuestion();
		SiteUser siteUser = this.userService.getUser(principal.getName());
		if(bindingResult.hasErrors()) {
			return String.format("redirect:/question/detail/%s#answer_%s", question.getId(),answer.getId());
		}
		Comment comment = this.commentService.create(answer, commentForm.getContent(), siteUser );
		return String.format("redirect:/question/detail/%s#answer_%s", question.getId(),answer.getId());
	}
	
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String commnetVote(Principal principal, @PathVariable("id") Integer id) {
        Comment comment = this.commentService.getComment(id);
        Answer answer = comment.getAnswer();
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.commentService.vote(comment, siteUser);
        return String.format("redirect:/question/detail/%s#answer_%s", 
                answer.getQuestion().getId(), answer.getId());
    }

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String modifyComment(CommentForm commentForm, @PathVariable("id") Integer id,Principal principal) {
		
		Comment comment = this.commentService.getComment(id);
		if(!comment.getAuthor().getUsername().equals(principal.getName())){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
		}
		commentForm.setContent(comment.getContent());
		System.out.println("dsfs");
		return "comment_form";

	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String modifyComment(
			@Valid CommentForm commentForm,
			BindingResult bindingResult,
			@PathVariable("id") Integer id,
			Principal principal
			) {
		
		Comment comment = this.commentService.getComment(id);
		if(!comment.getAuthor().getUsername().equals(principal.getName())){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
		}
		Answer answer = comment.getAnswer();		
		if(	bindingResult.hasErrors()) {
			
			return "comment_form";
		}
		this.commentService.modify(comment, commentForm.getContent());
		return String.format("redirect:/question/detail/%s",answer.getQuestion().getId());

	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String deleteComment(@PathVariable("id") Integer id, Principal principal ) {
		Comment comment = this.commentService.getComment(id);
		if(!comment.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제권한이 없습니다.");			
		}
		Answer answer = comment.getAnswer();
		this.commentService.delete(comment);
		return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
	}
	
	
}
