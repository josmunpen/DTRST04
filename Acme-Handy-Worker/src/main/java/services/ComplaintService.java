
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
}
