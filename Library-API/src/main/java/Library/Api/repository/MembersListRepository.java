package Library.Api.repository;



import Library.Api.entity.MembersList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembersListRepository extends JpaRepository<MembersList, Integer> {
    List<MembersList> findByNameContainingIgnoreCase(String name);
}
