package com.pycredit.aapublicservice.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

import com.pycredit.publicservice.aop.service.PersonService;



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
