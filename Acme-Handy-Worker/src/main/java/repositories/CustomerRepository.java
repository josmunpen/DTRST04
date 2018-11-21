
package repositories;

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
}
