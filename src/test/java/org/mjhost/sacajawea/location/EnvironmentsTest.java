package org.mjhost.sacajawea.location;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mjhost.sacajawea.exceptions.InitializationException;
import org.mjhost.sacajawea.location.Environments.Environment;
import org.mjhost.sacajawea.survivalkit.MetadataReader;

public class EnvironmentsTest {

	private static final String SAMPLE_ENVIRONMENT_LOCATION = "org/mjhost/sacajawea/location/EnvironmentSample.xml";
	Environments environments;
	
	@Before
	public void setUp() throws Exception{
		environments = new Environments();
		environments.initialize(new MetadataReader(SAMPLE_ENVIRONMENT_LOCATION).read());
	}
	
	@Test
	public void sampleEnvironmentShouldBeReadAndUnderstood() {
		Environment env = environments.get("it");
		assertThat(env.getDescription(), equalTo("for all services served in italian language"));
	}
	
	@Test(expected=InitializationException.class)
	public void shouldntInitializeTwice() throws Exception {
		environments.initialize(new MetadataReader(SAMPLE_ENVIRONMENT_LOCATION).read());
	}
	
	@Test(expected=InitializationException.class)
	public void shouldntInitializeWhenNullDoc() throws Exception {
		Environments envs = new Environments();
		envs.initialize(null);
	}
	
	@Test
	public void shouldReturnUnknownWhenNotDefined() throws Exception {
		Environment env = environments.get("de");
		assertThat(env, notNullValue());
		assertThat(env.getName(), equalTo("unknown"));
		assertThat(env.getDescription(), equalTo(""));
	}

}
