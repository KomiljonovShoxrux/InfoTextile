package org.example.info_textile.repository;

import org.example.info_textile.model.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactMessageRepo extends JpaRepository<ContactMessage, Long> {
}
