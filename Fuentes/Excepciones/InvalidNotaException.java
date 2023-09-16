package Excepciones;

@SuppressWarnings("serial")
public class InvalidNotaException extends Exception{
	public InvalidNotaException(String msg) {
		super(msg);
	}
}
