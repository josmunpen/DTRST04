
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Warranty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class WarrantyServiceTest extends AbstractTest {

	//Service
	@Autowired
	private WarrantyService	warrantyService;


	//Test
	@Test
	public void testCreateSaveandDelete() {
		System.out.println("------Test Create, Save and Delete------");
		final Warranty war, saved;
		final Collection<String> sw = new ArrayList<>();
		final Collection<String> ccm = new ArrayList<>();
		final Collection<String> nw = new ArrayList<>();
		final Collection<String> pw = new ArrayList<>();
		war = this.warrantyService.create();
		try {
			super.authenticate("admin");
			war.setTitle("title");
			war.setTerms("terms");
			war.setApplicableLaws("laws");
			war.setFinalMode(false);

			saved = this.warrantyService.save(war);
			Assert.isTrue(this.warrantyService.findAll().contains(saved));

			this.warrantyService.delete(saved);
			Assert.isNull(this.warrantyService.findOne(saved));

			super.unauthenticate();

			System.out.println("Success!");

		} catch (final Exception e) {
			System.out.println("Error, " + e.getMessage() + "!");
		}
	}

}
