
package services;

import java.util.Arrays;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Section;
import domain.Tutorial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TutorialServiceTest extends AbstractTest {

	//Service
	@Autowired
	private TutorialService	tutorialService;


	//Tests
	@Test
	public void testCreateSaveAndFindAll() {
		System.out.println("------Test Create, Save and FindAll------");
		final Tutorial tut, saved;
		tut = this.tutorialService.create();
		try {
			super.authenticate("handyWorker");
			tut.setTitle("title");
			tut.setLastUpdate(new Date());
			tut.setSummary("summary");
			tut.setPhotoURL("http://www.urlphoto.com");
			tut.setSections(Arrays.asList(new Section()));

			saved = this.tutorialService.save(tut);
			Assert.isTrue(this.tutorialService.findAll().contains(saved));

			this.tutorialService.delete(saved);
			Assert.isNull(this.tutorialService.findOne(saved));

			super.unauthenticate();
			System.out.println("Success!");

		} catch (final Exception e) {
			System.out.println("Error, " + e.getMessage() + "!");
		}
	}
}
