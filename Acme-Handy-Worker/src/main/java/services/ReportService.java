package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import domain.Administrator;
import domain.Complaint;
import domain.Note;
import domain.Referee;
import domain.Report;
import repositories.RefereeRepository;
import repositories.ReportRepository;
import security.Authority;
import security.LoginService;
import repositories.ReportRepository;
import security.Authority;
import security.LoginService;
import domain.Administrator;
import domain.Complaint;
import domain.Report;

public class ReportService {

	@Autowired
	public ReportRepository		reportRepository;
	
	@Autowired
	public RefereeRepository	refereeRepository;

	@Autowired
	public AdministratorService	administratorService;

	//35.2
	public Report reportByComplaint(final Complaint complaint){
		final Report res;
		//Comprobamos que el logged user es un customer
		Assert.notNull(LoginService.getPrincipal());
		Assert.notNull(LoginService.getPrincipal().getId());
		Authority a= new Authority();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a));
		
		res=this.reportRepository.getReportByComplaintId(complaint.getId());
		return res;
	}
	//36.1
	public Report create() {
		final Report res = new Report();
		//Comprobamos que la persona que lo crea es un referee
		Assert.notNull(LoginService.getPrincipal());
		Assert.notNull(LoginService.getPrincipal().getId());
		Authority a= new Authority();
		a.setAuthority(Authority.REFEREE);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a));
		
		res.setAttachments("");
		res.setComplaint(new Complaint());
		res.setDescription("");
		res.setMoment(new Date());
		res.setNotes(new ArrayList<Note>());
		return res;
		
	}
	
	public Report save(Report report) {
		final Report res = new Report();
		//Comprobamos que la persona que lo crea es un referee
		Assert.notNull(LoginService.getPrincipal());
		Assert.notNull(LoginService.getPrincipal().getId());
		Authority a= new Authority();
		a.setAuthority(Authority.REFEREE);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a));
		
		//Comprobamos que es el dueño de ese report
		Referee referee=this.refereeRepository.getReportByReportId(report.getId());
		Assert.isTrue(referee.getId()==LoginService.getPrincipal().getId());
		
		res.setAttachments(report.getAttachments());
		res.setComplaint(report.getComplaint());
		res.setDescription(report.getDescription());
		res.setMoment(Calendar.getInstance().getTime());
		res.setNotes(report.getNotes());
		return res;
		
	}
	
	//38.5
	public ArrayList<Object> notesStatistics() {
		final Administrator admin;
		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		return this.reportRepository.notesStatistics();
	}


}