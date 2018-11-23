
package services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.LoginService;
import security.UserAccount;
import domain.HandyWorker;

@Service
@Transactional
public class HandyWorkerService {

	//Repository
	public HandyWorkerRepository	handyWorkerRepository;


	//Services

	//Constructor
	public HandyWorkerService() {
		super();
	}

	//Simple CRUD

	//8.1
	public HandyWorker create() {
		HandyWorker res;
		res = new HandyWorker();
		return res;
	}

	//9.2
	public HandyWorker save(final HandyWorker handyWorker) {
		Assert.notNull(handyWorker);
		HandyWorker res;
		//Restricciones
		res = this.handyWorkerRepository.save(handyWorker);
		return res;
	}

	//Complex methods

	//Returns logged handyWorker
	public HandyWorker findByPrincipal() {
		HandyWorker res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);

		return res;
	}

	//Returns logged customer from his or her handyWorker
	public HandyWorker findByUserAccount(final UserAccount userAccount) {
		HandyWorker res;
		Assert.notNull(userAccount);

		res = this.handyWorkerRepository.findByUserAccountId(userAccount.getId());

		return res;
	}
}
