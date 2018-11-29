
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

import repositories.ComplaintRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.TickerGenerator;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
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
	
	@Autowired
	public HandyWorkerService	handyWorkerService;

	//Generador de tickers
	@Autowired
	public TickerGenerator      tickerGenerator;
	
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
		final Complaint res = new Complaint();
		res.setAttachments(new ArrayList<String>());
		res.setDescription("");
		res.setMoment(new Date());
		res.setFixUpTask(new FixUpTask());
		res.setTicker(TickerGenerator.generateTicker());

		return res;
	}
	
	public Complaint save(Complaint complaint) {

		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		Assert.isTrue(user.getId()==customerService.findCustomerByComplaint(complaint).getId());
		
		final Complaint res = new Complaint();
		res.setAttachments(complaint.getAttachments());
		res.setDescription(complaint.getDescription());
		res.setMoment(Calendar.getInstance().getTime());
		res.setTicker(TickerGenerator.generateTicker());
		res.setFixUpTask(complaint.getFixUpTask());
		return res;
	}

	//Complex methods

	public Collection<Complaint> findByCustomer() {
		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		final List<Complaint> res = new ArrayList<>(this.complaintRepository
				.findByCustomerId(user.getId()));

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

		final List<Complaint> res = new ArrayList<Complaint>(this.complaintRepository.findComplaintsNoReferee());

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
	//37.3
	public Collection<Complaint> findByHandyWorker(){
		//Logged user must be a handyworker
				final Authority a = new Authority();
				final UserAccount user = LoginService.getPrincipal();
				a.setAuthority(Authority.HANDYWORKER);
				Assert.isTrue(user.getAuthorities().contains(a));

				final Collection<Complaint> res;
				final HandyWorker handyworker;
				handyworker = this.handyWorkerService.findByPrincipal();
				Assert.notNull(handyworker);
				Assert.isTrue(handyworker.getId() != 0);

				res = this.complaintRepository.findByHandyWorker(handyworker.getId());

				return res;
	}
}
