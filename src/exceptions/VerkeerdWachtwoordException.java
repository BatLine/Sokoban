package exceptions;

/**
 * 
 * De klasse voor het verwerken van een Wachtwoord exception
 * 
 * @author Gilles De Pessemier, Rune De Bruyne, Aaron Everaert, Chiel Meneve
 *
 */
public class VerkeerdWachtwoordException extends RuntimeException{
	public VerkeerdWachtwoordException()
    {
    }
    
    public VerkeerdWachtwoordException(String message)
    {
        super(message);
    }

    public VerkeerdWachtwoordException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public VerkeerdWachtwoordException(Throwable cause)
    {
        super(cause);
    }

    public VerkeerdWachtwoordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
