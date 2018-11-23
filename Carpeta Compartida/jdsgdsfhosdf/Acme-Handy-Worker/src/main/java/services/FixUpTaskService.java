
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
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;

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
		return new FixUpTask();
	}

	public FixUpTask save(final FixUpTask fixUpTask) {
		Assert.notNull(fixUpTask);

		//Logged must be a customer for creating Fix-Up Tasks
		final Customer customer;
		customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);

		final FixUpTask res;
		//TODO: Atributos obligatorios
		Assert.notNull(fixUpTask.getMoment());

		res = this.fixUpTaskRepository.save(fixUpTask);
		
		
		return res;
	}

	public void delete(final FixUpTask fixUpTask) {
		//TODO:Comprobacion de los 3 pasos
		Assert.notNull(fixUpTask);

		Assert.isTrue(fixUpTask.getId() != 0);

		Assert.isTrue(this.fixUpTaskRepository.exists(fixUpTask.getId()));
		//Atributos?

		this.fixUpTaskRepository.delete(fixUpTask);
	}

	public Collection<FixUpTask> findByCustomer() {
		Collection<FixUpTask> res;
		final Customer customer;

		//Logged is customer
		customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);

		res = this.fixUpTaskRepository.findByCustomerId(customer.getId());
		return res;
	}

	//11.1
	public Collection<FixUpTask> findAll() {
		Collection<FixUpTask> res;

		//Logged customer must be a handy worker
		final HandyWorker handyWorker;
		handyWorker = this.handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);

		res = this.fixUpTaskRepository.findAll();
		return res;
	}
}
