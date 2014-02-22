/**
 * 
 */
package org.mjhost.sacajawea.survivalkit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.mjhost.sacajawea.exceptions.InitializationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author Paolo Mariotti
 *
 */
public class MetadataReader {

	private URL metadataUrl;
	
	public MetadataReader(String metadata) throws MalformedURLException {
		MalformedURLException caught = null;
		try{
			this.metadataUrl = new URL(metadata);
		}catch(MalformedURLException e){
			caught = e;
			this.metadataUrl = this.getClass().getClassLoader().getResource(metadata);
		}finally{
			if(this.metadataUrl == null){
				throw new InitializationException(InitializationException.CANNOT_INITIALIZE, caught);
			}
		}
	}
	
	public Document read() throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db =dbf.newDocumentBuilder();
		Document doc = db.parse(metadataUrl.openStream());
		doc.normalizeDocument();
		return doc;
	}
	
}
