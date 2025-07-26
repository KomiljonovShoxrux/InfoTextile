package org.example.info_textile.controller;

import org.example.info_textile.model.Inquiry;
import org.example.info_textile.repository.InquiryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class InquiryController {

    @Autowired
    InquiryRepo inquiryRepo;


    @PostMapping("/inquiry")
    public Inquiry saveInquiry(@RequestBody Inquiry inquiry) {
        return inquiryRepo.save(inquiry);
    }
    @GetMapping("/inquirys")
    public List<Inquiry> getAllInquirys() {
        return inquiryRepo.findAll();
    }




}
