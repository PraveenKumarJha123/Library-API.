package Library.Api.controller;




import Library.Api.entity.IssuedBook;
import Library.Api.entity.ReturnedBook;
import Library.Api.service.IssuedBookService;
import Library.Api.service.ReturnedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/returned-books")
public class ReturnedBookController {


    @Autowired
    private IssuedBookService issuedBookService;

    @Autowired
    private ReturnedBookService returnedBookService;

    @PostMapping
    public ResponseEntity<ReturnedBook> createReturnedBook(@RequestBody ReturnedBook returnedBook) {
        ReturnedBook createdReturnedBook = returnedBookService.createReturnedBook(returnedBook);
        return ResponseEntity.ok(createdReturnedBook);
    }

    @GetMapping
    public ResponseEntity<List<ReturnedBook>> getAllReturnedBooks() {
        List<ReturnedBook> returnedBooks = returnedBookService.getAllReturnedBooks();
        return ResponseEntity.ok(returnedBooks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnedBook> getReturnedBookById(@PathVariable Integer id) {
        Optional<ReturnedBook> returnedBook = returnedBookService.getReturnedBookById(id);
        return returnedBook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReturnedBook> updateReturnedBook(@PathVariable Integer id, @RequestBody ReturnedBook returnedBookDetails) {
        ReturnedBook updatedReturnedBook = returnedBookService.updateReturnedBook(id, returnedBookDetails);
        return ResponseEntity.ok(updatedReturnedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReturnedBook(@PathVariable Integer id) {
        returnedBookService.deleteReturnedBook(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/{id}/bill")
    public ResponseEntity<String> generateBill(@PathVariable Integer id) {
        Optional<IssuedBook> issuedBookOptional = issuedBookService.getIssuedBookById(id);

        if (issuedBookOptional.isPresent()) {
            IssuedBook issuedBook = issuedBookOptional.get();

            StringBuilder billDetails = new StringBuilder();
            billDetails.append("Member Name: ").append(issuedBook.getMembersList().getName()).append("\n");
            billDetails.append("Book Name: ").append(issuedBook.getBook_list().getBookName()).append("\n");
            billDetails.append("Expected Return Date: ").append(issuedBook.getReturnedDate()).append("\n");
            billDetails.append("Expected Price: ").append(issuedBook.getExpectedPrice()).append("\n");
            billDetails.append("Actual Return Date: ").append(issuedBook.getReturnedBooks().get(0).getActualReturnedDate()).append("\n");
            billDetails.append("Actual Price: ").append(issuedBook.getReturnedBooks().get(0).getTotalAmount()).append("\n");

            return ResponseEntity.ok(billDetails.toString());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

