
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;
import domain.Tutorial;

@Repository
public interface TutorialRepository extends JpaRepository<Tutorial, Integer> {

	//49.1
	@Query("select t from Tutorial t where t.handyWorker.id=?1")
	Collection<FixUpTask> findByHandyWorkerId(int handyWorkerId);

}
