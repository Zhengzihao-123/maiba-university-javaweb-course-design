package cn.maiba;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyUtil { 
	
	/*
	 * 转换setter,getter
	 * for example:
	 * 		User类有一个数据成员为account，那么访问该数据成员的getter,
	 * 		那么getGetter("account")得到该数据成员的getter为getAccount
	 */
	public static String getGetter(String DataMember){
		return DataMember = "get" +
							DataMember.substring(0, 1).toUpperCase() +
							DataMember.substring(1,DataMember.length());
	}
	
	public static String getSetter(String DataMember){
		return DataMember = "set" +
							DataMember.substring(0, 1).toUpperCase() +
							DataMember.substring(1,DataMember.length());
	}
	
	public static Object excute(Object classObject, Method myMethod, Object[] parameters) throws Exception {
		String errorMsg; 
		
		try 
		{ 
			//调用方法 
			return myMethod.invoke(classObject, parameters); 
		} 
		catch(InvocationTargetException invokeE) 
		{ 
			//捕获调用方法时抛出的异常 
			errorMsg = invokeE.getTargetException().getMessage(); 
		} 
		
		throw new Exception("通过Java反射机制执行方法时出错:"+errorMsg); 
	}
}
