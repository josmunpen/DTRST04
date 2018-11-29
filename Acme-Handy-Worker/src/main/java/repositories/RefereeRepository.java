
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Referee;

@Repository
public interface RefereeRepository extends JpaRepository<Referee, Integer> {

	@Query("select r from Referee r where r.userAccount.id = ?1")
	Referee findByUserAccountId(int userAccount);
	
	@Query("select r1 from Referee r join r.reports r1 where r1.id = ?1")
	Referee findByReportId(int id);
	
	

}
