package exceptions;

import org.apache.commons.mail.EmailException;

public class SendEmailException extends Exception {
    public SendEmailException(EmailException e) {
        super(e);
    }
}
