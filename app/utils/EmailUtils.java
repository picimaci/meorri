package utils;

import play.Logger;
import play.i18n.Messages;
import exceptions.SendEmailException;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class EmailUtils {
    public static void sendResetPasswordEmail(String recipient, String token) throws SendEmailException {
        try {
            String baseURL = AppConf.getConfigString(AppConf.BASE_URL);
            String relativeURL = controllers.login.routes.LoginCtrl.resetPassword(token).url();

            HtmlEmail email = new HtmlEmail();

            email.setSubject(Messages.get("email.lostpassword.subject"));
            email.setHtmlMsg(Messages.get("email.lostpassword.message", baseURL + relativeURL));
            email.setHostName(AppConf.getConfigString(AppConf.SMTP_HOST));
            email.setAuthentication(
                    AppConf.getConfigString(AppConf.SMTP_USERNAME),
                    AppConf.getConfigString(AppConf.SMTP_PASSWORD)
            );
            email.setStartTLSEnabled(Boolean.parseBoolean(AppConf.getConfigString(AppConf.SMTP_TLS)));
            email.setFrom(AppConf.getConfigString(AppConf.EMAIL_SENDER));
            email.addTo(recipient);
            email.send();
        } catch (EmailException e) {
            Logger.error("Error sending the e-mail", e);
            throw new SendEmailException(e);
        }
    }
}
