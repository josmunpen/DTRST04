package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.type.CalendarDateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Note;
import domain.Referee;
import domain.Report;

import repositories.NoteRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class NoteService {
	//Repositories
		@Autowired
		public NoteRepository	noteRepository;

		//Services
		@Autowired
		public ComplaintService		complaintService;
		
		@Autowired
		public ReportService		reportService;
		
		@Autowired
		public CustomerService		customerService;

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
		
		public Note saveNoteCustomer(Note note) {

			//Logged user must be a customer
			final Authority a = new Authority();
			final UserAccount user = LoginService.getPrincipal();
			a.setAuthority(Authority.CUSTOMER);
			Assert.isTrue(user.getAuthorities().contains(a));
			
			/*
			final Note res = new Note();
			res.setCustomer(note.getCustomer());
			res.setHandyWorker(note.getHandyWorker());
			res.setReferee(note.getReferee());
			res.setCustomerComment(note.getCustomerComment());
			res.setHandyWorkerComment(note.getHandyWorkerComment());
			res.setRefereeComment(note.getRefereeComment());
			res.setMandatoryComment(note.getMandatoryComment());
			res.setMoment(Calendar.getInstance().getTime());
			res.setCustomerComment(note.getCustomerComment());
			this.noteRepository.save(res);
			return res;
			*/
			final Note res=new Note();
			res.setCustomer(note.getCustomer());
			res.setCustomerComment(note.getCustomerComment());
			res.setMandatoryComment(note.getMandatoryComment());
			res.setMoment(Calendar.getInstance().getTime());
			res.setCustomerComment(note.getCustomerComment());
			this.noteRepository.save(res);
			return res;
		}
}
