
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

@Service
@Transactional
public class EndorsementService {

	//Repository
	@Autowired
	public EndorsementRepository	endorsementRepository;

	//Services
	@Autowired
	public CustomerService			customerService;


	//Constructor
	public EndorsementService() {
		super();
	}

	//Simple CRUD methods
	//48
	public Endorsement create() {
		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		final Endorsement res = new Endorsement();
		//TODO: Sender y recipient?

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

	public Endorsement save(final Endorsement endorsement) {
		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		//Restrictions
		//TODO: Atributos tipo DATE, sender y recipient
		Assert.notNull(endorsement.getMoment());

		final Endorsement res;
		res = this.endorsementRepository.save(endorsement);
		return res;
	}

	public void delete(final Endorsement endorsement) {
		//TODO:Comprobacion de los 3 pasos
		Assert.notNull(endorsement);
		Assert.isTrue(endorsement.getId() != 0);
		Assert.isTrue(this.endorsementRepository.exists(endorsement.getId()));

		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		this.endorsementRepository.delete(endorsement);

	}

	//49.2
	public Endorsement saveByHandyWorker(final Endorsement endorsement) {
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));
		Assert.isTrue(endorsement.getEndorser().getUserAccount().equals(user));

		Assert.notNull(endorsement);
		Assert.notNull(endorsement.getId());
		Assert.notNull(endorsement.getComment());
		Assert.notNull(endorsement.getMoment());

		final Endorsement res = this.endorsementRepository.save(endorsement);

		return res;
	}

	public void deleteByHandyWorker(final Endorsement endorsement) {
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a));
		Assert.isTrue(endorsement.getEndorser().getUserAccount().equals(user));

		Assert.notNull(endorsement);
		Assert.notNull(endorsement.getId());
		Assert.notNull(endorsement.getComment());
		Assert.notNull(endorsement.getMoment());

		this.endorsementRepository.delete(endorsement);

	}
}
