package exceptions;

/**
 * 
 * De klasse voor het verwerken van een VerplichtVeld exception
 * 
 * @author Gilles De Pessemier, Rune De Bruyne, Aaron Everaert, Chiel Meneve
 *
 */
public class VerplichtVeldException extends RuntimeException{
	public VerplichtVeldException()
    {
    }
    
    public VerplichtVeldException(String message)
    {
        super(message);
    }

    public VerplichtVeldException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public VerplichtVeldException(Throwable cause)
    {
        super(cause);
    }

    public VerplichtVeldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
