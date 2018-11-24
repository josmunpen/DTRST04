
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WarrantyRepository;
import domain.Administrator;
import domain.Warranty;

@Service
@Transactional
public class WarrantyService {

	@Autowired
	public WarrantyRepository	warrantyRepository;

	@Autowired
	public AdministratorService	administratorService;


	//12.2
	public void delete(final Warranty warranty) {
		final Administrator admin;
		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Assert.isTrue(warranty.isFinalMode() == false);

		this.warrantyRepository.delete(warranty);

	}

	//12.2
	public Warranty save(final Warranty warranty) {
		final Administrator admin;
		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Assert.isTrue(warranty.isFinalMode() == false);

		this.warrantyRepository.delete(warranty.getId());
		this.warrantyRepository.save(warranty);

		return warranty;

	}
}
