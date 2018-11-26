
package repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Report;

public interface ReportRepository extends JpaRepository<Report, Integer> {

	//38.5
	@Query("select min(r.notes.size), max(r.notes.size), avg(r.notes.size), stddev(r.notes.size) from Report r")
	ArrayList<Object> notesStatistics();
}
