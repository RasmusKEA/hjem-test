package com.example.demo.model;

public class Mail {
    private String name, address, phone, mail, subject, text;



    public Mail(String name, String address, String phone, String mail, String text, String subject) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.mail = mail;
        this.subject = subject;
        this.text = text;
    }

    public Mail() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSubject() { return subject; }

    public void setSubject(String subject) { this.subject = subject; }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "MailModel{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}

