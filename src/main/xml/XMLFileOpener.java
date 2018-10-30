/**
 * 
 */
package main.xml;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * This class allows to open an XML file and get its content as a File object.
 * 
 * @author Pomeg
 *
 */
public class XMLFileOpener extends FileFilter {

	private static XMLFileOpener instance = null; // Singleton

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
		jFileChooserXML.setCurrentDirectory(new File(".")); // set current dir
															// to project dir
		jFileChooserXML.setFileFilter(this);
		jFileChooserXML.setFileSelectionMode(JFileChooser.FILES_ONLY);

		if (jFileChooserXML.showOpenDialog(null) != JFileChooser.APPROVE_OPTION)
			throw new XMLException("Problem when opening file");

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
		return extension.contentEquals("xml");
	}

	@Override
	public String getDescription() {
		return "XML File";
	}

	/**
	 * Get the extension of a file.
	 * 
	 * @param f
	 *            is the file of which we want to the get the extension.
	 * @return String, a string representing the file's extension.
	 */
	private String getExtension(File f) {
		String fileName = f.getName();
		int i = fileName.lastIndexOf('.');
		if (i > 0 && i < fileName.length() - 1)
			return fileName.substring(i + 1).toLowerCase();
		return null;
	}
}
