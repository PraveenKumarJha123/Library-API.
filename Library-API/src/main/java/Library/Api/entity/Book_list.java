package Library.Api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "book_list")
public class Book_list {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "book_name", nullable = false, unique = true)
    private String bookName;

    @Column(name = "book_count", nullable = false)
    private Integer bookCount;

    @Column(name = "book_status", nullable = false)
    private Boolean bookStatus = false;

    @Column(name = "book_author_name", nullable = false)
    private String bookAuthorName;

}