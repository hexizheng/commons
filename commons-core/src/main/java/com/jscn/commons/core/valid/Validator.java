package com.jscn.commons.core.valid;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.jscn.commons.core.annotation.NotBlank;
import com.jscn.commons.core.annotation.Valid;
import com.jscn.commons.core.annotation.ValueLimit;
import com.jscn.commons.core.utils.StringUtils;

/**
 * 表单校验器
 * 
 * @author 贺夕政
 * 
 */
public class Validator {

	private Validator(){}	//阻止被实例化
	/**
	 * 校验器 
	 * 
	 * @param obj 需要校验的bean
	 * @return 如果全部通过返回null，否则返回注解中的message
	 */
	public static String valid(Object obj) {
		try {
			Field[] fields = obj.getClass().getDeclaredFields();// 获得属性
			for (Field field : fields) {
				String ret = validField(field,obj);
				if(ret!=null){
					return ret;
				}
				
			}
			
			Method[] methods = obj.getClass().getDeclaredMethods();
			for (Method method : methods) {
				String ret = validMethod(method,obj);
				if(ret!=null){
					return ret;
				}
				
			}
		} catch (Exception e) {
			return "未知错误";
		}
		return null;
	}
	
	
	private static String validField(Field field,Object obj) throws Exception{
		NotBlank notBlank = field.getAnnotation(NotBlank.class);
		ValueLimit limit = field.getAnnotation(ValueLimit.class);
		PropertyDescriptor pd = new PropertyDescriptor(field.getName(),obj.getClass());
		Method getMethod = pd.getReadMethod();// 获得get方法
		String value = (String) getMethod.invoke(obj);
		
		if(notBlank != null) {
			if(StringUtils.isBlank(value)){
				return notBlank.message();
			}
		}
		
		if(limit!=null){
			long min = limit.min();
			long max = limit.max();
			try{
				long val = Long.parseLong(value);
				if(val<min||val>max){
					return limit.message();
				}
			}catch(Exception e){
				return "输入格式错误，只能是数字";
			}
			
		}
		
		return null;
	}
	
	private static String validMethod(Method method,Object obj)throws Exception{
		Valid valid = method.getAnnotation(Valid.class);
		if(valid!=null){
			return (String)method.invoke(obj);
		}
		return null;
	}
}
