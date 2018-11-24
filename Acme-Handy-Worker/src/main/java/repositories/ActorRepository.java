
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer> {

	//38.2
	@Query("select s from Message m join m.sender s where m.flagSpam=true")
	Collection<Actor> suspiciousActors();

}
