package org.mjhost.sacajawea.location;

import java.util.HashMap;
import java.util.Map;

import org.mjhost.sacajawea.exceptions.InitializationException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Environments {

	public static final Environment ANY = new Environment("any"); 
	
	Environments() {
	}
	
	Map<String, Environment> available;
	
	public void initialize(Node metadata){
		if(available == null){
			if(metadata != null){
				available = new HashMap<String, Environment>();
				readAllEnvironments(metadata);
			}else{
				throw new InitializationException(InitializationException.NO_METADATA_PROVIDED);
			}
		}else{
			throw new InitializationException(InitializationException.ALREADY_INITIALIZED);
		}
	}
	
	void readAllEnvironments(Node metadata) {
		NodeList allElements = metadata.getChildNodes();
		int elementLength = allElements.getLength();
		for (int i = 0; i < elementLength; i++){
			Node element = allElements.item(i);
			if("environments".equalsIgnoreCase(element.getNodeName())){
				readEnvironments(element.getChildNodes());
			}
		}
	}
	
	void readEnvironments(NodeList elements){
		int length = elements.getLength();
		for(int i = 0; i < length; i++){
			Node element = elements.item(i);
			if("environment".equalsIgnoreCase(element.getNodeName())){
				addEnvironment(element.getChildNodes());
			}
		}
	}
	
	void addEnvironment(NodeList environmentElements){
		int length = environmentElements.getLength();
		Environment environment = new Environment("undefined");
		for(int i = 0; i < length; i++){
			Node element = environmentElements.item(i);
			if("name".equalsIgnoreCase(element.getNodeName())){
				environment.name = element.getTextContent();
			}else if("description".equalsIgnoreCase(element.getNodeName())){
				environment.description = element.getTextContent();
			}
		}
		if(!"undefined".equalsIgnoreCase(environment.getName())){
			available.put(environment.getName(), environment);
		}
	}
	
	public Environment get(String name){
		if(available.containsKey(name)){
			return available.get(name);
		}
		return new Environment("unknown");
	}

	static class Environment{
		private String name;
		private String description = "";
		
		Environment(String name) {
			this.name = name;
		}
		
		public String getDescription(){
			return this.description;
		}
		
		public String getName(){
			return this.name;
		}
		
		@Override
		public String toString() {
			return "[Environment " + this.name + "]";
		}
	}
}


