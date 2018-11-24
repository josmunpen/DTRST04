
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Administrator;
import domain.Message;

public class MessageService {

	@Autowired
	public MessageRepository	messageRepository;

	@Autowired
	public AdministratorService	administratorService;


	//12.4
	public Message createForActor(final Actor actor) {
		final Administrator admin;
		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		final Message m = new Message();
		m.setRecipient(actor);
		m.setSender(admin);

		return m;

	}

}
