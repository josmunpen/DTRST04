
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Category;

public class CategoryService {

	@Autowired
	public CategoryRepository	categoryRepository;


	//12.3
	public Category create() {
		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		final Category result = new Category();
		result.setParentCategory(new Category());

		return result;
	}
	public Category save(final Category category) {
		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		Assert.notNull(category);
		Assert.isTrue(category.getId() != 0);

		Category res;
		res = this.categoryRepository.save(category);
		return res;
	}

	public void delete(final Category category) {
		//Logged user must be an administrator
		final Authority a = new Authority();
		final UserAccount user = LoginService.getPrincipal();
		a.setAuthority(Authority.ADMIN);
		Assert.isTrue(user.getAuthorities().contains(a));

		Assert.notNull(category);
		Assert.isTrue(category.getId() != 0);

		this.categoryRepository.delete(category);

	}

}
