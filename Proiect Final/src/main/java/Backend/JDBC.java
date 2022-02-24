package Backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

import GUI.GUI;
import Proiect.Person;
import Proiect.Professor;
import Proiect.RedBlackTree;
import Proiect.Student;
import Proiect.Task;
import Proiect.TaskException;
/**
 * Class for handling the back-end part and also the user instance of the Task Manager Application (P3 project).
 * There are 6 tables in the relational database but the application uses only 5 out of them:
 *  -userprofessors to validate professors when logging in
 *  -similarly userstudents to validate students
 * 	-subjects is used to check what subjects is the user enrolled to 
 *  -tasks is for saving and extracting tasks.
 *  -taskdistribution is used for distributing newly created tasks to assignee students
 * @author Nevezi-Strango David
 *
 */
public class JDBC {
	/**
	 * User instance
	 */
	private static Person user;
	/**
	 * Connection instance
	 */
	private static Connection connectionInstance = null;
	/**
	 * JDBC instance
	 */
	private static JDBC instance = new JDBC();

	/**
	 * Constructor
	 */
	private JDBC() {
		
	}
	/**
	 * Function to create a connection to the database
	 * @throws SQLException if connection to the database has failed
	 * 
	 */
	private static void getConnection() throws SQLException {
		if (connectionInstance == null) {
			connectionInstance = DriverManager.getConnection("jdbc:mysql://localhost:3306/taskmanagement","root","root1root!");
		}
	}
	/**
	 * Function to close the connection to the database
	 * @throws SQLException if the application failed to close connection to the database
	 * 
	 */
	private static void closeConnection() throws SQLException {
		if (connectionInstance != null) {
			connectionInstance.close();
			connectionInstance = null;
		}
	}
	
	/**
	 * @return the User instance
	 */
	public static Person getUser() {
		return user;
	}
	/**
	 * @param userNew the User to set as instance
	 */
	public static void setUser(Person userNew) {
		user = userNew;
	}
	
	/**
	 * Function to search for user in database
	 * @param userString user's Name
	 * @param passwordString user's Password
	 * @return boolean whether user has been found or not
	 * @throws SQLException if an error occurred while accessing the database
	 */
	public static boolean searchUser(String userString, String passwordString) throws SQLException {
		getConnection();
		
		boolean returnBool = false;
		ResultSet resultSet = null;
		if(GUI.studBool) {
			PreparedStatement searchUser = connectionInstance.prepareStatement
					("select userid,name,faculty,year,specialization from userstudents where name = ? and password = ?");

			searchUser.setString(1, userString);
			searchUser.setString(2, passwordString);
			resultSet = searchUser.executeQuery();
			
			if(resultSet.next()) {
			user = new Student(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3), null,resultSet.getInt(4),resultSet.getString(5));
			}
			returnBool = true;

			resultSet.close();
			searchUser.close();
		} else if(GUI.profBool) {
			PreparedStatement searchUser = connectionInstance.prepareStatement
					("select userid,name,faculty,grade from userprofessors where name = ? and password = ?");

			searchUser.setString(1, userString);
			searchUser.setString(2, passwordString);
			resultSet = searchUser.executeQuery();
			
			if(resultSet.next()) {
				user = new Professor(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3), null,resultSet.getString(4));
			}
			returnBool = true;

