package main.xml;

/**
 * XMLException encapsulates an exception from the XML package.
 * 
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
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
