
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
import domain.Actor;
import domain.Box;
import domain.SocialProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ActorServiceTest extends AbstractTest {

	//Service
	@Autowired
	private ActorService	actorService;


	//Test
	@Test
	public void testCreate() {
		System.out.println("------Test Create------");
		final Actor actor, actor2, saved;
		final Collection<SocialProfile> sp1 = new ArrayList<>();
		final Collection<Box> boxes1 = new ArrayList<>();
		actor = this.actorService.create();
		try {
			super.authenticate("admin");
			actor.setName("Pepe");
			actor.setEmail("actorPepe@gmail.com");
			actor.setPhoneNumber("123456789");
			actor.setAddress("PepeAddress");
			actor.setBan(false);
			actor.setMiddleName("PepeMiddleName");
			actor.setSurname("PepeSurname");
			actor.setPhotoURL("http://www.urlpepe.com");
			actor.setSocialProfiles(sp1);
			actor.setUserAccount(new UserAccount());
			actor.setBoxes(boxes1);

			saved = this.actorService.save(actor);
			Assert.isTrue(this.actorService.findAll().contains(saved));

			super.unauthenticate();

			System.out.println("Success!");

		} catch (final Exception e) {
			System.out.println("Error, " + e.getMessage() + "!");
		}
	}

	@Test
	public void testBanActor() {
		System.out.println("------Test BanActor------");
		final Actor actor;
		final Collection<SocialProfile> sp1 = new ArrayList<>();
		final Collection<Box> boxes1 = new ArrayList<>();
		actor = this.actorService.create();
		actor.setName("Pepe");
		actor.setEmail("actorPepe@gmail.com");
		actor.setPhoneNumber("123456789");
		actor.setAddress("PepeAddress");
		actor.setBan(false);
		actor.setMiddleName("PepeMiddleName");
		actor.setSurname("PepeSurname");
		actor.setPhotoURL("http://www.urlpepe.com");
		actor.setSocialProfiles(sp1);
		actor.setUserAccount(new UserAccount());
		actor.setBoxes(boxes1);
		try {
			actor = this.actorService.banActor(actor);
			Assert.isTrue(actor.getBan() == true);

			System.out.println("Success!");
		} catch (final Exception e) {
			System.out.println("Error, " + e.getMessage() + "!");
		}
	}

	@Test
	public void testUnbanActor() {
		System.out.println("------Test UnbanActor------");
		final Actor actor;
		final Collection<SocialProfile> sp1 = new ArrayList<>();
		final Collection<Box> boxes1 = new ArrayList<>();
		actor = this.actorService.create();
		actor.setName("Pepe");
		actor.setEmail("actorPepe@gmail.com");
		actor.setPhoneNumber("123456789");
		actor.setAddress("PepeAddress");
		actor.setBan(true);
		actor.setMiddleName("PepeMiddleName");
		actor.setSurname("PepeSurname");
		actor.setPhotoURL("http://www.urlpepe.com");
		actor.setSocialProfiles(sp1);
		actor.setUserAccount(new UserAccount());
		actor.setBoxes(boxes1);
		try {
			actor = this.actorService.UnbanActor(actor);
			Assert.isTrue(actor.getBan() == false);

			System.out.println("Success!");
		} catch (final Exception e) {
			System.out.println("Error, " + e.getMessage() + "!");
		}
	}

	public void testSuspiciousActors() {
		final Collection<Actor> SuspActors;
		//TODO
		try {

			System.out.println("Success!");
		} catch (final Exception e) {
			System.out.println("Error, " + e.getMessage() + "!");
		}
	}
}
