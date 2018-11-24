
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Administrator;
import domain.Category;

public class CategoryService {

	@Autowired
	public CategoryRepository	categoryRepository;

	@Autowired
	public AdministratorService	administratorService;


	//12.3
	public Category create() {
		final Administrator admin;
		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Category result;

		result = new Category();

		this.categoryRepository.save(result);

		return result;
	}

	public Category save(final Category category) {
		final Administrator admin;
		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Assert.notNull(category);
		Category res;

		res = this.categoryRepository.save(category);
		return res;
	}

	public void delete(final Category category) {
		final Administrator admin;
		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		this.categoryRepository.delete(category);

	}

}
