package com.github.ggeorgovassilis.springjsonmapper.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.ggeorgovassilis.springjsonmapper.BaseRestInvokerProxyFactoryBean;
import com.github.ggeorgovassilis.springjsonmapper.model.MappingDeclarationException;
import com.github.ggeorgovassilis.springjsonmapper.services.InterfaceWithErrors;

/**
 * Test that various error checks work
 * 
 * @author George Georgovassilis
 *
 */
@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public abstract class AbstractErrorChecksTest {

	@Autowired
	BaseRestInvokerProxyFactoryBean httpProxyFactory;

	@Autowired
	InterfaceWithErrors iwe;

	@Test //(expected = MappingDeclarationException.class)
	public void testAmibiguousRequestBody() {
		Assertions.assertThrows(MappingDeclarationException.class, 
				() -> iwe.methodWithTwoAnonymousRequestBodies(new byte[0], new byte[0]));
	}

	@Test //(expected = MappingDeclarationException.class)
	public void testNamedAndUnnamedRequestBody() {
		Assertions.assertThrows(MappingDeclarationException.class, 
				() -> iwe.methodWithNamedAndAnonymousRequestBodies(new byte[0], new byte[0]));
	}

	@Test //(expected = MappingDeclarationException.class)
	public void testIncompleteParameterAnnotations() {
		Assertions.assertThrows(MappingDeclarationException.class, 
				() -> iwe.methodWithIncompleteParameterAnnotations("s1", "s2"));
	}

	@Test //(expected = MappingDeclarationException.class)
	public void testDuplicateParameterAnnotations() {
		Assertions.assertThrows(MappingDeclarationException.class, 
				() -> iwe.methodWithDuplicateParameterAnnotations("s1", "s2"));
	}

	@Test //(expected = MappingDeclarationException.class)
	public void testAmbigiousRequestMethods() {
		Assertions.assertThrows(MappingDeclarationException.class, 
				() -> iwe.methodWithAmbiguousHttpMethod());
	}
}
