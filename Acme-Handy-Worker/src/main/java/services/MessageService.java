
package services;

import java.util.Collection;

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

		for (final Box b : logActor.getBoxes())
			if (b.getName() == "out") {
				final Collection<Message> col = b.getMessages();
				col.add(res);
				b.setMessages(col);
			}
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
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		b.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(a) || user.getAuthorities().contains(b));

		//Restrictions
		Assert.notNull(message.getMoment());
		Assert.notNull(message.getPriority());
		Assert.notNull(message.getBody());
		Assert.notNull(message.getSubject());
		Assert.isTrue(message.getFlagSpam() == false);
		Assert.notNull(message.getSender());
		Assert.notNull(message.getRecipient());

		//Logged actor is sender
		final Actor logActor;
		logActor = this.actorService.findByPrincipal();
		Assert.notNull(logActor);
		Assert.notNull(logActor.getId());
		Assert.isTrue(logActor.equals(message.getSender()));

		for (final Box box : logActor.getBoxes())
			if (box.getName() == "out") {
				final Collection<Message> col = box.getMessages();
				col.add(res);
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

		//Logged user must be a customer/handyworker
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(user.getAuthorities().contains(a));
		//TODO: or a handyworker

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
