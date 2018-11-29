
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

import security.UserAccount;
import utilities.AbstractTest;
import domain.Box;
import domain.SocialProfile;
import domain.Sponsor;
import domain.Sponsorship;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorServiceTest extends AbstractTest {

	//Service
	@Autowired
	private SponsorService	sponsorService;


	//Test
	@Test
	public void testCreate() {
		System.out.println("------Test Create------");
		final Sponsor spo, saved;
		final Collection<SocialProfile> sp1 = new ArrayList<>();
		final Collection<Box> boxes1 = new ArrayList<>();
		final Collection<Sponsorship> ss1 = new ArrayList<>();
		spo = this.sponsorService.create();
		try {
			super.authenticate("admin");
			spo.setName("Pepe");
			spo.setEmail("actorPepe@gmail.com");
			spo.setPhoneNumber("123456786");
			spo.setAddress("PepeAddress");
			spo.setBan(false);
			spo.setMiddleName("PepeMiddleName");
			spo.setSurname("PepeSurname");
			spo.setPhotoURL("http://www.urlpepe.com");
			spo.setSocialProfiles(sp1);
			spo.setUserAccount(new UserAccount());
			spo.setBoxes(boxes1);
			spo.setSponsorships(ss1);

			saved = this.sponsorService.save(spo);
			Assert.isTrue(this.sponsorService.findAll().contains(saved));

			super.unauthenticate();

			System.out.println("Success!");

		} catch (final Exception e) {
			System.out.println("Error, " + e.getMessage() + "!");
		}
	}
}
