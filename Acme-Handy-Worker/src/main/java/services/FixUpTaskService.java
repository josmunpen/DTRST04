
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FixUpTaskRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Category;
import domain.Customer;
import domain.FixUpTask;

@Service
@Transactional
public class FixUpTaskService {

	//Repositories
	@Autowired
	public FixUpTaskRepository	fixUpTaskRepository;

	//Services
	@Autowired
	public CustomerService		customerService;

	@Autowired
	public HandyWorkerService	handyWorkerService;


	//Constructor
	public FixUpTaskService() {
		super();
	}

	//Simple CRUD

	//10.1

	public FixUpTask create() {
		final FixUpTask res = new FixUpTask();
		final Category cat = new Category();
		cat.setName("CATEGORY");
		res.setCategory(cat);
		return res;
	}

	public FixUpTask save(final FixUpTask fixUpTask) {
		Assert.notNull(fixUpTask);

		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		//Restrictions
		Assert.notNull(fixUpTask.getMoment());
		Assert.notNull(fixUpTask.getTicker());
		Assert.notNull(fixUpTask.getAddress());
		Assert.notNull(fixUpTask.getDescription());
		//TODO: Atributos tipo DATE
		Assert.notNull(fixUpTask.getCategory());

		final FixUpTask res;
		res = this.fixUpTaskRepository.save(fixUpTask);
		return res;
	}

	public void delete(final FixUpTask fixUpTask) {
		//TODO:Comprobacion de los 3 pasos
		Assert.notNull(fixUpTask);
		Assert.isTrue(fixUpTask.getId() != 0);
		Assert.isTrue(this.fixUpTaskRepository.exists(fixUpTask.getId()));

		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		this.fixUpTaskRepository.delete(fixUpTask);
	}

	public Collection<FixUpTask> findByCustomer() {

		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		Collection<FixUpTask> res;
		final Customer customer;
		customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);

		res = this.fixUpTaskRepository.findByCustomerId(customer.getId());
		return res;
	}

	//11.1
	public Collection<FixUpTask> findAll() {

		//Logged user must be a handyWorker
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		Collection<FixUpTask> res;
		res = this.fixUpTaskRepository.findAll();
		return res;
	}
}
