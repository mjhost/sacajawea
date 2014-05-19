package org.mjhost.sacajawea.survivalkit;

import org.junit.Test;
import org.mjhost.sacajawea.exceptions.InitializationException;

public class MetadataReaderTest {

	@Test(expected=InitializationException.class)
	public void shouldThrowExceptionWhenMetadataStringIsNull() throws Exception {
		new MetadataReader(null);
	}
	
	@Test(expected=InitializationException.class)
	public void shouldThrowExceptionWhenMetadataStringIsEmpty() throws Exception {
		new MetadataReader("");
	}
	
	@Test
	public void shouldReadFileUrls() throws Exception {
		 new MetadataReader(this.getClass().getClassLoader().getResource("org/mjhost/sacajawea/location/ServicesSample.xml").toExternalForm());
	}

}
