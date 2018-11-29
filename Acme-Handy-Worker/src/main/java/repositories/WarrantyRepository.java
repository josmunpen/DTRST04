
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Warranty;

@Repository
public interface WarrantyRepository extends JpaRepository<Warranty, Integer> {

	//12.2
	@Query("select distinct w from FixUpTask f join f.warranty w where w.id=?1 and w.finalMode='true'")
	Collection<Warranty> findByWarrantyId(int warrantyId);

}
