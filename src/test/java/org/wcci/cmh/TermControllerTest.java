package org.wcci.cmh;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TermControllerTest {

	@Resource
	private TermController underTest;
	
	@Test
	public void shouldFindAllTerms() {
		
//		underTest.displayEntireListOfTerms(name, model);
		
	}

}