
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomisationRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Customisation;

@Service
@Transactional
public class CustomisationService {

	//Repository
	@Autowired
	public CustomisationRepository	customisationRepository;

	//Services
	@Autowired
	public AdministratorService		administratorService;


	//Constructor
	public CustomisationService() {
		super();
	}

	//Simple CRUD
	public Customisation create() {

		//Logged user must be a handyWorker
		final Authority a = new Authority();
		final Customisation res = new Customisation();
		final List<String> cardMakes = new ArrayList<String>();
		cardMakes.add("VISA");
		cardMakes.add("MASTER");
		cardMakes.add("DINNERS");
		cardMakes.add("AMEX");
		final List<String> spamWords = new ArrayList<String>();
		spamWords.add("sex");
		spamWords.add("viagra");
		spamWords.add("cialis");
		spamWords.add("one million");
		spamWords.add("you’ve been selected");
		spamWords.add("Nigeria");
		spamWords.add("sexo");
		spamWords.add("un millón");
		spamWords.add("ha sido seleccionado");
		res.setSpamWords(spamWords);
		res.setVATPercentage(21);
		res.setSystemName("Acme Handy Worker");
		res.setBannerURL("https://tinyurl.com/acme-handy-worker-logo");
		//TODO:HACER QUE DISTINGA ENTRE ESPAÑOL E INGLES “¡Bienvenidos a Acme Handy Worker! Precio, calidad y confianza en el mismo sitio”
		res.setWelcomeMessage("Welcome to Acme Handy Worker! Price, quality, and trust in a single place");
		res.setPhoneNumberCountryCode("+34");
		res.setCreditCardMakes(cardMakes);
		res.setFinderDuration(1);
		res.setResultsNumber(10);
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		return new Customisation();
	}

	public Customisation save(final Customisation customisation) {
		Assert.notNull(customisation);

		Customisation res;

		//Logged user must be an admin
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		res = this.customisationRepository.save(customisation);
		return res;
	}

	public Collection<Customisation> findAll() {

		//Logged user must be a handyWorker
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		// res;
		final Collection<Customisation> res = this.customisationRepository.findAll();
		return res;
	}

}
