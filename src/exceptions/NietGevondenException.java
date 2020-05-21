package exceptions;

/**
 * 
 * De klasse voor het verwerken van een NietGevonden exception
 * 
 * @author Gilles De Pessemier, Rune De Bruyne, Aaron Everaert, Chiel Meneve
 *
 */
public class NietGevondenException extends RuntimeException{
	public NietGevondenException()
    {
    }
    
    public NietGevondenException(String message)
    {
        super(message);
    }

    public NietGevondenException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public NietGevondenException(Throwable cause)
    {
        super(cause);
    }

    public NietGevondenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
