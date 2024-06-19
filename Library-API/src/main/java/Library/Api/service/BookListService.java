package Library.Api.service;



import Library.Api.entity.Book_list;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookListService {

    Book_list createBook(Book_list book);

    List<Book_list> getAllBooks();

    Optional<Book_list> getBookById(Integer id);

    Book_list updateBook(Integer id, Book_list bookDetails);

    void deleteBook(Integer id);

    List<Book_list> bulkAddBooks(List<Book_list> books);

    Page<Book_list> getBooks(Pageable pageable);

    List<Book_list> searchBooksByName(String bookName);
}
