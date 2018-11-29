
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

	//35.2
	@Query("select c from Customer c join c.fixUpTasks f where c.id=?1")
	Collection<Application> findByCustomerId(int customerId);

}
