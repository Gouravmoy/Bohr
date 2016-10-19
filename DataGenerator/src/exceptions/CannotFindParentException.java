package exceptions;

public class CannotFindParentException extends Exception {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return super.getMessage();
	}

	public CannotFindParentException() {
		super();
	}

	public CannotFindParentException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public CannotFindParentException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public CannotFindParentException(String arg0) {
		super(arg0);
	}

	public CannotFindParentException(Throwable arg0) {
		super(arg0);
	}

}
