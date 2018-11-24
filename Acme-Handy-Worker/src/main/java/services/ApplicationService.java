
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
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
	public Application create() {

		//Logged user must be a handyWorker
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		return new Application();

		//TODO: FixUpTask de la application?

	}
	//Complex methods
	//10.2
	public Collection<Application> findByCustomer() {
		Collection<Application> res;

		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		final Customer customer;
		customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);

		res = this.applicationRepository.findByCustomerId(customer.getId());
		return res;
	}

	public Application save(final Application application) {
		Assert.notNull(application);

		Application res;

		//Logged user must be a customer
		final Customer customer;
		customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);
		Assert.notNull(customer.getId());

		//TODO:IDcustomerLogeado==idDelCustomerReal
		final int id = LoginService.getPrincipal().getId();
		final int fixUpTaskId = application.getFixUpTask().getId();

		//TODO: Atributos obligatorios
		//TODO: DATE Assert.notNull();
		Assert.notNull(application.getStatus());
		Assert.notNull(application.getComment());
		//TODO: Assert.notnull(application.getRecejtedCause()); RejectedCausee no debería tener @NotBlank?

		if (application.getStatus().equals("accepted"))
			Assert.notNull(application.getCreditCard());

		res = this.applicationRepository.save(application);
		return res;
	}

	//11.3
	public Collection<Application> findByHandyWorker() {
		Collection<Application> res;

		//Logged must be a handyWorker
		final HandyWorker handyWorker;
		handyWorker = this.handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);

		res = this.applicationRepository.findByHandyWorkerId(handyWorker.getId());
		return res;
	}
}
