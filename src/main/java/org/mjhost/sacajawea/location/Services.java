package org.mjhost.sacajawea.location;

import java.util.HashMap;
import java.util.Map;

import org.mjhost.sacajawea.exceptions.InitializationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Services {

	public static final Service ANY = new Service("any", null); 
	
	public Services() {
	}
	
	Map<String, Service> available;
	
	public void initialize(Document metadata){
		if(available == null){
			if(metadata != null){
				available = new HashMap<String, Service>();
				readAllServices(metadata);
			}else{
				throw new InitializationException(InitializationException.NO_METADATA_PROVIDED);
			}
		}else{
			throw new InitializationException(InitializationException.ALREADY_INITIALIZED);
		}
	}
	
	void readAllServices(Document metadata) {
		NodeList allElements = metadata.getChildNodes();
		int elementLength = allElements.getLength();
		for (int i = 0; i < elementLength; i++){
			Node element = allElements.item(i);
			if("services".equalsIgnoreCase(element.getNodeName())){
				readServices(element.getChildNodes(), Services.ANY);
			}
		}
	}
	
	void readServices(NodeList elements, Service parent){
		int length = elements.getLength();
		for(int i = 0; i < length; i++){
			Node element = elements.item(i);
			if("service".equalsIgnoreCase(element.getNodeName())){
				addService(element.getChildNodes(), parent);
			}
		}
	}
	
	void addService(NodeList serviceElements, Service parent){
		int length = serviceElements.getLength();
		Service service = new Service("undefined", parent);
		for(int i = 0; i < length; i++){
			Node element = serviceElements.item(i);
			if("name".equalsIgnoreCase(element.getNodeName())){
				service.name = element.getTextContent();
			}else if("description".equalsIgnoreCase(element.getNodeName())){
				service.description = element.getTextContent();
			}else if("service".equalsIgnoreCase(element.getNodeName())){
				addService(element.getChildNodes(), service);
			}
		}
		if(!"undefined".equalsIgnoreCase(service.getName())){
			available.put(service.getName(), service);
		}
	}

	public Service get(String name){
		if(available.containsKey(name)){
			return available.get(name);
		}
		return new Service("unknown");
	}
	

	static class Service{
		private String name;
		private String description;
		private Service parent;
		
		public Service(String name) {
			this(name, Services.ANY);
		}
		
		public String getName() {
			return name;
		}
		public String getDescription() {
			return description;
		}
		public Service getParent() {
			if(parent == null){
				return Services.ANY;
			}
			return parent;
		}

		public Service(String name, Service parent){
			this.name = name;
			this.parent = parent;
		}
		
		@Override
		public String toString() {
			return "[Service " + this.name + "]";
		}
	}
}
