package exceptions;

/**
 * @author M1026335
 * @author M1026352
 * 
 *         DAO level exception wrapping
 *
 */
public class DAOException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public DAOException() {
		super();
	}

	public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(String message) {
		super(message);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}

}
