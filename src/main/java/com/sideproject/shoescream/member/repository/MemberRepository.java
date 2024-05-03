package com.sideproject.shoescream.member.repository;

import com.sideproject.shoescream.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberId(String memberId);

    Optional<Member> findByEmail(String email);

    boolean existsByMemberId(String memberId);

    boolean existsByEmail(String email);
}
