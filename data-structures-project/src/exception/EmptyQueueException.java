package exception;

public class EmptyQueueException extends RuntimeException {
	public EmptyQueueException(String error) {
		super(error);
	}
}
