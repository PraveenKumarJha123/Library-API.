package Library.Api.service;



import Library.Api.entity.MembersList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MembersListService {
    MembersList createMember(MembersList member);
    List<MembersList> getAllMembers();
    Optional<MembersList> getMemberById(Integer id);
    MembersList updateMember(Integer id, MembersList memberDetails);
    void deleteMember(Integer id);
    List<MembersList> bulkAddMembers(List<MembersList> members);
    Page<MembersList> getMembers(Pageable pageable);
    List<MembersList> searchMembersByName(String name);
}
