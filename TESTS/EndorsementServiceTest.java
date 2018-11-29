
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Endorsement;
import domain.Endorser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorsementServiceTest extends AbstractTest {

	//Service
	@Autowired
	private EndorsementService	endorsementService;


	//Test
	@Test
	public void testEndorsement() {
		System.out.println("------Test Endorsement------");
		final Endorsement end, saved;
		end = this.endorsementService.create();
		try {
			super.authenticate("customer");
			end.setComment("comment1");
			end.setMoment(new Date());
			end.setSender(new Endorser());
			end.setRecipient(new Endorser());
			saved = this.endorsementService.save(end);
			Assert.isTrue(this.endorsementService.findAll().contains(saved));

			//findBy, saveBy y delteBy de Customer y HadnyWorker

			super.unauthenticate();
			System.out.println("Success!");

		} catch (final Exception e) {
			System.out.println("Error, " + e.getMessage() + "!");
		}
	}

}
