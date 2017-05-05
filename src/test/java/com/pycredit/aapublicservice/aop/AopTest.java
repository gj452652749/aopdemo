package com.pycredit.aapublicservice.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pycredit.publicservice.aop.service.PersonService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class AopTest {
private final static Log log = LogFactory.getLog(AopTest.class);
	
	public static void main( String[] args )
	{
		ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		PersonService personService = appContext.getBean(PersonService.class);
		String personName = "Jim";
		personService.addPerson(personName);
		personService.deletePerson(personName);
		personService.editPerson(personName);
		((ClassPathXmlApplicationContext)appContext).close();
	}
}
