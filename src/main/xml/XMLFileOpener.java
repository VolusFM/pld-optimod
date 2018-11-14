package main.xml;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * XMLFileOpener allows to open an XML file and get its content as a File
 * object.
 *
 */
public class XMLFileOpener extends FileFilter {

    private static final String RESOURCES_FOLDER = "./resources";
    private static final String XML_FILE = "XML File";
    private static final String XML = "xml";
    private static XMLFileOpener instance = null;

    private XMLFileOpener() {
    }

    /**
     * Get the unique instance of the class.
     * 
     * @return XMLFileOpener, the unique instance of the class.
     */
    public static XMLFileOpener getInstance() {
	if (instance == null)
	    instance = new XMLFileOpener();
	return instance;
    }

    /**
     * Open an XML file to get its content.
     * 
     * @return File, a File representing the chosen XML file.
     * @throws XMLException
     */
    public File open() throws XMLException {
	JFileChooser jFileChooserXML = new JFileChooser();

	// If there's a resources folder, use it as default location
	File resources = new File(RESOURCES_FOLDER);
	if (resources.exists() && resources.isDirectory()) {
	    jFileChooserXML.setCurrentDirectory(resources);
	}
	jFileChooserXML.setFileFilter(this);
	jFileChooserXML.setFileSelectionMode(JFileChooser.FILES_ONLY);

	if (jFileChooserXML.showOpenDialog(null) != JFileChooser.APPROVE_OPTION)
	    throw new XMLException("Un problème est survenu - avez vous bien choisi un fichier ?");

	return new File(jFileChooserXML.getSelectedFile().getAbsolutePath());
    }

    @Override
    public boolean accept(File f) {
	if (f == null)
	    return false;
	if (f.isDirectory())
	    return true;
	String extension = getExtension(f);
	if (extension == null)
	    return false;
	return extension.contentEquals(XML);
    }

    @Override
    public String getDescription() {
	return XML_FILE;
    }

    /**
     * Get the extension of a file.
     * 
     * @param f is the file of which we want to the get the extension.
     * @return String, a string representing the file's extension.
     */
    private static String getExtension(File f) {
	String fileName = f.getName();
	int i = fileName.lastIndexOf('.');
	if (i > 0 && i < fileName.length() - 1)
	    return fileName.substring(i + 1).toLowerCase();
	return null;
    }
}
