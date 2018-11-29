
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Finder;
import domain.FixUpTask;
import domain.Money;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	//Service
	@Autowired
	private FinderService	finderService;


	//Test
	@Test
	public void testFinder() {
		System.out.println("------Test Finder------");
		final Finder fin, saved;
		final Collection<FixUpTask> ft = new ArrayList<>();
		fin = this.finderService.create();
		try {
			super.authenticate("handyWorker");
			fin.setKeyWord("finder");
			fin.setCategory("category");
			fin.setMinPrice(new Money());
			fin.setMaxPrice(new Money());
			fin.setStartDate(new Date());
			fin.setEndDate(new Date());
			fin.setWarranty(3.0);
			saved = this.finderService.save(fin);
			Assert.isTrue(this.finderService.findAll().contains(saved));

			//11.2?
			//37.2?

			super.unauthenticate();
			System.out.println("Success!");

		} catch (final Exception e) {
			System.out.println("Error, " + e.getMessage() + "!");
		}
	}

}
