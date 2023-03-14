package com.example.proyectoapiv1.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Service
public class EnviarEmailService {
    //investigar como estilizar el mail

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine thymeleafTemplateEngine;

    @Value("classpath:/img/logo.png")
    Resource logo;

    public void enviarMail(String to, String subject, String body){
        SimpleMailMessage email = new SimpleMailMessage();

        email.setFrom("digitalbooking.grupo6@gmail.com");
        email.setTo(to);
        email.setSubject(subject);
        email.setText(body);

        javaMailSender.send(email);

    }

    private void enviarMailHtml(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        helper.addInline("logo.png", logo);

        javaMailSender.send(message);
    }


    public void enviarTemplateRegistro(
            String to, String subject, Map<String, Object> templateModel)
            throws MessagingException {

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("mailRegistro.html", thymeleafContext);

        enviarMailHtml(to, subject, htmlBody);
    }

    public void enviarTemplateReserva(
            String to, String subject, Map<String, Object> templateModel)
            throws MessagingException {

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("mailReserva.html", thymeleafContext);

        enviarMailHtml(to, subject, htmlBody);
    }




}
