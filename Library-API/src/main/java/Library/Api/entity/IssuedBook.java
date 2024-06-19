package Library.Api.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "issued_book")
public class IssuedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_list_id")
    private Book_list book_list;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "members_list_id")
    private MembersList membersList;

    @Temporal(TemporalType.DATE)
    @Column(name = "issued_date", nullable = false)
    private Date issuedDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "returned_date", nullable = false)
    private Date returnedDate;

    @Column(name = "expected_price", nullable = false)
    private Integer expectedPrice;

    @OneToMany(mappedBy = "issuedBook", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ReturnedBook> returnedBooks;
}
