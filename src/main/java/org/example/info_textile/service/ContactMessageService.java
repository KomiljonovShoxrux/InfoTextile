package org.example.info_textile.service;

import org.example.info_textile.model.ContactMessage;
import org.example.info_textile.repository.ContactMessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContactMessageService {


    @Autowired
    ContactMessageRepo contactMessageRepo;
    @Autowired
    EmailService emailService;


    public ContactMessage save(ContactMessage contact) {
        return contactMessageRepo.save(contact);
    }

    public List<ContactMessage> getAll() {
        return contactMessageRepo.findAll();
    }

    public ContactMessage reply(Long id, String reply) {
        ContactMessage contact = contactMessageRepo.findById(id).orElseThrow();
        contact.setAdminReply(reply);
        contact.setRepliedDate(LocalDateTime.now());
        contactMessageRepo.save(contact);

        // Email yuborish
        emailService.sendEmail(contact.getEmail(), "Javobingiz", reply);
        return contact;
    }
}
