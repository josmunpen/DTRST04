
package repositories;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer> {

	//Query 10.1 (METIENDO ID DE CUSTOMER)
	@Query("select distinct c.fixUpTasks from Customer c where c.id=?1")
	Collection<FixUpTask> findByCustomerId(int customerId);

	/*
	 * //Query 10.1 (METIENDO ID DE USERACCOUNT)
	 * 
	 * @Query("select distinct c.fixuptasks from Customer c where c.id=?1")
	 * Collection<FixUpTask> findByCustomerId(int customerId);
	 */

	//TODO:Query 11.2
	/*
	 * @Query("select f from FixUpTask where f.description like '%?1%' or f.address like '%?1%' or " +
	 * "f.category.name = ?2 or f.")
	 */

	//12.5
	@Query("select avg(f.applications.size), min(f.applications.size), max(f.applications.size), stddev(f.applications.size) from FixUpTask f")
	ArrayList<Object> applicationsStatistics();

	@Query("select avg(f.maximumPrice.amount), min(f.maximumPrice.amount), max(f.maximumPrice.amount), stddev(f.maximumPrice.amount) from FixUpTask f")
	ArrayList<Object> maximunPriceStatistics();

	//38.5
	@Query("select min(f.complaints.size), max(f.complaints.size), avg(f.complaints.size), stddev(f.complaints.size) from FixUpTask f")
	ArrayList<Object> complaintsStatistics();

	@Query("select count(f)/(select count(f1) from FixUpTask f1) from FixUpTask f where f.complaints.size>0")
	double fixUpTasksWithComplaints();
	

}