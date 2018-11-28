
package service;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ComplaintService;
import utilities.AbstractTest;
import domain.Complaint;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ComplaintServiceTest extends AbstractTest {

	//Service
	@Autowired
	private ComplaintService	complaintService;


	//Test
	@Test
	public void testComplaint() {
		System.out.println("------Test Complaint------");
		final Complaint complaint, saved;
		final Collection<Complaint> complaints;
		complaint = this.complaintService.create();//create by ID?
		try {
			complaint.setTicker("exampleTicker");
			final Date d = new Date("09/03/19");
			complaint.setMoment();//Averiguar cómo poner date y money.
			complaint.setAttachments(Arrays.asList("http://www.attachments1.com", "www.attachments2.com"));
			complaint.setDescription("This is an attachment.");

			saved = this.complaintService.save(complaint);
			Assert.notNull(saved);
			complaints = this.complaintService.findAll();

			Assert.isTrue(complaints.contains(saved));
			System.out.println("Éxito");
		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}
	}
}
