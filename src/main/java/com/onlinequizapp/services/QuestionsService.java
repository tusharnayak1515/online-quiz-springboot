package com.onlinequizapp.services;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlinequizapp.entities.Question;
import com.onlinequizapp.repositories.QuestionsRepo;

@Service
public class QuestionsService {
	
	@Autowired
	private QuestionsRepo questionsRepo;

	public Question save(Question entity) {
		return questionsRepo.save(entity);
	}
	
	public Question update(Question questionDto) {
		Optional<Question> question = findById(questionDto.getQuestionid());
		if(question.isPresent()) {
			Question myquestion = question.get();
			return questionsRepo.save(myquestion);
		}
		return null;
	}

	public void deleteById(int id) {
		Optional<Question> question = findById(id);
		if(question.isPresent()) {
			Question myquestion = question.get();
			questionsRepo.delete(myquestion);			
		}
	}

	public Optional<Question> findById(int id) {
        return questionsRepo.findById(id);
    }
	
	public List<Question> findAll() {
		return questionsRepo.findAll();
	}
    
}

