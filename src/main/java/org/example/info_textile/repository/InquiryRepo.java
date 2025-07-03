package org.example.info_textile.repository;

import org.example.info_textile.model.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepo extends JpaRepository<Inquiry, Long> {
}
