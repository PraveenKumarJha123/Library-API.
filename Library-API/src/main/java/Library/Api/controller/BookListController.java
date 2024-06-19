package Library.Api.controller;





import Library.Api.entity.Book_list;
import Library.Api.service.BookListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookListController {

    @Autowired
    private BookListService bookListService;

    @PostMapping
    public ResponseEntity<Book_list> createBook(@RequestBody Book_list book) {
        Book_list createdBook = bookListService.createBook(book);
        return ResponseEntity.ok(createdBook);
    }

    @GetMapping
    public ResponseEntity<List<Book_list>> getAllBooks() {
        List<Book_list> books = bookListService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book_list> getBookById(@PathVariable Integer id) {
        Optional<Book_list> book = bookListService.getBookById(id);
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book_list> updateBook(@PathVariable Integer id, @RequestBody Book_list bookDetails) {
        Book_list updatedBook = bookListService.updateBook(id, bookDetails);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        bookListService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Book_list>> bulkAddBooks(@RequestBody List<Book_list> books) {
        List<Book_list> addedBooks = bookListService.bulkAddBooks(books);
        return ResponseEntity.ok(addedBooks);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Book_list>> getBooksPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book_list> booksPage = bookListService.getBooks(pageable);
        return ResponseEntity.ok(booksPage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book_list>> searchBooksByName(@RequestParam String bookName) {
        List<Book_list> books = bookListService.searchBooksByName(bookName);
        return ResponseEntity.ok(books);
    }
}
