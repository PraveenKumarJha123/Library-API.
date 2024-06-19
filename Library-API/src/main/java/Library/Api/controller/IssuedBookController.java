package Library.Api.controller;


import Library.Api.entity.IssuedBook;
import Library.Api.service.IssuedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/issued-books")
public class IssuedBookController {

    @Autowired
    private IssuedBookService issuedBookService;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd,MM,yy");

    @PostMapping
    public ResponseEntity<IssuedBook> createIssuedBook(@RequestBody IssuedBook issuedBook) {
        IssuedBook createdIssuedBook = issuedBookService.createIssuedBook(issuedBook);
        return ResponseEntity.ok(createdIssuedBook);
    }

    @GetMapping
    public ResponseEntity<List<IssuedBook>> getAllIssuedBooks() {
        List<IssuedBook> issuedBooks = issuedBookService.getAllIssuedBooks();
        return ResponseEntity.ok(issuedBooks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssuedBook> getIssuedBookById(@PathVariable Integer id) {
        Optional<IssuedBook> issuedBook = issuedBookService.getIssuedBookById(id);
        return issuedBook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<IssuedBook> updateIssuedBook(@PathVariable Integer id, @RequestBody IssuedBook issuedBookDetails) {
        IssuedBook updatedIssuedBook = issuedBookService.updateIssuedBook(id, issuedBookDetails);
        return ResponseEntity.ok(updatedIssuedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssuedBook(@PathVariable Integer id) {
        issuedBookService.deleteIssuedBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<IssuedBook>> getIssuedBooksPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<IssuedBook> issuedBooksPage = issuedBookService.getIssuedBooks(pageable);
        return ResponseEntity.ok(issuedBooksPage);
    }
}
