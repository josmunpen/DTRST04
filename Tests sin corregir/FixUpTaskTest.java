
package service;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ApplicationService;
import services.CategoryService;
import services.CustomerService;
import services.FixUpTaskService;
import services.PhaseService;
import services.WarrantyService;
import utilities.AbstractTest;
import domain.FixUpTask;
import domain.Money;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FixUpTaskTest extends AbstractTest {

	// Service----------------------------------------------------------
	@Autowired
	private FixUpTaskService	fixUpTaskService;

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private WarrantyService		warrantyService;

	@Autowired
	private PhaseService		phaseService;

	@Autowired
	private ApplicationService	applicationService;


	// Test-------------------------------------------------------------

	@Test
	public void test() {
		final Money m = new Money();
		m.setAmount(25);
		m.setCurrency("Euros");
		final FixUpTask fixUpTask = this.fixUpTaskService.create();
		fixUpTask.setTicker("tickerExpress");
		fixUpTask.setMoment(new Date());
		fixUpTask.setAddress("address");
		fixUpTask.setDescription("description");
		fixUpTask.setMaximumPrice(m);
		final int categoryId = this.getEntityId("category1");
		final int customerId = this.getEntityId("customer1");
		final int warrantyId = this.getEntityId("warranty1");
		fixUpTask.setCategory(this.categoryService.findOne(categoryId));
		fixUpTask.setCustomer(this.customerService.findOne(customerId));
		fixUpTask.setWarranty(this.warrantyService.findOne(warrantyId));
		final FixUpTask saved = this.fixUpTaskService.save(fixUpTask);
		Assert.isTrue(this.fixUpTaskService.findAll().contains(saved));
		Assert.isTrue(this.fixUpTaskService.findOne(saved.getId()) == saved);
		saved.setAddress("addressEdited");
		final FixUpTask saved2 = this.fixUpTaskService.save(saved);
		Assert.isTrue(this.fixUpTaskService.findOne(saved2.getId()).getAddress() == "addressEdited");
		this.fixUpTaskService.delete(saved);
		Assert.isTrue(!this.fixUpTaskService.findAll().contains(saved));
		final int phaseId = this.getEntityId("phase1");
		final int fixUpTaskId = this.getEntityId("fixuptask3");
		this.fixUpTaskService.delete(this.fixUpTaskService.findOne(fixUpTaskId));
		Assert.isNull(this.phaseService.findOne(phaseId));

	}
}
