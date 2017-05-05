package com.pycredit.invoke;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import com.pycredit.pojo.bean.User;

public class InvokerTest {
	@Test
	public void beanWrapperTest() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		User usr=new User();
		usr.setId("23");
		//usr.setUserName("gj");
		BeanWrapper wrapper=PropertyAccessorFactory.forBeanPropertyAccess(usr);
		PropertyDescriptor[] pds=wrapper.getPropertyDescriptors();
		for(PropertyDescriptor pd:pds) {
			if(pd.getName().equals("class"))
				continue;
			Method m=pd.getReadMethod();
			Object value=m.invoke(usr);
			System.out.println(pd.getName()+":"+value);
		}
	}
	@Test
	public void test() throws IllegalArgumentException, IllegalAccessException {
		User usr=new User();
		usr.setId("23");
		usr.setUserName("gj");
		java.lang.reflect.Field[] flds = usr.getClass().getFields();  
	    if ( flds != null )  
	    {  
	        for ( int i = 0; i < flds.length; i++ )  
	        {  
	            System.out.println(flds[i].getName() + " - " + flds[i].get(usr));  
	        }  
	    }  
	}

}
