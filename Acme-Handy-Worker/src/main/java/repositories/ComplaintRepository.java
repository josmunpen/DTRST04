
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

	//TODO:35.1
	//@Query("select ")
	//Collection<Complaint> findByCustomerId(int customerId);

}
