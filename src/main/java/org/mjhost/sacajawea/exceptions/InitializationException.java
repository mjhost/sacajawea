/**
 * 
 */
package org.mjhost.sacajawea.exceptions;

/**
 * @author Paolo Mariotti
 *
 */
public class InitializationException extends RuntimeException {

	private static final long serialVersionUID = -2513670864275697652L;
	
	public static final String ALREADY_INITIALIZED = "This Object cannot be initialized more than once";
	public static final String NO_METADATA_PROVIDED = "No metadata provided to initialize this Object";
	public static final String CANNOT_INITIALIZE = "There was a problem with initialization values provided";

	public InitializationException(String message) {
		super(message);
	}
	
	public InitializationException(String message, Throwable throwable){
		super(message, throwable);
	}
	
}
