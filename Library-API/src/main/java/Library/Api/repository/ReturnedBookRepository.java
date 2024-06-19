package Library.Api.repository;



import Library.Api.entity.ReturnedBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnedBookRepository extends JpaRepository<ReturnedBook, Integer> {
}
