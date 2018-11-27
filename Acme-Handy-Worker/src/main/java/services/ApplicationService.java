
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.CreditCard;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Money;

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

		final FixUpTask fixUpTask = new FixUpTask();

		final Application res = new Application();

		res.setFixUpTask(fixUpTask);
		res.setMoment(new Date());
		res.setStatus("");
		res.setOfferedPrice(new Money());
		res.setComment("");
		res.setRejectedCause("");
		res.setCreditCard(new CreditCard());

		return new Application();
		//TODO: 11.3 When a handy worker applies for a fix-up task...
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

	public Application saveByCustomer(final Application application) {
		Assert.notNull(application);
		Assert.isTrue(application.getId() != 0);

		Application res;

		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		//Logged customer is application's fix up task owner
		final Customer logCustomer;
		logCustomer = this.customerService.findByPrincipal();
		Assert.notNull(logCustomer);
		Assert.notNull(logCustomer.getId());
		final List<Application> l1 = new ArrayList<Application>();
		final Collection<FixUpTask> col1 = logCustomer.getFixUpTasks();
		for (final FixUpTask f : col1)
			l1.addAll(f.getApplications());
		Assert.isTrue(l1.contains(application));

		//Atributes that cant be changed
		final Application oldApplication = this.applicationRepository.findById(application.getId());
		Assert.isTrue(application.getMoment() == oldApplication.getMoment());
		Assert.isTrue(application.getOfferedPrice() == oldApplication.getOfferedPrice());
		Assert.notNull(application.getStatus());
		Assert.isTrue(application.getStatus() == "pending" || application.getStatus() == "accepted" || application.getStatus() == "rejected");

		if (application.getStatus().equals("accepted"))
			Assert.notNull(application.getCreditCard());
		else if (application.getStatus().equals("rejected"))
			Assert.notNull(application.getRejectedCause());

		res = this.applicationRepository.save(application);
		return res;
	}

	public Application saveByHandyWorker(final Application application) {
		Assert.notNull(application);
		Assert.isTrue(application.getId() != 0);

		Application res;

		//Logged user must be a handy worker
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		//Logged handy worker is application's owner
		final HandyWorker logHandyWorker;
		logHandyWorker = this.handyWorkerService.findByPrincipal();
		Assert.notNull(logHandyWorker);
		Assert.notNull(logHandyWorker.getId());
		final List<Application> apps = new ArrayList<Application>();
		apps.addAll(logHandyWorker.getApplications());
		Assert.isTrue(apps.contains(application));

		Assert.isTrue(application.getOfferedPrice().getAmount() > 0);

		application.setMoment(Calendar.getInstance().getTime());

		res = this.applicationRepository.save(application);
		return res;
	}

	//11.3
	public Collection<Application> findByHandyWorker() {
		Collection<Application> res;

		//Logged user must be a handyWorker
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		//Logged handyWorker
		final HandyWorker handyWorker = this.handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);
		Assert.notNull(handyWorker.getId());

		res = this.applicationRepository.findByHandyWorkerId(handyWorker.getId());
		return res;
	}

	//12.5
	public double pendingApplications() {
		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		return this.applicationRepository.pendingApplications();
	}

	public double acceptedApplications() {
		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		return this.applicationRepository.acceptedApplications();
	}

	public double rejectedApplications() {
		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		return this.applicationRepository.rejectedApplications();
	}

	public double elapsedApplications() {
		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		return this.applicationRepository.elapsedApplications();
	}

	public ArrayList<Object> offeredPriceStatisctics() {
		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		return this.applicationRepository.offeredPriceStatistics();
	}

}
