package exceptions;

/**
 * 
 * De klasse voor het verwerken van een Account exception
 * 
 * @author Gilles De Pessemier, Rune De Bruyne, Aaron Everaert, Chiel Meneve
 *
 */
public class AccountException extends RuntimeException{
	public AccountException()
    {
    }
    
    public AccountException(String message)
    {
        super(message);
    }

    public AccountException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public AccountException(Throwable cause)
    {
        super(cause);
    }

    public AccountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
