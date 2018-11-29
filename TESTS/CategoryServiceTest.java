
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest {

	//Service
	@Autowired
	private CategoryService	categoryService;


	//Test
	@Test
	public void testCategory() {
		System.out.println("------Test Category------");
		final Category cat, saved;
		cat = this.categoryService.create();
		try {
			super.authenticate("admin");
			cat.setName("Category1");
			cat.setParentCategory(new Category());
			saved = this.categoryService.save(cat);
			Assert.isTrue(this.categoryService.findAll().contains(saved));

			this.categoryService.delete(cat);
			Assert.isNull(this.categoryService.findOne(cat));

			super.unauthenticate();

			System.out.println("Success!");

		} catch (final Exception e) {
			System.out.println("Error, " + e.getMessage() + "!");
		}
	}

}
