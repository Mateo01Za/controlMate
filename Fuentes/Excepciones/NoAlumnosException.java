package Excepciones;

@SuppressWarnings("serial")
public class NoAlumnosException extends Exception{
	public NoAlumnosException(String msg) {
		super(msg);
	}
}