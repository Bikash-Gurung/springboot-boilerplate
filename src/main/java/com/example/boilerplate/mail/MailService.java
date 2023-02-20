package com.example.boilerplate.mail;

import com.example.boilerplate.entity.User;
import com.example.boilerplate.enums.EmailTemplate;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.io.StringWriter;

@Service
public class MailService {
    final Configuration configuration;
    final JavaMailSender javaMailSender;

    @Autowired
    private MessageSource messageSource;

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

    @Async
    public void sendEmailWithLocale(User user, EmailTemplate emailTemplate, Locale locale) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setTo(user.getEmail());
        String emailContent = getEmailContent(user, emailTemplate, locale);
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

    String getEmailContent(User user, EmailTemplate emailTemplate, Locale locale) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
//        model.put("LOCALE", locale);

        model.put("USER_NAME", user.getName());
        model.put("GREETING", messageSource.getMessage("greeting", new Object[]{}, locale));
        model.put("CONTENT", messageSource.getMessage("content", new Object[]{}, locale));
        model.put("CLOSING_TEXT", messageSource.getMessage("closingtext", new Object[]{}, locale));
        configuration.getTemplate(emailTemplate.getTemplateName()).process(model, stringWriter);

        return stringWriter.getBuffer().toString();
    }

    private String getUTF8EncodedString(String rawString){
        byte[] bytes = rawString.getBytes(StandardCharsets.UTF_8);

        return new String(bytes, StandardCharsets.UTF_8);
    }

    private StringBuilder getASCIIFromNative(String text){
            final CharsetEncoder asciiEncoder = Charset.forName("US-ASCII").newEncoder();
            final StringBuilder result = new StringBuilder();
            for (final Character character : text.toCharArray()) {
                if (asciiEncoder.canEncode(character)) {
                    result.append(character);
                } else {
                    result.append("\\u");
                    result.append(Integer.toHexString(0x10000 | character).substring(1).toUpperCase());
                }
            }

            return result;
    }
}
