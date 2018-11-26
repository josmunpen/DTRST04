
package repositories;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select c from Customer c where c.userAccount.id = ?1")
	Customer findByUserAccountId(int userAccount);

	//Query 11.1
	//Navigate to customer from fixuptask
	@Query("select c from Customer c join c.fixUpTasks f where f.id=?1")
	Customer findFixUpTaskById(int fixUpTaskId);

	//12.5
	@Query("select avg(c.fixUpTasks.size), min(c.fixUpTasks.size), max(c.fixUpTasks.size), stddev(c.fixUpTasks.size)from Customer c")
	ArrayList<Object> fixUpTaskStatistics();

	@Query("select c from Customer c join c.fixUpTasks f where c.fixUpTasks.size > (select 1.1 * avg(c2.fixUpTasks.size) from Customer c2) order by (f.applications.size)")
	Collection<Customer> customersWithMoreFixUpTasks();

	@Query("select c from Customer c where ?1 in c.fixUpTasks")
	Customer findByFixUpTask(int id);

	//38.5
	@Query("select distinct c from Customer c join c.fixUpTasks f order by f.complaints.size desc")
	Collection<Customer> topThreeCustomersByComplaints();

}
