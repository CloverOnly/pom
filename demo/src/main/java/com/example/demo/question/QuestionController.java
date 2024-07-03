package com.example.demo.question;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.answer.AnswerForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {
	
//	@GetMapping("question/list.do")
//	public String list() {
//		//templates 을 찾아 입력
//		return "question_list";
//	}
	
//	private final QuestionRepository questionRepository;
	private final QuestionService questionService;
	
	@GetMapping("/list2.do")
	public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
		Page<Question> paging = this.questionService.getList(page);
		model.addAttribute("paging", paging);
		
		return "question_list";
	}
	
	//질문 답변 등록
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";
	}
	
	//등록에는 get,post 다 사용
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		//QuestionForm questionForm 요효성 검사를 위한 
		return "question_form";	
	}
	
	@PostMapping("/create")
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {

		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		//Service 부분
		this.questionService.create(questionForm.getSubject(), questionForm.getContent());
		
		return "redirect:/question/list2.do"; //질문 저장 후 질문 목록으로 이동
	}
	
}
