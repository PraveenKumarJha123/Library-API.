package Library.Api.service;



import Library.Api.entity.IssuedBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IssuedBookService {
    IssuedBook createIssuedBook(IssuedBook issuedBook);
    List<IssuedBook> getAllIssuedBooks();
    Optional<IssuedBook> getIssuedBookById(Integer id);
    IssuedBook updateIssuedBook(Integer id, IssuedBook issuedBookDetails);
    void deleteIssuedBook(Integer id);
    Page<IssuedBook> getIssuedBooks(Pageable pageable);
}
