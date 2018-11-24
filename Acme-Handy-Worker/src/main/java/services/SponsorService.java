
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.SponsorRepository;
import security.Authority;
import security.UserAccount;
import domain.Box;
import domain.Sponsor;

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
		Sponsor result;
		result = new Sponsor();
		final Box trash = new Box();
		final Box out = new Box();
		final Box spam = new Box();
		final Box in = new Box();
		final List<Box> predefined = new ArrayList<Box>();
		predefined.add(in);
		predefined.add(out);
		predefined.add(spam);
		predefined.add(trash);
		result.setBoxes(new ArrayList<Box>(predefined));
		final UserAccount user = new UserAccount();
		final Authority a = new Authority();
		a.setAuthority(Authority.REFEREE);
		user.addAuthority(a);
		result.setUserAccount(user);
		return result;
	}

}
