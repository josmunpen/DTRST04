
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Box;
import domain.Customisation;
import domain.Message;

@Service
@Transactional
public class MessageService {

	//Repository
	@Autowired
	public MessageRepository	messageRepository;

	//Services
	@Autowired
	public ActorService			actorService;

	@Autowired
	public CustomisationService	customisationService;


	//Constructor
	public MessageService() {
		super();
	}

	//Simple CRUD
	public Message create() {
		Message res;
		res = new Message();
		//Logged actor is sender
		final Actor logActor;
		logActor = this.actorService.findByPrincipal();
		Assert.notNull(logActor);
		Assert.notNull(logActor.getId());
		res.setSender(logActor);
		//TODO: Revisar null
		final Actor recipient = null;
		res.setRecipient(recipient);
		res.setMoment(new Date());
		res.setPriority("");
		res.setTag("");
		res.setBody("");
		res.setSubject("");
		res.setFlagSpam(false);
		return res;
	}
	//9.3
	public Message save(final Message message) {
		Assert.notNull(message);
		Assert.isTrue(message.getId() == 0);

		//Logged user must be a customer/handyworker
		final Authority a = new Authority();
		final Authority b = new Authority();
		final Authority c = new Authority();
		final Authority d = new Authority();
		final Authority e = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		b.setAuthority(Authority.HANDYWORKER);
		c.setAuthority(Authority.REFEREE);
		d.setAuthority(Authority.ADMIN);
		e.setAuthority(Authority.SPONSOR);
		Assert.isTrue(user.getAuthorities().contains(a) || user.getAuthorities().contains(b) || user.getAuthorities().contains(c) || user.getAuthorities().contains(d) || user.getAuthorities().contains(e));

		//Restrictions
		final Collection<Customisation> customisation = this.customisationService.findAll();
		final List<Customisation> customisation1 = new ArrayList<Customisation>(customisation);
		final List<String> spamWords = new ArrayList<String>(customisation1.get(0).getSpamWords());
		final String asunto = message.getSubject();
		final String cuerpo = message.getBody();
		final String texto = asunto + cuerpo;
		for (final String spamWord : spamWords)
			if (texto.toLowerCase().contains(spamWord)) {
				message.setFlagSpam(true);
				break;
			}
		Assert.isTrue(message.getFlagSpam() == false);

		//Logged actor is sender
		final Actor logActor;
		logActor = this.actorService.findByPrincipal();
		Assert.notNull(logActor);
		Assert.notNull(logActor.getId());
		Assert.isTrue(logActor.equals(message.getSender()));

		//Message saves in sender's out box

		for (final Box box : logActor.getBoxes())
			if (box.getName() == "out") {
				final Collection<Message> col = box.getMessages();
				col.add(message);
				box.setMessages(col);
			}

		/*
		 * final List<Box> logBoxes = new ArrayList<Box>(logActor.getBoxes());
		 * final Box in = logBoxes.get(1);
		 * final Collection<Message> inMessages = in.getMessages();
		 * inMessages.add(message);
		 */

		//Message saves in recipient's corresponding box
		final Actor recipient = message.getRecipient();
		for (final Box box : recipient.getBoxes())
			if (box.getName() == "in" && !message.getFlagSpam()) {

				final Collection<Message> col = box.getMessages();
				col.add(message);
				box.setMessages(col);

			} else if (box.getName() == "spam" && message.getFlagSpam()) {
				final Collection<Message> col = box.getMessages();
				col.add(message);
				box.setMessages(col);

			}

		final Message res;

		res = this.messageRepository.save(message);

		return res;
	}
	public void delete(final Message message) {
		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);
		Assert.isTrue(this.messageRepository.exists(message.getId()));

		//Logged user must be an actor
		final Authority a = new Authority();
		final Authority b = new Authority();
		final Authority c = new Authority();
		final Authority d = new Authority();
		final Authority e = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		b.setAuthority(Authority.HANDYWORKER);
		c.setAuthority(Authority.REFEREE);
		d.setAuthority(Authority.SPONSOR);
		e.setAuthority(Authority.ADMIN);

		//Logged actor is sender or recipient
		final Actor logActor;
		logActor = this.actorService.findByPrincipal();
		Assert.notNull(logActor);
		Assert.notNull(logActor.getId());
		Assert.isTrue(logActor.equals(message.getSender()) || logActor.equals(message.getRecipient()));

		Assert.isTrue(user.getAuthorities().contains(a) || user.getAuthorities().contains(b) || user.getAuthorities().contains(c) || user.getAuthorities().contains(d) || user.getAuthorities().contains(e));

		//Restrictions
		Assert.isTrue(message.getFlagSpam() == false);

		this.messageRepository.delete(message);

	}

	//12.4
	//TODO: REVISAR ENTERA
	public Message createForActor(final Actor actor) {
		final Actor admin;
		admin = this.actorService.findByPrincipal();
		Assert.notNull(admin);

		final Message m = new Message();
		m.setRecipient(actor);
		m.setSender(admin);

		return m;

	}
	public Message sendAll(final Message message) {
		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);
		final Message res;

		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		for (final Actor act : this.actorService.findAll())
			//Message saves in recipient's corresponding box
			for (final Box box : act.getBoxes())
				if (box.getName() == "in" && !message.getFlagSpam()) {

					final Collection<Message> col = box.getMessages();
					col.add(message);
					box.setMessages(col);

				} else if (box.getName() == "spam" && message.getFlagSpam()) {
					final Collection<Message> col = box.getMessages();
					col.add(message);
					box.setMessages(col);

				}

		res = this.messageRepository.save(message);

		return res;
	}
}
