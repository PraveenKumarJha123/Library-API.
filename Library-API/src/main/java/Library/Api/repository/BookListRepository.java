package Library.Api.repository;

import Library.Api.entity.Book_list;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookListRepository extends JpaRepository<Book_list, Integer> {
    List<Book_list> findByBookNameContainingIgnoreCase(String bookName);
}