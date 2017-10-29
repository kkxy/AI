package exception;


public class DivZeroException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public DivZeroException() {
		// TODO Auto-generated constructor stub
	}
	
	public DivZeroException(String message) {
		super(message);
	}
	
	public DivZeroException(Throwable cause) {
        super(cause);
    }

}
