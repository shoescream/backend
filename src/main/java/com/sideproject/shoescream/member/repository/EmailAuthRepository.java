package com.sideproject.shoescream.member.repository;

import com.sideproject.shoescream.member.entity.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long>, EmailAuthCustomRepository {

}
