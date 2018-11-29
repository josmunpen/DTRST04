
package services;

import java.util.ArrayList;
import java.util.Arrays;
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
import domain.Application;
import domain.Box;
import domain.Curriculum;
import domain.Endorsement;
import domain.Finder;
import domain.HandyWorker;
import domain.Note;
import domain.Phase;
import domain.SocialProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class HandyWorkerServiceTest extends AbstractTest {

	//Service
	@Autowired
	private HandyWorkerService	handyWorkerService;


	//Test
	@Test
	public void testHandyWorker() {
		System.out.println("------Test HandyWorker------");
		final HandyWorker hw, saved;
		final Collection<Application> app = new ArrayList<>();
		final Collection<Phase> pph = new ArrayList<>();
		final Collection<Finder> fin = new ArrayList<>();
		final Collection<Note> notes = new ArrayList<>();
		hw = this.handyWorkerService.create();
		try {
			hw.setName("Maria");
			hw.setEmail("maria@gmail.com");
			hw.setPhoneNumber("123456788");
			hw.setAddress("address");
			hw.setBan(false);
			hw.setMiddleName("mnmaria");
			hw.setSurname("suranemMaria");
			hw.setPhotoURL("http://www.urlphoto.com");
			hw.setSocialProfiles(Arrays.asList(new SocialProfile()));
			hw.setUserAccount(new UserAccount());
			hw.setBoxes(Arrays.asList(new Box()));
			hw.setEndorsements(Arrays.asList(new Endorsement()));
			hw.setScore(2);
			hw.setMake("make");
			hw.setApplications(app);
			hw.setPlannedPhases(pph);
			hw.setFinders(fin);
			hw.setNotes(notes);
			hw.setCurriculum(new Curriculum());

			super.authenticate("customer");

			saved = this.handyWorkerService.save(hw);
			Assert.isTrue(this.handyWorkerService.findAll().contains(saved));

			final Collection<HandyWorker> hwwmapps = this.handyWorkerService.handyWorkersWithMoreApplications();
			Assert.notNull(hwwmapps);

			super.unauthenticate();
			super.authenticate("admin");
			final Collection<HandyWorker> topthreehw = this.handyWorkerService.topThreeHandyWorkersByComplaints();
			Assert.notNull(topthreehw);

			super.unauthenticate();

			//11.2?
			//37.2?

			System.out.println("Success!");

		} catch (final Exception e) {
			System.out.println("Error, " + e.getMessage() + "!");
		}
	}

}
