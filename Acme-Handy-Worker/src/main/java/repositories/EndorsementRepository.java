
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Endorsement;

@Repository
public interface EndorsementRepository extends JpaRepository<Endorsement, Integer> {

	//48
	@Query("select distinct c.endorsements from Customer c where c.id=?1")
	Collection<Endorsement> findByCustomerId(int customerId);

}
