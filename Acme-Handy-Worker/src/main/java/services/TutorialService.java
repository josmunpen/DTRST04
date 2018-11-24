
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.TutorialRepository;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {

	//Repository
	@Autowired
	public TutorialRepository	tutorialRepository;


	//Services

	//Constructor
	public TutorialService() {
		super();
	}

	//Complex methods
	//47.2
	public Collection<Tutorial> findAll() {
		Collection<Tutorial> res;
		res = this.tutorialRepository.findAll();
		return res;
	}
}
