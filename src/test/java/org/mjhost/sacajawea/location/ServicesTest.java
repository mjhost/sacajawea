package org.mjhost.sacajawea.location;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;
import org.mjhost.sacajawea.exceptions.InitializationException;
import org.mjhost.sacajawea.location.Services.Service;
import org.mjhost.sacajawea.survivalkit.MetadataReader;
import org.w3c.dom.Document;

public class ServicesTest {
	
	private static final String SAMPLE_SERVICES_LOCATION = "org/mjhost/sacajawea/location/ServicesSample.xml";
	Services services;
	
	@Before
	public void setUp() throws Exception{
		services = new Services();
		services.initialize(new MetadataReader(SAMPLE_SERVICES_LOCATION).read());
	}
	
	
	@Test(expected=InitializationException.class)
	public void initializationShouldFailOnNullMetadata() {
		Services s = new Services();
		s.initialize(null);
	}
	
	@Test
	public void shouldInitializeWithSampleServicesXml() throws Exception {
		Services s = new Services();
		s.initialize(new MetadataReader(SAMPLE_SERVICES_LOCATION).read());
	}
	
	@Test(expected=InitializationException.class)
	public void shouldComplainWhenInitializingMoreThanOnce() throws Exception{
		services.initialize(new MetadataReader(SAMPLE_SERVICES_LOCATION).read());
	}
	
	@Test
	public void unknownServicesShouldBeDifferent() throws Exception{
		Service first = services.get("something");
		Service second = services.get("something");
		assertThat(first, not(equalTo(second)));
	}
	
	@Test
	public void thereShouldBeAvailableServices() throws Exception {
		assertThat(services.available.entrySet().size(), greaterThan(0));
		for(Entry<String, Service> entry: services.available.entrySet()){
			System.out.println(entry.getKey());
		}
		Document doc = new MetadataReader(SAMPLE_SERVICES_LOCATION).read();
		assertThat(services.available.entrySet().size(), equalTo(doc.getElementsByTagName("service").getLength()));
	}
	
	@Test
	public void hierarchyShouldBeComplete() throws Exception {
		Service googleImages = services.get("google-images");
		assertThat(googleImages.getParent(), equalTo(services.get("google")));
		assertThat(googleImages.getParent().getParent(), equalTo(services.get("search")));
		assertThat(googleImages.getParent().getParent().getParent(), equalTo(Services.ANY));
		
		assertThat(Services.ANY.getParent(), equalTo(Services.ANY));
	}
	
	
}
