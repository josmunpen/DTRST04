
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Customer;

@Service
@Transactional
public class CustomerService {

	//Repository
	@Autowired
	private CustomerRepository	customerRepository;


	//Services

	//Constructor
	public CustomerService() {
		super();
	}

	//Simple CRUD

	//9.1
	public Customer create() {
		Customer result;

		result = new Customer();

		return result;
	}

	//9.2
	public Customer save(final Customer customer) {
		Assert.notNull(customer);
		Customer res;
		//Restricciones
		res = this.customerRepository.save(customer);
		return res;
	}

	//Complex methods

	//Returns logged customer
	public Customer findByPrincipal() {
		Customer res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);

		return res;
	}

	//Returns logged customer from his or her userAccount
	public Customer findByUserAccount(final UserAccount userAccount) {
		Customer res;
		Assert.notNull(userAccount);

		res = this.customerRepository.findByUserAccountId(userAccount.getId());

		return res;
	}
}
