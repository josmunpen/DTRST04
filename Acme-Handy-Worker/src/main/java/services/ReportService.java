
package services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

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
	public AdministratorService	administratorService;


	//38.5
	public ArrayList<Object> notesStatistics() {
		final Administrator admin;
		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		return this.reportRepository.notesStatistics();
	}

	//35.2
	public Report reportByComplaint(final Complaint complaint) {
		final Report res;
		//Comprobamos que el logged user es un customer
		Assert.notNull(LoginService.getPrincipal());
		Assert.notNull(LoginService.getPrincipal().getId());
		final Authority a = new Authority();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a));

		res = this.reportRepository.getReportByComplaintId(complaint.getId());
		return res;
	}
}
