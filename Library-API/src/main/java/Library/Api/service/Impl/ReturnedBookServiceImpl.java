package Library.Api.service.Impl;



import Library.Api.entity.Book_list;
import Library.Api.entity.IssuedBook;
import Library.Api.entity.ReturnedBook;
import Library.Api.exception.ResourceNotFoundException;
import Library.Api.repository.BookListRepository;
import Library.Api.repository.IssuedBookRepository;
import Library.Api.repository.ReturnedBookRepository;
import Library.Api.service.ReturnedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class ReturnedBookServiceImpl implements ReturnedBookService {

    @Autowired
    private ReturnedBookRepository returnedBookRepository;

    @Autowired
    private IssuedBookRepository issuedBookRepository;

    @Autowired
    private BookListRepository bookListRepository;

    private double calculateTotalAmount(Date actualReturnedDate, Date expectedReturnedDate, double expectedPrice) {
        long diffInMillies = actualReturnedDate.getTime() - expectedReturnedDate.getTime();
        long diffDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        double lateFee = diffDays > 0 ? diffDays * 20 : 0;
        return expectedPrice + lateFee;
    }

    @Override
    public ReturnedBook createReturnedBook(ReturnedBook returnedBook) {
        IssuedBook issuedBook = issuedBookRepository.findById(returnedBook.getIssuedBook().getId())
                .orElseThrow(() -> new ResourceNotFoundException("IssuedBook", "id", returnedBook.getIssuedBook().getId()));

        Book_list book = issuedBook.getBook_list();

        double totalAmount = calculateTotalAmount(returnedBook.getActualReturnedDate(), issuedBook.getReturnedDate(), issuedBook.getExpectedPrice());
        returnedBook.setTotalAmount(totalAmount);

        // Increment book count
        book.setBookCount(book.getBookCount() + 1);
        book.setBookStatus(true);
        bookListRepository.save(book);

        return returnedBookRepository.save(returnedBook);
    }

    @Override
    public List<ReturnedBook> getAllReturnedBooks() {
        return returnedBookRepository.findAll();
    }

    @Override
    public Optional<ReturnedBook> getReturnedBookById(Integer id) {
        return returnedBookRepository.findById(id);
    }

    @Override
    public ReturnedBook updateReturnedBook(Integer id, ReturnedBook returnedBookDetails) {
        ReturnedBook returnedBook = returnedBookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReturnedBook", "id", id));

        returnedBook.setActualReturnedDate(returnedBookDetails.getActualReturnedDate());
        returnedBook.setTotalAmount(returnedBookDetails.getTotalAmount());
        returnedBook.setIssuedBook(returnedBookDetails.getIssuedBook());

        return returnedBookRepository.save(returnedBook);
    }

    @Override
    public void deleteReturnedBook(Integer id) {
        ReturnedBook returnedBook = returnedBookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReturnedBook", "id", id));

        returnedBookRepository.delete(returnedBook);
    }
}
