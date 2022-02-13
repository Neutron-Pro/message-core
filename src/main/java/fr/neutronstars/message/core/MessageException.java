package fr.neutronstars.message.core;

public class MessageException extends RuntimeException
{
    public MessageException()
    {
        super();
    }

    public MessageException(String message)
    {
        super(message);
    }

    public MessageException(Throwable throwable)
    {
        super(throwable);
    }

    public MessageException(String message, Throwable throwable)
    {
        super(message, throwable);
    }

    public MessageException(
        String message,
        Throwable throwable,
        boolean fillStackTrace,
        boolean keepSuppressedException
    ) {
        super(message, throwable, fillStackTrace, keepSuppressedException);
    }
}
