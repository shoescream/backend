package com.sideproject.shoescream.member.repository;

import com.sideproject.shoescream.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
