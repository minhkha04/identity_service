package com.minhkha.backend.repository;

import com.minhkha.backend.entity.MailOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailOtpRepository extends JpaRepository<MailOtp, String> {
}
