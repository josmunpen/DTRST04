
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
import domain.FixUpTask;
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
		Assert.notNull(customer.getId());
		
		res = this.applicationRepository.findByCustomerId(customer.getId());
		return res;
	}

	public Application save(final Application application) {
		Assert.notNull(application);

		Application res;
		
		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		//Logged customer is application's owner
		final Customer logCustomer;
		logCustomer = this.customerService.findByPrincipal();
		Assert.notNull(logCustomer);
		Assert.notNull(logCustomer.getId());
		List<Application> l1=new ArrayList<Application>();
		Collection<FixUpTask> col1=logCustomer.getFixUpTasks();
		for(FixUpTask f:col1) {
			l1.addAll(f.getApplications());
		}
		Assert.isTrue(l1.contains(application));

		//Atributes that cant be changed
		Application oldApplication= this.applicationRepository.findById(application.getId());
		Assert.isTrue(application.getMoment()==oldApplication.getMoment());
		Assert.isTrue(application.getOfferedPrice()==oldApplication.getOfferedPrice());
		Assert.notNull(application.getStatus());
		Assert.isTrue(application.getStatus()=="pending"||application.getStatus()=="accepted"||
				application.getStatus()=="rejected");
		Assert.notNull(application.getComment());
		//L
		if (application.getStatus().equals("accepted")) {
			
			Assert.notNull(application.getCreditCard());
			
		}else if(application.getStatus().equals("rejected")) {
			
			Assert.notNull(application.getRejectedCause());
		}
		
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

	//12.5
	//TODO: Comprobar usuario logeado
	public double pendingApplications() {
		return this.applicationRepository.pendingApplications();
	}

	public double acceptedApplications() {
		return this.applicationRepository.acceptedApplications();
	}

	public double rejectedApplications() {
		return this.applicationRepository.rejectedApplications();
	}

	public double elapsedApplications() {
		return this.applicationRepository.elapsedApplications();
	}

	public ArrayList<Object> offeredPriceStatisctics() {
		return this.applicationRepository.offeredPriceStatistics();
	}

}
