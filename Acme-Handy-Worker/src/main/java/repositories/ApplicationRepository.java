
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	//Query 10.2
	//Tenemos que sacar las fixuptasks de las aplicaciones de un customer
	@Query("select f.applications from Customer c join c.fixUpTasks f where c.id=?1")
	Collection<Application> findByCustomerId(int customerId);

	//Query 11.3
	@Query("select h.applications from HandyWorker h where h.id=?1")
	Collection<Application> findByHandyWorkerId(int handyWorkerId);
}
