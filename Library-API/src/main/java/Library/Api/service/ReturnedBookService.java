package Library.Api.service;



import Library.Api.entity.ReturnedBook;

import java.util.List;
import java.util.Optional;

public interface ReturnedBookService {
    ReturnedBook createReturnedBook(ReturnedBook returnedBook);
    List<ReturnedBook> getAllReturnedBooks();
    Optional<ReturnedBook> getReturnedBookById(Integer id);
    ReturnedBook updateReturnedBook(Integer id, ReturnedBook returnedBookDetails);
    void deleteReturnedBook(Integer id);
}
