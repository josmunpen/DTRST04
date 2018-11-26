
package services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import domain.Administrator;

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

}
