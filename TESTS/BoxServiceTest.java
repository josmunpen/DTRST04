
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
import domain.Box;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class BoxServiceTest extends AbstractTest {

	//Service
	@Autowired
	private BoxService	boxService;


	//Test
	@Test
	public void testBox() {
		System.out.println("------Test Box------");
		final Box box, saved;
		final Collection<Message> m1 = new ArrayList<>();
		box = this.boxService.create();
		try {
			super.authenticate("customer");
			box.setName("Box1");
			box.setPredefined(true);
			box.setMessages(m1);
			saved = this.boxService.save(box);
			Assert.isTrue(this.boxService.findAll().contains(saved));

			this.boxService.delete(box);
			Assert.isNull(this.boxService.findOne(box));

			System.out.println("Success!");

			super.unauthenticate();

		} catch (final Exception e) {
			System.out.println("Error, " + e.getMessage() + "!");
		}
	}

}
