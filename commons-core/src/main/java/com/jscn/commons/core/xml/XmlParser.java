package com.jscn.commons.core.xml;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jscn.commons.core.annotation.ElementName;
import com.jscn.commons.core.exceptions.SystemException;
import com.jscn.commons.core.utils.StringUtils;

/**
 * 
 * @author 贺夕政  Dom4j 解析XML文档
 */
public class XmlParser{

	private Document doc;
	
    private static final Logger logger = LoggerFactory.getLogger(XmlParser.class);

	public XmlParser(String xmlContent) {
		try{
			StringReader stringReader = new StringReader(xmlContent);
			SAXReader reader = new SAXReader();
			doc = reader.read(stringReader);
		}catch(Exception e){
			throw new SystemException("初始化xmlParser出错 xmlContent="+xmlContent,e);
		}	
	}
	
	public XmlParser(File file) {
		try{
			FileReader fileReader = new FileReader(file);
			SAXReader reader = new SAXReader();
			doc = reader.read(fileReader);
		}catch(Exception e){
			throw new SystemException("初始化xmlParser出错 file="+file.getName(),e);
		}
	}
	public XmlParser(InputStream input) {
		try{
			SAXReader reader = new SAXReader();
			doc = reader.read(input);
		}catch(Exception e){
			throw new SystemException("初始化xmlParser出错 ",e);
		}
	}
	
	/**
	 * 
	 * 取得节点的值
	 * @param nodeName 节点全部路径
	 *            
	 */
	public  String getNodeVaule(String nodeName) {
		Node node= doc.selectSingleNode(nodeName);
		if (node == null){
            logger.warn("节点 [" + nodeName + "] 不存在");
		}
        return node != null ? node.getText() : "";
	}
	
	
	/**
	 *  以对象的形式返回子节点，用于返回多个节点值
	 * @param nodeName 属性上层节点名
	 * @param clazz clazz 存放子节点属性的类
	 * @return
	 */
	public <T> T getNodeVaule(Class<T> clazz) {
		try{
			Field[] fields = clazz.getDeclaredFields();// 获得属性
			T obj = clazz.newInstance();
			for (Field field : fields) {
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(),obj.getClass());
				Method writeMethod = pd.getWriteMethod();// 获得get方法
				ElementName element = field.getAnnotation(ElementName.class);
				if(element==null){
					continue;
				}
				;
				Node subNode = doc.selectSingleNode(element.name());
				if(!StringUtils.isBlank(element.attr())){
					String value = subNode.valueOf("@"+element.attr());
					writeMethod.invoke(obj,value);// 执行set方法返回一个Object
				}else if(subNode!=null){
					String value = subNode.getText();
					writeMethod.invoke(obj,value);// 执行set方法返回一个Object
				}
			}
			return obj;
		}catch(Exception e){
			throw new SystemException("解析Xml出错 class="+clazz.getSimpleName(),e);
		}
    	
	}
	
	
	 /**
     * 返回节点列表时
     * @param nodeName 属性上层节点名
     * @param Class<T> clazz 存放子节点属性的类
     * @return
     */
	@SuppressWarnings("unchecked")
    public <T>  List<T> getNodesValues(Class<T> clazz) {
		try{
	    	List<T> retList = new ArrayList<T>();
	    	
	    	String nodeName = clazz.getAnnotation(ElementName.class).name();
			List<Node> list = doc.selectNodes(nodeName);
	    	Iterator<Node> iterator = list.iterator();
	    	while(iterator.hasNext()){
	    		Node node = iterator.next();
	    		Field[] fields = clazz.getDeclaredFields();// 获得属性
	    		T obj = clazz.newInstance();
	    		for (Field field : fields) {
	    			PropertyDescriptor pd = new PropertyDescriptor(field.getName(),obj.getClass());
	    			Method writeMethod = pd.getWriteMethod();// 获得get方法
	    			ElementName element = field.getAnnotation(ElementName.class);
	    			if(element==null){
	    				continue;
	    			}
	    			boolean loop = element.loop();
	    			Node subNode = null;
	    			if(loop){
	    				subNode = node.selectSingleNode(element.name());
	    			}else{
	    				subNode = doc.selectSingleNode(element.name());
	    			}
	    			if(!StringUtils.isBlank(element.attr())){
						String value = subNode.valueOf("@"+element.attr());
						writeMethod.invoke(obj,value);// 执行set方法返回一个Object
					}
	    			else if(subNode!=null){
	    				String value = subNode.getText();
	    				writeMethod.invoke(obj,value);// 执行set方法返回一个Object
	    			}
	    		}
	    		retList.add(obj);
	    	}
	    	return retList;
		}catch(Exception e){
			throw new SystemException("解析Xml出错 class="+clazz.getSimpleName(),e);
		}
    }
    
	/**
	 * 
	 * 设置节点的值
	 * @param nodeName 节点全部路径
	 * @throws DocumentException 
	 *            
	 */
	public  void setNodeVaule(String nodeName,String nodeValue) {
		Node node= doc.selectSingleNode(nodeName);
		node.setText(nodeValue); 
	}
	
	/**
	 * 获取整个xm字符串
	 * @return
	 */
	public String getXmlStr(){
		return doc.asXML();
	}

	

}