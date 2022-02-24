package Proiect;

import java.util.List;
/**
 * The Person abstract class used for the P3 Project
 * 
 * @author Nevezi-Strango David
 *
 */
public abstract class Person {
	/**
	 * User's Identification number
	 */
	int uID;
	/**
	 * User's name
	 */
	String name;
	/**
	 * User's Faculty
	 */
	String faculty;
	/**
	 * User's list of tasks. A RedBlack Tree is implemented for handling the tasks
	 */
	RedBlackTree tasks;

	/**
	 * Constructor for Persons. Since it is an abstract class, there cannot be created an instance of the class
	 * 
	 * @param uid User's Identification number
	 * @param name User's name
	 * @param faculty User's Faculty
	 * @param tasks User's list of tasks
	 */
	public Person(int uid, String name, String faculty, RedBlackTree tasks) {

		this.uID = uid;
		this.name = name;
		this.faculty = faculty;
		this.tasks = tasks;
	}
	/**
	 * This function adds a new task to the target person's task list
	 * 
	 * @param newTask The new task intended to be added
	 * @throws TaskException if task with same ID is already in
	 */
	public void addTask(Task newTask) throws TaskException {
		if(!tasks.searchIf(newTask.getTaskid())) {
			tasks.insert(newTask);
		} else {
			throw new TaskException(TaskException.ID_FOUND);
		}
	}
	/**
	 * This function removes a specified task from a person's task list
	 * @param newTask The task that is intended to be removed
	 * @throws TaskException if task is not in the list
	 */
	public void removeTask(Task newTask) throws TaskException {
		if(tasks.searchIf(newTask.getTaskid())) {
			tasks.deleteNode(newTask);
		} else {
			throw new TaskException(TaskException.ID_NOT_FOUND);
		}
	}
	/**
	 * This function replace a specified task from a person's task list 
	 * @param task The task meant to be replaced
	 * @throws TaskException  if task is not in the list
	 */
	public void replaceTask(Task task) throws TaskException {
		if(tasks.searchIf(task.getTaskid())){
			tasks.replace(task);
		} else {
			throw new TaskException(TaskException.ID_NOT_FOUND);
		}
		
	}
	/**
	 * @return the uID
	 */
	public int getUID() {
		return uID;
	}
	/**
	 * @param uID to set
	 */
	public void setUID(int uID) {
		this.uID = uID;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the faculty
	 */
	public String getFaculty() {
		return faculty;
	}
	/**
	 * @param faculty to set
	 */
	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
	/**
	 * @return the tasks
	 */
	public RedBlackTree getTasks() {
		return tasks;
	}
	/**
	 * @param tasks to set
	 */
	public void setTasks(RedBlackTree tasks) {
		this.tasks = tasks;
	}

	
	
}
