package in.nareshit.raghu.exception;

public class ApplicationError extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ApplicationError() {
		super();
	}

	public ApplicationError(String message) {
		super(message);
	}

}
