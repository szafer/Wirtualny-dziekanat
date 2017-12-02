package pl.edu.us.server.services.user;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;

@Stateless
public class Mail {
    public static final String EMAIL_SESSION_JNDI_PATH = "java:/jboss/mail/gmail";

    private Session getEmailSession() throws Exception {
        InitialContext context = new InitialContext();
        return (Session) context.lookup(EMAIL_SESSION_JNDI_PATH);
    }

    @Resource(name = "java:/jboss/mail/gmail")
    private Session session;

    public void send(String addresses, String topic, String textMessage) {
        if (session == null) {
            try {
                session = getEmailSession();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        try {

            Message message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(addresses));
            message.setSubject(topic);
            message.setText(textMessage);
        //    message.addFrom(InternetAddress.parse("admin@usosweb.us.edu.pl")); to nie przechodzi będzie dummy adress

            Transport.send(message);

        } catch (MessagingException e) {
            Logger.getLogger(Mail.class.getName()).log(Level.WARNING, "Cannot send mail", e);
        }
    }
}
