package Library.Api.service.Impl;



import Library.Api.entity.Book_list;
import Library.Api.entity.IssuedBook;
import Library.Api.exception.ResourceNotFoundException;
import Library.Api.repository.BookListRepository;
import Library.Api.repository.IssuedBookRepository;
import Library.Api.repository.MembersListRepository;
import Library.Api.service.IssuedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class IssuedBookServiceImpl implements IssuedBookService {

    @Autowired
    private IssuedBookRepository issuedBookRepository;

    @Autowired
    private BookListRepository bookListRepository;

    @Autowired
    private MembersListRepository membersListRepository;

    private int calculateExpectedPrice(Date issuedDate, Date returnedDate) {
        long diffInMillies = Math.abs(returnedDate.getTime() - issuedDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return (int) diff * 10;
    }

    @Override
    public IssuedBook createIssuedBook(IssuedBook issuedBook) {
        Book_list book = bookListRepository.findById(issuedBook.getBook_list().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Book_list", "id", issuedBook.getBook_list().getId()));

        if (book.getBookCount() <= 0) {
            throw new RuntimeException("Book is out of stock");
        }

        // Decrement book count and update status if needed
        book.setBookCount(book.getBookCount() - 1);
        if (book.getBookCount() == 0) {
            book.setBookStatus(false);
        }
        bookListRepository.save(book);

        issuedBook.setIssuedDate(new Date());
        issuedBook.setExpectedPrice(calculateExpectedPrice(issuedBook.getIssuedDate(), issuedBook.getReturnedDate()));

        try {
            return issuedBookRepository.save(issuedBook);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("Error creating issued book: " + ex.getMessage());
        }
    }

    @Override
    public List<IssuedBook> getAllIssuedBooks() {
        return issuedBookRepository.findAll();
    }

    @Override
    public Optional<IssuedBook> getIssuedBookById(Integer id) {
        Optional<IssuedBook> issuedBook = issuedBookRepository.findById(id);
        if (!issuedBook.isPresent()) {
            throw new ResourceNotFoundException("IssuedBook", "id", id);
        }
        return issuedBook;
    }

    @Override
    public IssuedBook updateIssuedBook(Integer id, IssuedBook issuedBookDetails) {
        IssuedBook issuedBook = issuedBookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("IssuedBook", "id", id));
        issuedBook.setBook_list(issuedBookDetails.getBook_list());
        issuedBook.setMembersList(issuedBookDetails.getMembersList());
        issuedBook.setIssuedDate(issuedBookDetails.getIssuedDate());
        issuedBook.setReturnedDate(issuedBookDetails.getReturnedDate());
        issuedBook.setExpectedPrice(calculateExpectedPrice(issuedBookDetails.getIssuedDate(), issuedBookDetails.getReturnedDate()));
        return issuedBookRepository.save(issuedBook);
    }

    @Override
    public void deleteIssuedBook(Integer id) {
        IssuedBook issuedBook = issuedBookRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("IssuedBook", "id", id));
        issuedBookRepository.delete(issuedBook);
    }

    @Override
    public Page<IssuedBook> getIssuedBooks(Pageable pageable) {
        return issuedBookRepository.findAll(pageable);
    }
}
