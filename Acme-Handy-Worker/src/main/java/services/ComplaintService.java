
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Complaint;
import domain.FixUpTask;
import domain.Referee;

@Service
@Transactional
public class ComplaintService {

	//Repositories
	@Autowired
	public ComplaintRepository	complaintRepository;

	//Services
	@Autowired
	public CustomerService		customerService;

	@Autowired
	public FixUpTaskService		fixUpTaskService;

	@Autowired
	public RefereeService		refereeService;


	//Constructor
	public ComplaintService() {
		super();
	}

	//Simple CRUD
	//35.1
	public Complaint create() {

		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		//TODO: Revisar create
		final Complaint res = new Complaint();

		return res;
	}

	//Complex methods

	public Collection<Complaint> findByCustomer() {
		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		final List<FixUpTask> fixUp = new ArrayList<>();

		fixUp.addAll(this.fixUpTaskService.findByCustomer());

		FixUpTask f;
		final List<Complaint> res = new ArrayList<Complaint>();

		for (int i = 0; i < fixUp.size(); i++) {
			f = fixUp.get(0);
			res.addAll(f.getComplaints());
		}

		return res;

	}
	//36.1
	public Collection<Complaint> findNoReferee() {
		//Logged user must be a referee
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.REFEREE);
		Assert.isTrue(user.getAuthorities().contains(a));

		final Referee referee;
		referee = this.refereeService.findByPrincipal();
		Assert.notNull(referee);

		final List<Complaint> res = new ArrayList<Complaint>();

		return res;

	}

	//36.2
	public Collection<Complaint> findByReferee() {
		//Logged user must be a referee
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.REFEREE);
		Assert.isTrue(user.getAuthorities().contains(a));

		final Collection<Complaint> res;
		final Referee referee;
		referee = this.refereeService.findByPrincipal();
		Assert.notNull(referee);
		Assert.isTrue(referee.getId() != 0);

		res = this.complaintRepository.findByRefereeId(referee.getId());

		return res;
	}
}
