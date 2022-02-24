package Proiect;
/**
 * Exception class used for handling errors when creating or modifying tasks
 * @author Nevezi-Strango David
 *
 */
public class TaskException extends Exception{

	/**
	 * Exception constructor
	 * @param message Chosen from the static fields;
	 */
	public TaskException(String message) {
		super(message);
	}
	/**
	 * Static field for handling errors related to title
	 */
	public static String INVALID_TITLE = "The title is invalid";
	/**
	 * Static field for handling errors related to text
	 */
	public static String INVALID_TEXT = "The text is invalid!";
	/**
	 * Static field for handling errors related to deadline
	 */
	public static String INVALID_DEADLINE = "The deadline is invalid!";
	/**
	 * Static field for handling errors related to subject
	 */
	public static String INVALID_SUBJECT = "The subject is invalid!";
	/**
	 * Static field for handling errors related to cases when task has not been found. Used for deletions and searches
	 */
	public static String ID_NOT_FOUND = "Task with specified ID was not found!";
	/**
	 * Static field for handling errors related to cases when task has been found. Used for inserts and searches
	 */
	public static String ID_FOUND = "Task with specified ID can not be added: it is already in the list!";
}
