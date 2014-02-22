package org.mjhost.sacajawea;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class JaneyTest {

	@Test
	public void janeyHasAnExploreMethod() {
		String metadata = "metadata.xml";
		String infrastructure = "infrastructure.xml";
		Janey.explore(infrastructure, metadata);
		assertThat(true, equalTo(true));
	}

}
