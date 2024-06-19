package Library.Api.controller;





import Library.Api.entity.MembersList;
import Library.Api.service.MembersListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/members")
public class MembersListController {

    @Autowired
    private MembersListService membersListService;

    @PostMapping
    public ResponseEntity<MembersList> createMember(@RequestBody MembersList member) {
        MembersList createdMember = membersListService.createMember(member);
        return ResponseEntity.ok(createdMember);
    }

    @GetMapping
    public ResponseEntity<List<MembersList>> getAllMembers() {
        List<MembersList> members = membersListService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembersList> getMemberById(@PathVariable Integer id) {
        Optional<MembersList> member = membersListService.getMemberById(id);
        return member.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MembersList> updateMember(@PathVariable Integer id, @RequestBody MembersList memberDetails) {
        MembersList updatedMember = membersListService.updateMember(id, memberDetails);
        return ResponseEntity.ok(updatedMember);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Integer id) {
        membersListService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<MembersList>> bulkAddMembers(@RequestBody List<MembersList> members) {
        List<MembersList> addedMembers = membersListService.bulkAddMembers(members);
        return ResponseEntity.ok(addedMembers);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<MembersList>> getMembersPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MembersList> membersPage = membersListService.getMembers(pageable);
        return ResponseEntity.ok(membersPage);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MembersList>> searchMembersByName(@RequestParam String name) {
        List<MembersList> members = membersListService.searchMembersByName(name);
        return ResponseEntity.ok(members);
    }
}
