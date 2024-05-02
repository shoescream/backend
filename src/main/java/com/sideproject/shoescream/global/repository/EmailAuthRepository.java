package com.sideproject.shoescream.global.repository;

import com.sideproject.shoescream.global.entity.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long>, EmailAuthCustomRepository{

}
