package exceptions;

/**
 * @author M1026335
 * @author M1026352
 *
 *         Thrown when an entity is searched from the database and it is not
 *         found
 */
public class EntityNotPresent extends DAOException {

	private static final long serialVersionUID = 1L;

	public EntityNotPresent() {
		super();
	}

	public EntityNotPresent(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EntityNotPresent(String message, Throwable cause) {
		super(message, cause);
	}

	public EntityNotPresent(String message) {
		super(message);
	}

	public EntityNotPresent(Throwable cause) {
		super(cause);
	}

}
