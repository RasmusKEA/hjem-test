package com.example.demo.controller;

import com.example.demo.model.Mail;
import com.example.demo.service.MailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

public class MailController {

    public String formPost(WebRequest wr){
        //Får informationen fra webrequesten
        String name = wr.getParameter("name");
        String address = wr.getParameter("addr");
        String phone = wr.getParameter("tlfnr");
        String email = wr.getParameter("email");
        String text = wr.getParameter("text");

        Mail mm = new Mail(name,address,phone,email,text);
        MailService mail = new MailService();
        mail.sendMail(mm.getMail());
        return "redirect:/contact";
    }
}
