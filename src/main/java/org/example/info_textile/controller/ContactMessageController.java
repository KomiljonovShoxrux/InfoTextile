package org.example.info_textile.controller;

import org.example.info_textile.model.ContactMessage;
import org.example.info_textile.service.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/contactt")
public class ContactMessageController {

    @Autowired
    private ContactMessageService service;

    @PostMapping
    public ContactMessage sendMessage(@RequestBody ContactMessage contact) {
        return service.save(contact);
    }

    @GetMapping
    public List<ContactMessage> getAllMessages() {
        return service.getAll();
    }

    @PostMapping("/reply/{id}")
    public ContactMessage replyToMessage(@PathVariable Long id, @RequestBody String reply) {
        return service.reply(id, reply);
    }
}
