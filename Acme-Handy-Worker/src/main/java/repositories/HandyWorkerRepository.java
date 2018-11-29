
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.HandyWorker;

@Repository
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer> {

	@Query("select h from HandyWorker h where h.userAccount.id = ?1")
	HandyWorker findByUserAccountId(int userAccount);

	//12.5
	@Query("select h from HandyWorker h where h.applications.size >= (select(1.1*avg(h2.applications.size)) from HandyWorker h2) group by h.applications.size")
	Collection<HandyWorker> handyWorkersWithMoreApplications();

	//38.5
	@Query("select distinct hw from HandyWorker hw join hw.applications a where a.fixUpTask.complaints.size>0 order by count(hw.applications.size) desc")
	Collection<HandyWorker> topThreeHandyWorkersByComplaints();

}
