package exceptions;

/**
 * 
 * De klasse voor het verwerken van een SpelMaken exception
 * 
 * @author Gilles De Pessemier, Rune De Bruyne, Aaron Everaert, Chiel Meneve
 *
 */
public class SpelMakenException extends  RuntimeException{
	public SpelMakenException()
    {
    }
    
    public SpelMakenException(String message)
    {
        super(message);
    }

    public SpelMakenException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public SpelMakenException(Throwable cause)
    {
        super(cause);
    }

    public SpelMakenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
