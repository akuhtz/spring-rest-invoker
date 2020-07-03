package com.github.ggeorgovassilis.springjsonmapper.spring;

import static org.mockito.Mockito.mock;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringValueResolver;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.ggeorgovassilis.springjsonmapper.model.MappingDeclarationException;

/**
 * Tests a few code paths in {@link SpringAnnotationMethodInspector} that are
 * not covered by other tests.
 * 
 * @author george georgovassilis
 *
 */
public class TestSpringAnnotationMethodInspector {

	@RequestMapping
	public void someMethod1() {
	}

	@RequestMapping("/someMethod")
	public void someMethod2(String p1, @RequestParam int p2, String p3) {
	}

	@Test //(expected = MappingDeclarationException.class)
	public void testEmptyRequestMapping() throws Exception {
		SpringAnnotationMethodInspector inspector = new SpringAnnotationMethodInspector();
		Method someMethod1 = getClass().getMethod("someMethod1");
		Assertions.assertThrows(MappingDeclarationException.class, 
				() -> inspector.inspect(someMethod1, null));
	}

	@Test //(expected = MappingDeclarationException.class)
	public void testMissingParameters() throws Exception {
		SpringAnnotationMethodInspector inspector = new SpringAnnotationMethodInspector();
		StringValueResolver resolver = mock(StringValueResolver.class);
		inspector.setEmbeddedValueResolver(resolver);

		Method someMethod2 = getClass().getMethod("someMethod2", String.class, int.class, String.class);
		Assertions.assertThrows(MappingDeclarationException.class, 
				() -> inspector.inspect(someMethod2, new Object[] { "a", new Integer(2), "c" }));
	}
}
