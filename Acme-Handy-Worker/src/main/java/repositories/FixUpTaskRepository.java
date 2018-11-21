
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer> {

	//Query 10.1 (METIENDO ID DE CUSTOMER)
	@Query("select distinct c.fixuptasks from Customer c where c.id=?1")
	Collection<FixUpTask> findByCustomerId(int customerId);

	/*
	 * //Query 10.1 (METIENDO ID DE USERACCOUNT)
	 * 
	 * @Query("select distinct c.fixuptasks from Customer c where c.id=?1")
	 * Collection<FixUpTask> findByCustomerId(int customerId);
	 */

	//Query 11.2
	/*
	 * static final String qry = "select f from FixUpTask f";
	 * if(keyword)
	 */

}
