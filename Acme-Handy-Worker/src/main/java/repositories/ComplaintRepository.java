
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

	//35.1
	@Query("select f.complaints join c.fixUpTasks f from Customer c where c.id=?1")
	Collection<Complaint> complaintsByCustomer(int customerId);

	//TODO:36.1 Query que seleccione las complaints sin referee asignado
	@Query("select c from Complaint c where c")
	Collection<Complaint> findWithoutReferee();

	//36.2
	@Query("select distinct r.complaints from Referee r where r.id=?1")
	Collection<Complaint> findByRefereeId(int refereeId);
}
