package org.example.info_textile.controller;

import org.example.info_textile.model.Admin;
import org.example.info_textile.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/contact")
public class AdminController {

    @Autowired
    private AdminRepo contactRepository;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping
    public List<Admin> getAllMessages() {
        return contactRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
    }

    // Admin javobi
    @PostMapping("/reply/{id}")
    public ResponseEntity<?> replyToMessage(@PathVariable Long id, @RequestBody String replyText) {
        Optional<Admin> optional = contactRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Admin contact = optional.get();
        contact.setAdminReply(replyText);
        contactRepository.save(contact);

        // Email yuborish
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(contact.getEmail());
        mail.setSubject("Sizga javob");
        mail.setText("Salom " + contact.getName() + ",\n\n" + replyText);

        mailSender.send(mail);

        return ResponseEntity.ok("Javob yuborildi");
    }
}
