package cn.michael.travel.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailUtil {
    @Value("${mail.hostName}")
    private String hostName;
    @Value("${mail.authen_name}")
    private String authen_name;
    @Value("${mail.authen_pwd}")
    private String authen_pwd;
    @Value("${mail.charset}")
    private String charset;

    public void sendEmail(String emailTo, String emailMsg) throws EmailException {
        HtmlEmail htmlEmail = new HtmlEmail();
        htmlEmail.setHostName(hostName);
        htmlEmail.setAuthentication(authen_name, authen_pwd);
        htmlEmail.setFrom(authen_name);
        htmlEmail.setCharset(charset);
        htmlEmail.addTo(emailTo);
        htmlEmail.setSubject("【黑马旅游注册激活邮件】");
        htmlEmail.setMsg(emailMsg);
        htmlEmail.send();
    }
}