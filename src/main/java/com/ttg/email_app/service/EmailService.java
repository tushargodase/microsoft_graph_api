package com.ttg.email_app.service;

import com.microsoft.graph.models.*;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import com.microsoft.graph.users.item.sendmail.SendMailPostRequestBody;
import com.ttg.email_app.model.EmailCompose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.List;

@Service
public class EmailService {

    @Value("${user_email}")
    public String used_email;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private GraphServiceClient graphServiceClient;
    
    public void sendEmail(EmailCompose email) {
        try {
            // Get access token
            String accessToken = tokenService.getAccessToken();

            System.out.println("=== Email Service - Sending Email ===");

            // TODO: Implement actual email sending using Microsoft Graph API
            // This will be implemented in Stage 3

            Message message = new Message();
            message.setSubject(email.getSubject());
            message.setImportance(Importance.Low);

            ItemBody body = new ItemBody();
            body.setContentType(BodyType.Html);
            body.setContent(email.getBody());
            message.setBody(body);

            LinkedList<Recipient> toRecipients = new LinkedList<Recipient>();
            Recipient recipient = new Recipient();
            EmailAddress emailAddress = new EmailAddress();
            emailAddress.setAddress(email.getTo());
            recipient.setEmailAddress(emailAddress);
            toRecipients.add(recipient);
            message.setToRecipients(toRecipients);

            SendMailPostRequestBody sendMailRequestBody = new SendMailPostRequestBody();
            sendMailRequestBody.setMessage(message);

            graphServiceClient.users()
                    .byUserId(used_email)
                    .sendMail()
                    .post(sendMailRequestBody);

            System.out.println("=== Email Sent Successfully ===");

        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }
    
    public void saveDraft(EmailCompose email) {
        try {
            // Get access token
            String accessToken = tokenService.getAccessToken();
            System.out.println("=== Email Service - Saving Draft ===");
            System.out.println("Access Token: " + accessToken.substring(0, 20) + "... [truncated]");
            
            // TODO: Implement actual draft saving using Microsoft Graph API
            // This will be implemented in Stage 3
            
            System.out.println("To: " + email.getTo());
            System.out.println("Subject: " + email.getSubject());
            
            System.out.println("=== Draft Saved Successfully ===");
            
        } catch (Exception e) {
            System.err.println("Error saving draft: " + e.getMessage());
            throw new RuntimeException("Failed to save draft", e);
        }
    }

    public List<Message> getEmails(int top, String filter) {
        try {

            return Objects.requireNonNull(graphServiceClient.users()
                            .byUserId(used_email)
                            .mailFolders()
                            .byMailFolderId("inbox")
                            .messages()
                            .get())
                    .getValue();
        } catch (Exception e) {
            System.err.println("Error fetching emails: " + e.getMessage());
            throw new RuntimeException("Failed to fetch emails", e);
        }
    }


}
