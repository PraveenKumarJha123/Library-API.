package Library.Api.entity;




import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "returned_book")
public class ReturnedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "issued_book_id", nullable = false)
    @JsonBackReference
    private IssuedBook issuedBook;

    @Temporal(TemporalType.DATE)
    @Column(name = "actual_returned_date", nullable = false)
    private Date actualReturnedDate;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;
}
