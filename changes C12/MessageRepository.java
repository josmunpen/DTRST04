
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

}
