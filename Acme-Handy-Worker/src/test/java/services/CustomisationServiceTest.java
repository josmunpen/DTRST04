
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
import domain.Customisation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CustomisationServiceTest extends AbstractTest {

	//Service
	@Autowired
	private CustomisationService	customisationService;


	//Test
	@Test
	public void testCustomisation() {
		System.out.println("------Test Customisation------");
		super.authenticate("admin");
		final Customisation cus, saved;
		final Collection<String> sw = new ArrayList<>();
		final Collection<String> ccm = new ArrayList<>();
		final Collection<String> nw = new ArrayList<>();
		final Collection<String> pw = new ArrayList<>();
		sw.add("Nigeria");
		ccm.add("VISA");
		nw.add("shit");
		pw.add("good");
		System.out.println("Antes de create");
		cus = this.customisationService.create();
		System.out.println("Después de create");
		try {
			cus.setSystemName("sn1");
			cus.setBannerURL("http://www.urlbanner.com");
			cus.setWelcomeMessage("wcm1");
			cus.setSpamWords(sw);
			cus.setVATPercentage(1);
			cus.setPhoneNumberCountryCode("+34");
			cus.setCreditCardMakes(ccm);
			cus.setNegativeWords(nw);
			cus.setPositiveWords(pw);
			cus.setFinderDuration(20);
			cus.setResultsNumber(20);
			System.out.println("Antes de save: " + cus.getResultsNumber());

			saved = this.customisationService.save(cus);

			System.out.println(saved);

			Assert.isTrue(this.customisationService.findAll().contains(saved));

			super.unauthenticate();

			System.out.println("Success!");

		} catch (final Exception e) {
			System.out.println("Error, " + e.getMessage() + "!");
		}

	}

}
