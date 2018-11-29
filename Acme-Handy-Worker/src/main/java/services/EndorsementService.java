
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorsementRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Customer;
import domain.Endorsement;
import domain.Endorser;
import domain.FixUpTask;
import domain.HandyWorker;

@Service
@Transactional
public class EndorsementService {

	//Repository
	@Autowired
	public EndorsementRepository	endorsementRepository;

	//Services
	@Autowired
	public CustomerService			customerService;

	@Autowired
	public HandyWorkerService		handyWorkerService;

	@Autowired
	public FixUpTaskService			fixUpTaskService;

	@Autowired
	public ApplicationService		applicationService;


	//Constructor
	public EndorsementService() {
		super();
	}

	//Simple CRUD methods
	//48.1
	public Endorsement create() {
		//Logged user must be a customer or a handy worker
		final Authority a = new Authority();
		final Authority b = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		b.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a) || user.getAuthorities().contains(b));

		final Endorsement res = new Endorsement();
		res.setComment("");
		res.setMoment(new Date());
		res.setSender(new Endorser());
		res.setRecipient(new Endorser());

		return res;
	}

	public Collection<Endorsement> findByCustomer() {
		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		Collection<Endorsement> res;
		final Customer customer;
		customer = this.customerService.findByPrincipal();
		Assert.notNull(customer);

		res = this.endorsementRepository.findByCustomerId(customer.getId());
		return res;

	}

	public Endorsement saveByCustomer(final Endorsement endorsement) {
		Assert.notNull(endorsement);
		Assert.isTrue(endorsement.getId() != 0);
		//Logged user must be a customer
		final Authority au = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		au.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(au));

		//Logged user must be endorsement's sender
		final Customer sender;
		sender = this.customerService.findByPrincipal();
		Assert.notNull(sender);
		Assert.isTrue(sender.getId() != 0);
		Assert.isTrue(sender.equals(endorsement.getSender()));

		//Recipient must be a handy worker who has been involved
		//in any of his or her fix-up tasks
		//TODO: Comprobar que el recipient es un handy worker
		final Endorser recipient;
		recipient = endorsement.getRecipient();
		final Collection<FixUpTask> fixUpTasks = this.fixUpTaskService.findByCustomer();
		final List<Application> applicationsCustomer = new ArrayList<Application>();
		for (final FixUpTask f : fixUpTasks)
			applicationsCustomer.addAll(f.getApplications());

		boolean checkApp = false;
		final Collection<Application> applicationsHandyWorker = this.applicationService.findByHandyWorker();
		for (final Application a : applicationsHandyWorker)
			if (applicationsCustomer.contains(a)) {
				checkApp = true;
				break;
			}
		Assert.isTrue(checkApp);

		final Endorsement res;
		res = this.endorsementRepository.save(endorsement);
		return res;
	}

	public void deleteByCustomer(final Endorsement endorsement) {
		Assert.notNull(endorsement);
		Assert.isTrue(endorsement.getId() != 0);
		Assert.isTrue(this.endorsementRepository.exists(endorsement.getId()));

		//Logged user must be a customer
		final Authority au = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		au.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(au));

		//Logged user must be endorsement's sender
		final Customer sender;
		sender = this.customerService.findByPrincipal();
		Assert.notNull(sender);
		Assert.isTrue(sender.getId() != 0);
		Assert.isTrue(sender.equals(endorsement.getSender()));

		//Recipient must be a handy worker who has been involved
		//in any of his or her fix-up tasks
		//TODO: Comprobar que el recipient es un handy worker
		final Endorser recipient;
		recipient = endorsement.getRecipient();
		final Collection<FixUpTask> fixUpTasks = this.fixUpTaskService.findByCustomer();
		final List<Application> applicationsCustomer = new ArrayList<Application>();
		for (final FixUpTask f : fixUpTasks)
			applicationsCustomer.addAll(f.getApplications());

		boolean checkApp = false;
		final Collection<Application> applicationsHandyWorker = this.applicationService.findByHandyWorker();
		for (final Application a : applicationsHandyWorker)
			if (applicationsCustomer.contains(a)) {
				checkApp = true;
				break;
			}
		Assert.isTrue(checkApp);

		this.endorsementRepository.delete(endorsement);

	}

	//49.2

	public Collection<Endorsement> findByHandyWorker() {
		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		Collection<Endorsement> res;
		final HandyWorker handyWorker;
		handyWorker = this.handyWorkerService.findByPrincipal();
		Assert.notNull(handyWorker);

		res = this.endorsementRepository.findByHandyWorkerId(handyWorker.getId());
		return res;

	}

	public Endorsement saveByHandyWorker(final Endorsement endorsement) {
		//Logged user must be a handy worker
		final Authority au = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		au.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(au));

		//Logged user must be endorsement's sender
		final HandyWorker sender;
		sender = this.handyWorkerService.findByPrincipal();
		Assert.notNull(sender);
		Assert.notNull(sender.getId());
		Assert.isTrue(sender.equals(endorsement.getSender()));

		//Endorsement must be about a customer for whom he or she's worked
		//TODO: Comprobar que el recipient es un customer
		final Endorser recipient;
		recipient = endorsement.getRecipient();
		final Collection<Application> applicationsHandy = this.applicationService.findByHandyWorker();
		final List<FixUpTask> fixUpTaskHandy = new ArrayList<FixUpTask>();
		for (final Application a : applicationsHandy)
			fixUpTaskHandy.add(a.getFixUpTask());

		boolean checkApp = false;
		final Collection<FixUpTask> fixUpTaskCustomer = this.fixUpTaskService.findByCustomer();
		for (final FixUpTask f : fixUpTaskCustomer)
			if (fixUpTaskHandy.contains(f)) {
				checkApp = true;
				break;
			}
		Assert.isTrue(checkApp);

		final Endorsement res;
		res = this.endorsementRepository.save(endorsement);
		return res;
	}

	public void deleteByHandyWorker(final Endorsement endorsement) {
		Assert.notNull(endorsement);
		Assert.notNull(endorsement.getId());
		Assert.isTrue(endorsement.getId() != 0);
		Assert.isTrue(this.endorsementRepository.exists(endorsement.getId()));

		//Logged user must be a customer
		final Authority au = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		au.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(au));

		//Logged user must be endorsement's sender
		final Customer sender;
		sender = this.customerService.findByPrincipal();
		Assert.notNull(sender);
		Assert.notNull(sender.getId());
		Assert.isTrue(sender.equals(endorsement.getSender()));

		//Endorsement must be about a customer for whom he or she's worked
		//TODO: Comprobar que el recipient es un customer
		final Endorser recipient;
		recipient = endorsement.getRecipient();
		final Collection<Application> applicationsHandy = this.applicationService.findByHandyWorker();
		final List<FixUpTask> fixUpTaskHandy = new ArrayList<FixUpTask>();
		for (final Application a : applicationsHandy)
			fixUpTaskHandy.add(a.getFixUpTask());

		boolean checkApp = false;
		final Collection<FixUpTask> fixUpTaskCustomer = this.fixUpTaskService.findByCustomer();
		for (final FixUpTask f : fixUpTaskCustomer)
			if (fixUpTaskHandy.contains(f)) {
				checkApp = true;
				break;
			}
		Assert.isTrue(checkApp);

		this.endorsementRepository.delete(endorsement);

	}
}
