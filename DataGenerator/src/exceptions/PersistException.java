package exceptions;

/**
 * @author M1026335
 * @author M1026352
 *
 *         Exception when an entity cannot be persisted in the database
 */
public class PersistException extends DAOException {

	private static final long serialVersionUID = 1L;

	public PersistException() {
		super();
	}

	public PersistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PersistException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersistException(String message) {
		super(message);
	}

	public PersistException(Throwable cause) {
		super(cause);
	}

}
