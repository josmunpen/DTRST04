
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Category;

public class CategoryService {

	@Autowired
	public CategoryRepository	categoryRepository;


	//12.3
	public Category create() {
		Category result;

		result = new Category();

		return result;
	}

	public Category save(final Category category) {
		Assert.notNull(category);
		Category res;

		res = this.categoryRepository.save(category);
		return res;
	}

	public void delete(final Category category) {
		this.categoryRepository.delete(category);

	}

}
