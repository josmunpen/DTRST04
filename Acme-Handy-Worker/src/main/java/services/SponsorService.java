
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Box;
import domain.SocialProfile;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorService {

	//Repository
	public SponsorRepository	sponsorRepository;


	//Services

	//Constructor
	public SponsorService() {
		super();
	}

	//Simple CRUD methods
	//47.1
	public Sponsor create() {
		//User can't be logged to register
		final Authority a = new Authority();
		final Authority b = new Authority();
		final Authority c = new Authority();
		final Authority d = new Authority();
		final Authority e = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		b.setAuthority(Authority.HANDYWORKER);
		c.setAuthority(Authority.CUSTOMER);
		d.setAuthority(Authority.REFEREE);
		e.setAuthority(Authority.SPONSOR);
		Assert.isTrue(!(user.getAuthorities().contains(a) || user.getAuthorities().contains(b) || user.getAuthorities().contains(c) || user.getAuthorities().contains(d) || user.getAuthorities().contains(e)));

		Sponsor result;
		result = new Sponsor();

		//Actor
		final Box trash = new Box();
		final Box out = new Box();
		final Box spam = new Box();
		final Box in = new Box();
		trash.setName("trash");
		in.setName("in");
		out.setName("out");
		spam.setName("spam");
		out.setPredefined(true);
		in.setPredefined(true);
		spam.setPredefined(true);
		trash.setPredefined(true);
		final List<Box> predefined = new ArrayList<Box>();
		predefined.add(in);
		predefined.add(out);
		predefined.add(spam);
		predefined.add(trash);

		final UserAccount newUser = new UserAccount();
		final Authority f = new Authority();
		f.setAuthority(Authority.SPONSOR);
		newUser.addAuthority(f);

		result.setBoxes(new ArrayList<Box>(predefined));
		result.setSocialProfiles(new ArrayList<SocialProfile>());
		result.setName("");
		result.setEmail("");
		result.setAddress("");
		result.setSurname("");
		result.setPhoneNumber("");
		result.setPhotoURL("");

		//Sponsor
		result.setSponsorships(new ArrayList<Sponsorship>());
		result.setUserAccount(user);
		return result;
	}
}
