package gr.aueb.cf.schoolspring.core.exceptions;

public class AppServerException extends EntityGenericException {
    public AppServerException(String code, String message) {
        super(code, message);
    }
}
