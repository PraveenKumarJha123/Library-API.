package Library.Api.service.Impl;



import Library.Api.entity.MembersList;
import Library.Api.exception.ResourceNotFoundException;

import Library.Api.repository.MembersListRepository;
import Library.Api.service.MembersListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MembersListServiceImpl implements MembersListService {

    @Autowired
    private MembersListRepository membersListRepository;

    @Override
    public MembersList createMember(MembersList member) {
        try {
            return membersListRepository.save(member);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("Error creating member: " + ex.getMessage());
        }
    }

    @Override
    public List<MembersList> getAllMembers() {
        return membersListRepository.findAll();
    }

    @Override
    public Optional<MembersList> getMemberById(Integer id) {
        Optional<MembersList> member = membersListRepository.findById(id);
        if (!member.isPresent()) {
            throw new ResourceNotFoundException("Member", "id", id);
        }
        return member;
    }

    @Override
    public MembersList updateMember(Integer id, MembersList memberDetails) {
        MembersList member = membersListRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Member", "id", id));
        member.setName(memberDetails.getName());
        member.setAge(memberDetails.getAge());
        member.setEmail(memberDetails.getEmail());
        member.setPhoneNo(memberDetails.getPhoneNo());
        member.setAddress(memberDetails.getAddress());
        return membersListRepository.save(member);
    }

    @Override
    public void deleteMember(Integer id) {
        MembersList member = membersListRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Member", "id", id));
        membersListRepository.delete(member);
    }

    @Override
    public List<MembersList> bulkAddMembers(List<MembersList> members) {
        try {
            return membersListRepository.saveAll(members);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("Error adding members in bulk: " + ex.getMessage());
        }
    }

    @Override
    public Page<MembersList> getMembers(Pageable pageable) {
        return membersListRepository.findAll(pageable);
    }

    @Override
    public List<MembersList> searchMembersByName(String name) {
        return membersListRepository.findByNameContainingIgnoreCase(name);
    }
}
