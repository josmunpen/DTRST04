
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Phase;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, Integer> {

	//11.4
	@Query("select h.plannedPhases from HandyWorker h where h.id=?1")
	Collection<Phase> findByHandyWorkerId(int handyWorkerId);

}