			resultSet.close();
			searchUser.close();
		}
		closeConnection();
		return returnBool;
		
	}
	
	/**
	 * Function to create JTree for subjects
	 * @param userNode Subject parent JTreeNode
	 * @throws SQLException if an error occurred while accessing the database
	 */
	public static void createSubjectTree(DefaultMutableTreeNode userNode) throws SQLException {
		getConnection();
		 
		DefaultMutableTreeNode all = new DefaultMutableTreeNode("All");
		DefaultMutableTreeNode personal = new DefaultMutableTreeNode("Personal");
		userNode.add(all);
		userNode.add(personal);
		PreparedStatement searchSubjects = connectionInstance.prepareStatement
				("select subject from subjects where userid=?");
		searchSubjects.setInt(1, user.getUID());
		ResultSet resultSet = searchSubjects.executeQuery();
		while(resultSet.next()) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(resultSet.getString(1));
			userNode.add(node);
		}
		resultSet.close();
		searchSubjects.close();
		
		closeConnection();
	}
	/**
	 * Function to create the task container for the person instance. RedBlackTree is being used
	 * @throws SQLException if an error occurred while accessing the database
	 * @throws TaskException if invalid informations where introduced to the tasks
	 */
	public static void createTaskList() throws SQLException, TaskException {
		getConnection();
		
		RedBlackTree tasks = new RedBlackTree();

		PreparedStatement searchTasks;
		if(user instanceof Student) {
			searchTasks = connectionInstance.prepareStatement
					("select distinct SENDERID,t.TASKID,title,status, subject, DATE_FORMAT(deadline,'%d/%m/%Y'), "
							+ "text from tasks t , taskdistribution td where receiverid = ? "
							+ "and t.TASKID = td.TASKID order by deadline");
		}else {
			searchTasks = connectionInstance.prepareStatement
					("select distinct SENDERID,TASKID,title, subject, DATE_FORMAT(deadline,'%d/%m/%Y'), "
							+ "text from tasks where senderid = ? order by deadline");
			
		} 
		searchTasks.setInt(1, user.getUID());
		ResultSet resultSet = searchTasks.executeQuery();
		int i=0;
		while(resultSet.next()) {
			System.out.println((i++) + ". tasks");
			if(user instanceof Student) {
				tasks.insert(new Task(resultSet.getInt(1),resultSet.getInt(2),resultSet.getString(3),resultSet.getInt(4),resultSet.getString(5),resultSet.getString(6), resultSet.getString(7)));
			} else {
				tasks.insert(new Task(resultSet.getInt(1),resultSet.getInt(2),resultSet.getString(3),0,resultSet.getString(4),resultSet.getString(5), resultSet.getString(6)));
			}
		}
		resultSet.close();
		searchTasks.close();
		user.setTasks(tasks);
		
		closeConnection();
	}
	/**
	 * Function to search for every Student which had the specified task assigned to. Used for visualizing tasks for Professors
	 * @param taskID The Task's ID
	 * @return A HashMap(String,String), every entry is composed of the student's name and his task's status
	 * @throws SQLException if an error occurred while accessing the database
	 */
	public static Map<String,String> searchDistribution(int taskID) throws SQLException {
		getConnection();
		
		Map <String, String> studentStatus = new HashMap<String,String>();
		
		PreparedStatement distribution = connectionInstance.prepareStatement
				("select name,status from taskdistribution td, userstudents u where td.receiverid = u.userid and taskid=?");
		distribution.setInt(1, taskID);
		ResultSet resultSet = distribution.executeQuery();
		while(resultSet.next()) {
			String status = new String();
			int i = resultSet.getInt(2);
				if(i == 1) {
					status = "Open";
				}else if(i == 2) {
					status = "Accepted";
				}else if(i == 3) {
					status = "Blocked";
				}else if(i == 4) {
					status = "Done";
				}
			studentStatus.put(resultSet.getString(1), status);
		}
		resultSet.close();
		distribution.close();
		
		closeConnection();
		return studentStatus;
	}
	/**
	 * Function to save the current task (modified or newly added) into the database
	 * @param newTask the task that is intended to be modified or newly added
	 * @param isNew whether the newTask parameter is new or has just been modified
	 * @param isStatusChanged if only the status has been changed
	 * @throws SQLException if an error occurred while accessing the database
	 * @throws TaskException if the task could not have been replaced or inserted
	 */
	public static void saveTasks(Task newTask, boolean isNew, boolean isStatusChanged) throws SQLException, TaskException {
		getConnection();
		
		if(isStatusChanged) {
			
			PreparedStatement saveTask = connectionInstance.prepareStatement
						("update taskdistribution set status=? where receiverid=?");
			saveTask.setInt(1, newTask.getStatus());
			saveTask.setInt(2, user.getUID());
			saveTask.executeUpdate();
			saveTask.close();

			user.replaceTask(newTask);
			
		}else if(isNew) {
			
			PreparedStatement saveTask = connectionInstance.prepareStatement("insert into tasks values (default,?,?,?,?,?)");
			saveTask.setInt(1, newTask.getSenderID());
			saveTask.setString(2, newTask.getTitle());
			saveTask.setString(3, newTask.getSubject());
			saveTask.setString(4, newTask.getDeadline().format("yyyy-mm-dd"));
			saveTask.setString(5, newTask.getText());
			
			saveTask.executeUpdate();
			saveTask.close();
			
			PreparedStatement maxTaskID = connectionInstance.prepareStatement("select max(taskid) from tasks");
			ResultSet resultSet =  maxTaskID.executeQuery();
			resultSet.next();
			int nextTaskID=resultSet.getInt(1);
			newTask.setTaskid(nextTaskID);
			resultSet.close();
			maxTaskID.close();
				
			if(user instanceof Professor) {
				HashSet<Integer> studentID = new HashSet<Integer>();
				PreparedStatement studentsStatement = connectionInstance.prepareStatement
						("select userid from subjects where userid != ? and subject = ?");
				studentsStatement.setInt(1, user.getUID());
				studentsStatement.setString(2, newTask.getSubject());
				
				ResultSet resultSetStudents = studentsStatement.executeQuery();
				while(resultSetStudents.next()) {
					studentID.add(resultSetStudents.getInt(1));
				}
				
				resultSetStudents.close();
				studentsStatement.close();
				for(Integer studID : studentID) {
					PreparedStatement saveTaskDistribution = connectionInstance.prepareStatement
							("insert into taskdistribution values(?,?,1)");
					saveTaskDistribution.setInt(1, studID);
					saveTaskDistribution.setInt(2,newTask.getTaskid());
					saveTaskDistribution.executeUpdate();
					saveTaskDistribution.close();
				}
				
			} else {
				PreparedStatement saveTaskDistribution = connectionInstance.prepareStatement
						("insert into taskdistribution values (?,?,1)");
				saveTaskDistribution.setInt(1, user.getUID());
				saveTaskDistribution.setInt(2,newTask.getTaskid());
				saveTaskDistribution.executeUpdate();
				saveTaskDistribution.close();
			}

			user.addTask(newTask);
		
		} else {
		
			PreparedStatement updateTask = connectionInstance.prepareStatement
					("update tasks set title=?, subject=?, deadline=?,text=? where taskid=?");
			updateTask.setString(1, newTask.getTitle());
			updateTask.setString(2, newTask.getSubject());
			updateTask.setString(3, newTask.getDeadline().format("yyyy-mm-dd"));
			updateTask.setString(4, newTask.getText());
			updateTask.setInt(5, newTask.getTaskid());
			
			updateTask.executeUpdate();
			updateTask.close();

			user.replaceTask(newTask);
		
		}
		closeConnection();
	}
	/**
	 * Function to delete the intended task from the database
	 * @param task The task intended to be deleted
	 * @throws SQLException if an error occurred while accessing the database
	 * @throws TaskException if the task could not have been deleted
	 */
	public static void deleteTask(Task task) throws SQLException, TaskException {
		getConnection();
		
		PreparedStatement deleteTaskDistribution = connectionInstance.prepareStatement
				("delete from taskdistribution where taskid=?");
		deleteTaskDistribution.setInt(1, task.getTaskid());
		deleteTaskDistribution.executeUpdate();
		deleteTaskDistribution.close();
		
		PreparedStatement deleteTask = connectionInstance.prepareStatement
				("delete from tasks where taskid=?");
		deleteTask.setInt(1, task.getTaskid());
		deleteTask.executeUpdate();
		deleteTask.close();
		
		user.removeTask(task);
		
		closeConnection();
	}
	
}
