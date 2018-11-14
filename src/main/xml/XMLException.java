package main.xml;

/**
 * XMLException encapsulates an exception from the XML package.
 * 
 */
public class XMLException extends Exception {

    /**
     * Serial version ID
     */
    private static final long serialVersionUID = -6846244456959586088L;

    /**
     * Build an XML exception to throw.
     * 
     * @param message is the message of the exception.
     */
    public XMLException(String message) {
	super(message);
    }

}
