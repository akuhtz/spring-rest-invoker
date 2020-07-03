package com.github.ggeorgovassilis.springjsonmapper.integrationtests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.ggeorgovassilis.springjsonmapper.services.BookService;
import com.github.ggeorgovassilis.springjsonmapper.services.Item;
import com.github.ggeorgovassilis.springjsonmapper.services.QueryResult;
import com.github.ggeorgovassilis.springjsonmapper.services.VolumeInfo;
import com.github.ggeorgovassilis.springjsonmapper.spring.SpringRestInvokerProxyFactoryBean;

/**
 * Integration test with the google books API using the
 * {@link SpringRestInvokerProxyFactoryBean}
 * 
 * @author george georgovassilis
 */
@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public abstract class AbstractGoogleBooksApiTest {

	@Autowired
	protected BookService bookService;
	
	private static final String BOOK_TITLE = "Philosophiae naturalis principia mathematica";
	private static final String BOOK_TITLE_RESULT = "Philosophiae Naturalis Principia Mathematica";

	@Test
	public void testFindBooksByTitle() throws Exception {

		QueryResult result = bookService.findBooksByTitle("\""+BOOK_TITLE+"\"");
		assertTrue(result.getItems().size() > 0);
		boolean found = false;
		for (Item item : result.getItems()) {
			VolumeInfo info = item.getVolumeInfo();

			found |= info != null && info.getAuthors() != null && !info.getAuthors().isEmpty()
					&& (BOOK_TITLE_RESULT.equals(info.getTitle())
							&& "Sir Isaac Newton".equals(info.getAuthors().get(0)));
		}
		assertTrue(found);
	}

	@Test
	public void testFindBooksById() {
		Item item = bookService.findBookById("3h9_GY8v-hgC");
		VolumeInfo info = item.getVolumeInfo();
		assertEquals(BOOK_TITLE, info.getTitle());
		assertEquals("Isaac Newton", info.getAuthors().get(0));
	}

}
