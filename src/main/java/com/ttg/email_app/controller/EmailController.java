package com.ttg.email_app.controller;

import com.ttg.email_app.model.EmailCompose;
import com.ttg.email_app.service.EmailService;
import com.microsoft.graph.models.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class EmailController {
    
    private final EmailService emailService;
    
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }
    
    @GetMapping({"", "/", "/compose"})
    public String showCompose(Model model) {
        model.addAttribute("email", new EmailCompose());
        return "compose";
    }
    
    @GetMapping("/inbox")
    public String showInbox(@RequestParam(value = "top", defaultValue = "50") int top,
                           @RequestParam(value = "filter", required = false, defaultValue = "") String filter,
                           Model model) {
        List<Message> emails = emailService.getEmails(top, filter);
        model.addAttribute("emails", emails);
        model.addAttribute("filter", filter);
        model.addAttribute("top", top);
        return "inbox";
    }

    @PostMapping("/send")
    public String sendEmail(EmailCompose email) {
        emailService.sendEmail(email);
        return "redirect:/inbox";
    }

    @PostMapping("/saveDraft")
    public String saveDraft(EmailCompose email) {
        emailService.saveDraft(email);
        return "redirect:/compose";
    }
}
