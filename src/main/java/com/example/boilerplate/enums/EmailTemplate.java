package com.example.boilerplate.enums;

import lombok.Getter;

import java.util.Map;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum EmailTemplate {
    EMAIL_VERIFICATION("email-verification.ftl", "Verify your email"),
    GREETING("greeting.ftl", "Welcome");

    private String templateName;
    private String mailSubject;

    EmailTemplate(String templateName, String mailSubject) {
        this.templateName = templateName;
        this.mailSubject = mailSubject;
    }

    private static final Map<String, EmailTemplate> EMAIL_TEMPLATE_MAP = Arrays.stream(EmailTemplate.values())
            .collect(Collectors.toMap(EmailTemplate::getTemplateName, Function.identity()));

    public static EmailTemplate get(String templateName) {
        return EMAIL_TEMPLATE_MAP.get(templateName);
    }
}
