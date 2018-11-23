
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.LoginService;
import domain.Application;
import domain.Customer;
import domain.HandyWorker;

@Service
@Transactional
public class ApplicationService {

	//Repository
	@Autowired
	public ApplicationRepository	applicationRepository;

	//Services
	@Autowired
	public CustomerService			customerService;
	@Autowired
	public HandyWorkerService		handyWorkerService;


	//Constructor
	public ApplicationService() {
		super();
	}

	//Simple CRUD

	//Complex methods
	//10.2
	public Collection<Application> findByCustomer() {
		Collection<Application> res;

		//Logged must be a customer
		final Customer customer;
		customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);
		Assert.notNull(customer.getId());

		res = this.applicationRepository.findByCustomerId(customer.getId());
		return res;
	}

	public Application save(final Application application) {
		Assert.notNull(application);

		Application res;
		//TODO: Atributos obligatorios

		//Logged must be a customer
		//TODO: Customer solo pueden actualizar
		final Customer customer;
		customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);
		Assert.notNull(customer.getId());
		//TODO:IDcustomerLogeado==idDelCustomerReal
		int id=LoginService.getPrincipal().getId();
		int fixUpTaskId=application.getFixUpTask().getId();

		String status=application.getStatus();
		res = this.applicationRepository.save(application);
		return res;
	}

	//11.3
	public Collection<Application> findByHandyWorker() {
		Collection<Application> res;

		//Logged must be a customer
		final HandyWorker handyWorker;
		handyWorker = this.handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);

		res = this.applicationRepository.findByHandyWorkerId(handyWorker.getId());
		return res;
	}
}
