
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorsementRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Customer;
import domain.Endorsement;
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


	//Constructor
	public EndorsementService() {
		super();
	}

	//Simple CRUD methods
	//48.1
	public Endorsement create() {
		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		final Endorsement res = new Endorsement();
		//TODO: Sender y recipient? El recipient tiene que haber trabajado para el customer

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
		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		//Logged user must be endorsement's sender
		final Customer sender;
		sender = this.customerService.findByPrincipal();
		Assert.notNull(sender);
		Assert.notNull(sender.getId());
		Assert.isTrue(sender.equals(endorsement.getSender()));

		//Restrictions
		Assert.notNull(endorsement.getMoment());
		Assert.notNull(endorsement.getComment());
		Assert.notNull(endorsement.getSender());
		Assert.notNull(endorsement.getRecipient());

		final Endorsement res;
		res = this.endorsementRepository.save(endorsement);
		return res;
	}

	public void deleteByCustomer(final Endorsement endorsement) {
		Assert.notNull(endorsement);
		Assert.notNull(endorsement.getId());
		Assert.isTrue(endorsement.getId() != 0);
		Assert.isTrue(this.endorsementRepository.exists(endorsement.getId()));

		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		//Logged user must be endorsement's sender
		final Customer sender;
		sender = this.customerService.findByPrincipal();
		Assert.notNull(sender);
		Assert.notNull(sender.getId());
		Assert.isTrue(sender.equals(endorsement.getSender()));

		//Restrictions
		Assert.notNull(endorsement.getMoment());
		Assert.notNull(endorsement.getComment());
		Assert.notNull(endorsement.getSender());
		Assert.notNull(endorsement.getRecipient());

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
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));

		//Logged user must be endorsement's sender
		final HandyWorker sender;
		sender = this.handyWorkerService.findByPrincipal();
		Assert.notNull(sender);
		Assert.notNull(sender.getId());
		Assert.isTrue(sender.equals(endorsement.getSender()));

		//Restrictions
		Assert.notNull(endorsement.getMoment());
		Assert.notNull(endorsement.getComment());
		Assert.notNull(endorsement.getSender());
		Assert.notNull(endorsement.getRecipient());

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
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		//Logged user must be endorsement's sender
		final Customer sender;
		sender = this.customerService.findByPrincipal();
		Assert.notNull(sender);
		Assert.notNull(sender.getId());
		Assert.isTrue(sender.equals(endorsement.getSender()));

		//Restrictions
		Assert.notNull(endorsement.getMoment());
		Assert.notNull(endorsement.getComment());
		Assert.notNull(endorsement.getSender());
		Assert.notNull(endorsement.getRecipient());

		this.endorsementRepository.delete(endorsement);

	}
}
