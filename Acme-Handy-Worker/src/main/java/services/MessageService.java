
package services;

import java.util.ArrayList;
import java.util.Collection;
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

		//TODO:Recipient
		return res;
	}

	//9.3
	public Message save(final Message message) {
		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);

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
		Assert.notNull(message.getMoment());
		Assert.isTrue(message.getPriority() == "HIGH" || message.getPriority() == "LOW" || message.getPriority() == "NEUTRAL");
		Assert.notNull(message.getBody());
		Assert.notNull(message.getSubject());
		final Collection<Customisation> customisation = this.customisationService.findAll();
		final List<Customisation> customisation1 = new ArrayList<Customisation>(customisation);
		final List<String> l1 = new ArrayList<String>(customisation1.get(0).getSpamWords());
		final String asunto = message.getSubject();
		for (final String spamWord : l1)
			if (asunto.contains(spamWord)) {
				message.setFlagSpam(true);
				break;
			}
		Assert.isTrue(message.getFlagSpam() == false);
		Assert.notNull(message.getSender());
		Assert.notNull(message.getRecipient());

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

		Assert.isTrue(user.getAuthorities().contains(a) || user.getAuthorities().contains(b) || user.getAuthorities().contains(c) || user.getAuthorities().contains(d) || user.getAuthorities().contains(e));

		//Restrictions
		Assert.notNull(message.getMoment());
		Assert.notNull(message.getPriority());
		Assert.notNull(message.getBody());
		Assert.notNull(message.getSubject());
		Assert.isTrue(message.getFlagSpam() == false);
		Assert.notNull(message.getSender());
		Assert.notNull(message.getRecipient());

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

}
