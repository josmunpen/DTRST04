
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Message;

@Service
@Transactional
public class MessageService {

	//Repository
	@Autowired
	public MessageRepository	messageRepository;


	//Services

	//Constructor
	public MessageService() {
		super();
	}

	//Simple CRUD 

	public Message create() {
		Message res;
		res = new Message();
		//TODO: DATE

		return res;
	}

	//9.3
	public Message save(final Message message) {
		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);

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

}
