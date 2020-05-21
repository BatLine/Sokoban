package exceptions;

/**
 * 
 * De klasse voor het verwerken van een LevelNietMeerMogelijkException exception
 * 
 * @author Gilles De Pessemier, Rune De Bruyne, Aaron Everaert, Chiel Meneve
 *
 */
public class LevelNietMeerMogelijkException extends RuntimeException {
	public LevelNietMeerMogelijkException()
    {
    }
    
    public LevelNietMeerMogelijkException(String message)
    {
        super(message);
    }

    public LevelNietMeerMogelijkException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public LevelNietMeerMogelijkException(Throwable cause)
    {
        super(cause);
    }

    public LevelNietMeerMogelijkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
