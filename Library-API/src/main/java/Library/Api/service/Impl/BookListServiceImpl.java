package Library.Api.service.Impl;



import Library.Api.entity.Book_list;
import Library.Api.exception.ResourceNotFoundException;

import Library.Api.repository.BookListRepository;
import Library.Api.service.BookListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookListServiceImpl implements BookListService {

    @Autowired
    private BookListRepository bookListRepository;

    @Override
    public Book_list createBook(Book_list book) {
        try {
            return bookListRepository.save(book);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("Error creating book: " + ex.getMessage());
        }
    }

    @Override
    public List<Book_list> getAllBooks() {
        return bookListRepository.findAll();
    }

    @Override
    public Optional<Book_list> getBookById(Integer id) {
        Optional<Book_list> book = bookListRepository.findById(id);
        if (!book.isPresent()) {
            throw new ResourceNotFoundException("Book", "id", id);
        }
        return book;
    }

    @Override
    public Book_list updateBook(Integer id, Book_list bookDetails) {
        Book_list book = bookListRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Book", "id", id));
        book.setBookName(bookDetails.getBookName());
        book.setBookCount(bookDetails.getBookCount());
        book.setBookStatus(bookDetails.getBookStatus());
        book.setBookAuthorName(bookDetails.getBookAuthorName());
        return bookListRepository.save(book);
    }

    @Override
    public void deleteBook(Integer id) {
        Book_list book = bookListRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Book", "id", id));
        bookListRepository.delete(book);
    }

    @Override
    public List<Book_list> bulkAddBooks(List<Book_list> books) {
        try {
            return bookListRepository.saveAll(books);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("Error adding books in bulk: " + ex.getMessage());
        }
    }

    @Override
    public Page<Book_list> getBooks(Pageable pageable) {
        return bookListRepository.findAll(pageable);
    }

    @Override
    public List<Book_list> searchBooksByName(String bookName) {
        return bookListRepository.findByBookNameContainingIgnoreCase(bookName);
    }
}
