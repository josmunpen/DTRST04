
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;
import domain.Referee;

@Repository
public interface RefereeRepository extends JpaRepository<Referee, Integer> {

	@Query("select r from Referee r where r.userAccount.id = ?1")
	Referee findByUserAccountId(int userAccount);
	
	//Referee por reportId
	@Query("select r from Referee r where r.reports.id=?1")
	Referee getReportByReportId(int id);
	
	@Query("select r.complaint from Report r join Complaint c where c not member of r.complaint")
	Collection<Complaint> findComplaintsWithNoReferee();

}
