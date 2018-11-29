
package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Application;
import domain.CreditCard;
import domain.FixUpTask;
import domain.Money;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	//Service
	@Autowired
	private ApplicationService	applicationService;


	//Test
	@Test
	public void testCreate() {
		System.out.println("------Test Create------");
		final Application app, saved, app2, app3;
		app = this.applicationService.create();
		try {
			super.authenticate("handyWorker");
			app.setMoment(new Date());
			app.setStatus("PENDING");
			app.setOfferedPrice(new Money());
			app.setComment("comment");
			app.setRejectedCause("rejectedCause");
			app.setCreditCard(new CreditCard());
			app.setFixUpTask(new FixUpTask());

			saved = this.applicationService.save(app);
			Assert.isTrue(this.applicationService.findAll().contains(saved));

			super.unauthenticate();

			//findByCustomer?

			//saveByCustomer?

			//saveByHandyWorker

			//findBy HandyWorker

			super.authenticate("handyWorker");

			app2 = app;
			app2.setStatus("ACCEPTED");
			app2 = this.applicationService.save(app2);

			app3 = app;
			app3.setStatus("REJECTED");
			app3 = this.applicationService.save(app3);

			final double pendingApps = this.applicationService.pendingApplications();
			Assert.isTrue(pendingApps > 0);

			final double acceptedApps = this.applicationService.pendingApplications();
			Assert.isTrue(acceptedApps > 0);

			final double rejectedApps = this.applicationService.pendingApplications();
			Assert.isTrue(rejectedApps > 0);

			final Date date = new GregorianCalendar(2012, 2, 2).getTime();
			app2.setMoment(date);
			app2 = this.applicationService.saveByCustomer(app2);
			final double elapsedApps = this.applicationService.elapsedApplications();
			Assert.isTrue(elapsedApps > 0);

			final ArrayList<Object> offPStats = this.applicationService.offeredPriceStatisctics();
			Assert.notNull(offPStats);

			super.unauthenticate();

			System.out.println("Success!");

		} catch (final Exception e) {
			System.out.println("Error, " + e.getMessage() + "!");
		}
	}

	//	@Test
	//	public void testApplication() {
	//		System.out.println("------Test Application------");
	//		final Application app, saved, app2, app3;
	//		app = this.applicationService.create();
	//		try {
	//			app.setMoment(new Date());
	//			app.setStatus("PENDING");
	//			app.setOfferedPrice(new Money());
	//			app.setComment("comment");
	//			app.setRejectedCause("rejectedCause");
	//			app.setCreditCard(new CreditCard());
	//			app.setFixUpTask(new FixUpTask());
	//
	//			saved = this.applicationService.save(app);
	//			Assert.isTrue(this.applicationService.findAll().contains(saved));
	//
	//			app2 = app;
	//			app2.setStatus("ACCEPTED");
	//			app2 = this.applicationService.save(app2);
	//
	//			app3 = app;
	//			app3.setStatus("REJECTED");
	//			app3 = this.applicationService.save(app3);
	//
	//			//findByCustomer?
	//
	//			//saveByCustomer?
	//
	//			//saveByHandyWorker
	//
	//			//findBy HandyWorker
	//
	//			final double pendingApps = this.applicationService.pendingApplications();
	//			Assert.isTrue(pendingApps > 0);
	//
	//			final double acceptedApps = this.applicationService.pendingApplications();
	//			Assert.isTrue(acceptedApps > 0);
	//
	//			final double rejectedApps = this.applicationService.pendingApplications();
	//			Assert.isTrue(rejectedApps > 0);
	//
	//			final Date date = new GregorianCalendar(2012, 2, 2).getTime();
	//			app2.setMoment(date);
	//			app2 = this.applicationService.saveByCustomer(app2);
	//			final double elapsedApps = this.applicationService.elapsedApplications();
	//			Assert.isTrue(elapsedApps > 0);
	//
	//			final ArrayList<Object> offPStats = this.applicationService.offeredPriceStatisctics();
	//			Assert.notNull(offPStats);
	//
	//			System.out.println("Success!");
	//
	//		} catch (final Exception e) {
	//			System.out.println("Error, " + e.getMessage() + "!");
	//		}
	//	}
}
