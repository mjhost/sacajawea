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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author Paolo Mariotti
 *
 */
public class MetadataReader {

	private URL metadataUrl;
	private static Logger logger = LoggerFactory.getLogger(MetadataReader.class);
	
	public MetadataReader(String metadata){
		if(metadata != null && !metadata.isEmpty()){
			try{
				this.metadataUrl = new URL(metadata);
			}catch(MalformedURLException e){
				logger.debug("Failed to read metadata as url trying as resource file. Reason {}", e.getMessage());
				this.metadataUrl = this.getClass().getClassLoader().getResource(metadata);
			}
		}
		if(metadataUrl == null){
			throw new InitializationException(InitializationException.CANNOT_INITIALIZE);
		}
	}
	
	public Document read(){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document doc = null;
		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(metadataUrl.openStream());
			doc.normalizeDocument();
		} catch (ParserConfigurationException e) {
			logger.error("cannot read document", e);
		} catch (SAXException e) {
			logger.error("cannot read document", e);
		} catch (IOException e) {
			logger.error("cannot read document", e);
		}
		return doc;
	}
	
}
