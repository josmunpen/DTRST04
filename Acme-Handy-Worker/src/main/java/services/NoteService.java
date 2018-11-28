
package services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Customer;
import domain.HandyWorker;
import domain.Note;
import domain.Referee;

@Service
@Transactional
public class NoteService {

	//Repositories
	@Autowired
	public NoteRepository	noteRepository;

	//Services
	@Autowired
	public ComplaintService	complaintService;

	@Autowired
	public ReportService	reportService;

	@Autowired
	public CustomerService	customerService;


	//Constructor
	public NoteService() {
		super();
	}

	//35.2
	public Note create() {

		//Logged user must be a customer
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));

		final Note res = new Note();
		res.setCustomer(new Customer());
		res.setHandyWorker(new HandyWorker());
		res.setReferee(new Referee());
		res.setCustomerComment("");
		res.setHandyWorkerComment("");
		res.setRefereeComment("");
		res.setMandatoryComment("");
		res.setMoment(new Date());
		//res.setMoment(Calendar.getInstance().getTime());
		res.setCustomerComment("");
		return res;
	}

}
