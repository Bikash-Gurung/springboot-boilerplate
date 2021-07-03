package com.example.boilerplate.mail;

import com.example.boilerplate.entity.User;
import com.example.boilerplate.enums.EmailTemplate;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailService {
    final Configuration configuration;
    final JavaMailSender javaMailSender;

    public MailService(Configuration configuration, JavaMailSender javaMailSender) {
        this.configuration = configuration;
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(User user, EmailTemplate emailTemplate) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject(emailTemplate.getMailSubject());
        helper.setTo(user.getEmail());
        String emailContent = getEmailContent(user, emailTemplate);
        helper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);
    }

    String getEmailContent(User user, EmailTemplate emailTemplate) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("USER_NAME", user.getName());
        model.put("VERIFICATION_CODE", user.getVerificationCode());
        configuration.getTemplate(emailTemplate.getTemplateName()).process(model, stringWriter);

        return stringWriter.getBuffer().toString();
    }
}
