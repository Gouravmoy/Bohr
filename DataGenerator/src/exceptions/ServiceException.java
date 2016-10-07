package exceptions;

/**
 * @author M1026335
 * @author M1026352
 *
 *         Thrown at the Service level
 */
public class ServiceException extends ApplicationException {
	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

}
