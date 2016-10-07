package exceptions;

/**
 * @author M1026335
 * @author M1026352
 * 
 *         Exception is thrown if an entity that is to be persisted in the
 *         database already exists
 *
 */
public class EntityAlreadyExists extends DAOException {

	private static final long serialVersionUID = 1L;

	public EntityAlreadyExists() {
		super();
	}

	public EntityAlreadyExists(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EntityAlreadyExists(String message, Throwable cause) {
		super(message, cause);
	}

	public EntityAlreadyExists(String message) {
		super(message);
	}

	public EntityAlreadyExists(Throwable cause) {
		super(cause);
	}

}
