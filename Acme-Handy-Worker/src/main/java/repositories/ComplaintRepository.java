
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

	@Query("select f.complaints from Customer c join c.fixUpTasks f where c.id=?1")
	Collection<Complaint> findByCustomerId(int customerId);

	//Nueva 36.1
	@Query("select c from Complaint c join Referee r where c not member of r.complaints")
	Collection<Complaint> findComplaintsNoReferee();
	//Query que seleccione las complaints sin referee asignado
	@Query("select r.complaint from Report r join Complaint c where c not member of r.complaint")
	Collection<Complaint> findComplaintsWithNoReferee();

	//36.2
	@Query("select distinct r.complaints from Referee r where r.id=?1")
	Collection<Complaint> findByRefereeId(int refereeId);
	
	//37.3
	@Query("select distinct f1.complaints from HandyWorker h join h.applications a1 join a1.fixUpTasks f1 where h.id=?1")
	Collection<Complaint> findByHandyWorker(int id);
}
