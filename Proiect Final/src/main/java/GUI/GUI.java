package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import Backend.JDBC;
import Proiect.CustomDate;
import Proiect.Person;
import Proiect.Professor;
import Proiect.RedBlackTree;
import Proiect.Student;
import Proiect.Task;
import Proiect.TaskException;

import java.awt.Insets;
import java.awt.Window;

/**
 * 
 * GUI for the Task Manager (P3 project)
 * @author Nevezi-Strango David
 */
public class GUI extends JFrame{

	
	/**
	 * JFrame for task management 
	 */
	JFrame taskFrame;
	/**
	 * JFrame for login
	 */
	JFrame loginFrame = this;
	/**
	 *   Used for various button eventhandling
	 */
	JDialog newDialog = null; 
	
	/**
	 * JTree to visualize the person's subjects
	 */
	JTree subjectJTree;
	/**
	 *  Used for listing all of the current task from the database
	 */
	DefaultListModel<Task> tasksListModel;
	/**
	 * Used for printing out the relevant information of the selected task to the end-User
	 */
	JTextArea selectedTaskArea;
	
	/**
	 * boolean value of the ProfessorButton
	 */
	public static boolean profBool = true;
	/**
	 * boolean value of the StudentButton
	 */
	public static boolean studBool = false;
	
	/**
	 * Constructor of the GUI Login screen
	 */
	public GUI() {
		super("Task Manager");
		this.setSize(400,300);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.enableInputMethods(true);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	
		createLoginButtons();
		this.pack();
		this.setVisible(true);
		
	}

	/**
	 * Function to create the login JFrame
	 */
	private void createLoginButtons() {
		
		JPanel loginPanel = new JPanel();
		JPanel personPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		personPanel.setLayout(new BoxLayout(personPanel,BoxLayout.X_AXIS ));
		personPanel.setPreferredSize(new Dimension(245, 35));
		loginPanel.setLayout(new BoxLayout(loginPanel,BoxLayout.Y_AXIS ));
		personPanel.setPreferredSize(new Dimension(245, 35));
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.setPreferredSize(new Dimension(245, 25));
		
		JLabel personLabel = new JLabel("I am a:");
		JRadioButton professorButton = new JRadioButton("Professor",this.profBool);
		professorButton.setPreferredSize(new Dimension(30, 50));
		JRadioButton studentButton = new JRadioButton("Student",this.studBool);
		studentButton.setPreferredSize(new Dimension(30, 50));

		ButtonGroup personBG = new ButtonGroup();
		personBG.add(professorButton);
		personBG.add(studentButton);
		
		personPanel.add(personLabel);
		personPanel.add(professorButton);
		personPanel.add(studentButton);
		
		
		JTextField userLogin = new JTextField("User",15);
		userLogin.setFont(userLogin.getFont().deriveFont(Font.ITALIC));
		userLogin.setForeground(Color.GRAY);
		JPasswordField passwordLogin = new JPasswordField("Password",15);
		passwordLogin.setFont(passwordLogin.getFont().deriveFont(Font.ITALIC));
		passwordLogin.setForeground(Color.GRAY);
		
		loginPanel.add(userLogin);
		loginPanel.add(passwordLogin);
		
		
		JButton loginButton = new JButton("Log in");
		loginButton.setPreferredSize(new Dimension(80, 30));
		JButton exitButton = new JButton("Exit");
		exitButton.setPreferredSize(new Dimension(80, 30));
		
		buttonPanel.add(loginButton,BorderLayout.WEST);
		buttonPanel.add(exitButton,BorderLayout.EAST);
		
		
		professorButton.addItemListener( new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(professorButton.isSelected()) {
					profBool = true;
					studBool = false;
					System.out.println("prof:" + profBool + "; stud:" + studBool);
				} 
				
			}
			
		});
		
		studentButton.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(studentButton.isSelected()) {
					studBool = true;
					profBool = false;
					System.out.println("prof:" + profBool + "; stud:" + studBool);
				}
				
			}
			
		});
		
		userLogin.addFocusListener( new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				JTextField src = (JTextField)e.getSource();
				if(src.getText().equals("User")) {
					src.setText("");
					src.setFont(src.getFont().deriveFont(Font.PLAIN));
					src.setForeground(Color.BLACK);
				}
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				JTextField src = (JTextField)e.getSource();
				if(src.getText().equals("")) {
					src.setText("User");
					src.setFont(src.getFont().deriveFont(Font.ITALIC));
					src.setForeground(Color.GRAY);
				}
			}
			
		});
		
		passwordLogin.addFocusListener( new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				JTextField src = (JTextField)e.getSource();
				if(src.getText().equals("Password")) {
					src.setText("");
					src.setFont(src.getFont().deriveFont(Font.PLAIN));
					src.setForeground(Color.BLACK);
				}
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				JTextField src = (JTextField)e.getSource();
				if(src.getText().equals("")) {
					src.setText("Password");
					src.setFont(src.getFont().deriveFont(Font.ITALIC));
					src.setForeground(Color.GRAY);
				}
			}
			
		});
		
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PasswordValidate(userLogin.getText(), String.valueOf(passwordLogin.getPassword()));	
				userLogin.setText("User");
				userLogin.setFont(userLogin.getFont().deriveFont(Font.ITALIC));
				userLogin.setForeground(Color.GRAY);
				passwordLogin.setText("Password");
				passwordLogin.setFont(passwordLogin.getFont().deriveFont(Font.ITALIC));
				passwordLogin.setForeground(Color.GRAY);
			}

			
			
		});

		loginButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0 ,false), "Login");
		loginButton.getActionMap().put("Login", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loginButton.doClick();
				
			}
			
		});
		
		
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
			
		});
		
		exitButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false), "Exit");
		exitButton.getActionMap().put("Exit", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				exitButton.doClick();
				
			}
			
		});
		this.add(personPanel);
		this.add(loginPanel);
		this.add(buttonPanel);
		
	}
	
	/**
	 * Function to validate the user
	 * @param userString user's Name
	 * @param passwordString user's Password
	 * @throws HeadlessException 
	 */
	private void PasswordValidate(String userString, String passwordString) {
		try {
			if(JDBC.searchUser(userString, passwordString) && !userString.equals("User") 
					&& !passwordString.equals("Password") && JDBC.getUser() != null) {
				System.out.println("ok");
				taskFrame = new JFrame("Manage your Tasks!");
				taskFrame.setLocationRelativeTo(loginFrame);
				loginFrame.setVisible(false);
				taskFrame.setSize(700,300);
				taskFrame.setResizable(false);
				taskFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
				taskFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
				
				createTaskFrame();
				taskFrame.setVisible(true);
			} else {
				System.out.println("wrong: " + userString + " " + passwordString);
				errorPopup(loginFrame,"Error","Login Failed! Try again!");
			}
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			errorPopup(loginFrame,"Error","<html><p align=\"center\">Failure accesing the database.Contact developers!</p></html>");
			e.printStackTrace();
		}
	}

	
	/**
	 * Function to create the task management JFrame and components
	 */
	private void createTaskFrame() {
		JPanel left = new JPanel();
		left.setLayout(new BorderLayout());
		JPanel middle = new JPanel();
		middle.setLayout(new BoxLayout(middle,BoxLayout.Y_AXIS ));
		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right,BoxLayout.Y_AXIS ));

		
		DefaultMutableTreeNode subjectNode = new DefaultMutableTreeNode("Subjects");
		try {
			JDBC.createSubjectTree(subjectNode);
			
		} catch (SQLException e2) {
			errorPopup(taskFrame,"Error!","<html><p align=\"center\">An error has occured while \ncreating the subject Tree.\n Contact developers</p></html>");
			e2.printStackTrace();
		}

		try {
			JDBC.createTaskList();
		} catch (SQLException e2) {
			errorPopup(taskFrame,"Error!","<html><p align=\"center\">An error has occured while \nshowing the list of tasks.\n Contact developers</p></html>");
			e2.printStackTrace();
		} catch (TaskException e2) {
			errorPopup(taskFrame,"Error!","<html><p align=\"center\">An error has occured while \nshowing the list of tasks.\n Contact developers</p></html>");
			e2.printStackTrace();
		}
		this.subjectJTree = new JTree(subjectNode);
		
		JButton logOutButton = new JButton("Log Out");
		left.add(subjectJTree, BorderLayout.NORTH);
		left.add(logOutButton, BorderLayout.SOUTH);
		
		
		
		JPanel taskLabelPanel = new JPanel();
		taskLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel taskListLabel = new JLabel("List of Tasks:");
		taskLabelPanel.add(taskListLabel); 
		
		this.tasksListModel = new DefaultListModel<Task>();
		JList<Task> tasksJList = new JList<Task>(tasksListModel);
		JScrollPane tasksPane = new JScrollPane(tasksJList);
		tasksPane.setPreferredSize(new Dimension(275,200));
		
		JPanel middleButtonPanel = new JPanel();
		middleButtonPanel.setLayout(new BorderLayout());
		JButton newButton = new JButton("New Task");
		JButton deleteButton = new JButton("Delete Task");
		middleButtonPanel.add(newButton,BorderLayout.WEST);
		middleButtonPanel.add(deleteButton,BorderLayout.EAST);
		
		middle.add(taskLabelPanel);
		middle.add(tasksPane);
		middle.add(middleButtonPanel);
		
		
		
		JPanel selectedLabelPanel = new JPanel();
		selectedLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel selectedTaskLabel = new JLabel("Selected Task:");
		selectedLabelPanel.add(selectedTaskLabel);
		
		this.selectedTaskArea = new JTextArea();
		JScrollPane selectedTask = new JScrollPane(selectedTaskArea);
		selectedTask.setPreferredSize(new Dimension(275,200));
		this.selectedTaskArea.setEditable(false);
		this.selectedTaskArea.setFont(this.selectedTaskArea.getFont().deriveFont(Font.BOLD));
		this.selectedTaskArea.setLineWrap(true);
		
		JPanel rightButtonPanel = new JPanel();
		rightButtonPanel.setLayout(new BorderLayout());
		JButton changeStatusButton = new JButton("Change Status");
		JButton editButton = new JButton("Edit Task");
		rightButtonPanel.add(changeStatusButton,BorderLayout.WEST);
		rightButtonPanel.add(editButton,BorderLayout.EAST);
		
		right.add(selectedLabelPanel);
		right.add(selectedTask);
		right.add(rightButtonPanel);
		

		this.taskFrame.add(left);
		this.taskFrame.add(middle);
		this.taskFrame.add(right);
		this.taskFrame.pack();
		this.subjectJTree.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				String subject = subjectJTree.getLastSelectedPathComponent().toString();
				tasksListModel.clear();
				
				selectedTaskArea.selectAll();
				selectedTaskArea.replaceSelection("");
				
				List<Task> taskList = new ArrayList<Task>();
				JDBC.getUser().getTasks().inOrder(taskList);
				if(subject.equals("All")) {
					tasksListModel.addAll(taskList);
				} else if (!subject.equals("Subjects")){
					for(Task task : taskList) {
						if(task.getSubject().equalsIgnoreCase(subject)) {
							tasksListModel.addElement(task);
							System.out.println(task.toString());
						}
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		tasksJList.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {

				Task selectedTask = tasksJList.getSelectedValue();
				if(JDBC.getUser() instanceof Student) {
					selectedTaskArea.selectAll();
					selectedTaskArea.replaceSelection(selectedTask.toStringAll());
				} else {
					String selectedToString = new String(selectedTask.getTitle() + "\n" +
							selectedTask.getDeadline().format("dd. mm. yyyy.") + "\n" + selectedTask.getText() + "\n\n");
					try {
						for(Map.Entry<String, String> entry : JDBC.searchDistribution(selectedTask.getTaskid()).entrySet()) {
								selectedToString += entry.getKey() + ": " + entry.getValue() + "\n";
								System.out.println(entry.getKey() + " " + entry.getValue());
						}
					} catch (SQLException e1) {
						errorPopup(taskFrame,"Error!","<html><p align=\"center\">An error has occured while showing the list of asignee students. Contact developers</p></html>");
						e1.printStackTrace();
					}
					System.out.println(selectedToString);
					selectedTaskArea.selectAll();
					selectedTaskArea.replaceSelection(selectedToString);
				}
				
				if(JDBC.getUser().getUID() != selectedTask.getSenderID()) {
					deleteButton.setEnabled(false);
					editButton.setEnabled(false);
				} else {
					deleteButton.setEnabled(true);
					editButton.setEnabled(true);
				}
				if((JDBC.getUser() instanceof Professor) && !selectedTask.getSubject().equals("Personal")){
					changeStatusButton.setEnabled(false);
				} else {
					changeStatusButton.setEnabled(true);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		logOutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				taskFrame.dispose();
				loginFrame.setVisible(true);
				JDBC.setUser(null);
				
			}
			
		});
		
		logOutButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false), "Log Out");
		logOutButton.getActionMap().put("Log Out", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logOutButton.doClick();
				
			}
			
		});
		
		newButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(subjectJTree.isSelectionEmpty() || subjectJTree.getLastSelectedPathComponent().toString().equals("Subjects")) {
					errorPopup(taskFrame,"Error!","You haven't selected a subject!");
				}else if( JDBC.getUser() instanceof Professor || subjectJTree.getLastSelectedPathComponent().toString().equals("Personal")) {
					//create a new Dialog for the creation of a new task
					newDialog = new JDialog(taskFrame, "Edit task!");
					newDialog.setSize(new Dimension(200,250));
					newDialog.getContentPane().setLayout(new BoxLayout(newDialog.getContentPane(),BoxLayout.Y_AXIS));
					newDialog.setResizable(false);
					JPanel taskDataPanel = new JPanel();
					taskDataPanel.setLayout(new GridLayout(4,2));
					JLabel titleLabel = new JLabel("Title:");
					JTextField titleField = new JTextField(16);
					JLabel subjectLabel = new JLabel("Subject:");
					JTextField subjectField = new JTextField(16);
					if(!subjectJTree.getLastSelectedPathComponent().toString().equals("All")) {
						subjectField.setText(subjectJTree.getLastSelectedPathComponent().toString());
					}
					JLabel deadlineLabel = new JLabel("Deadline:");
					JTextField deadlineField = new JTextField("dd/mm/yyyy",16);
					deadlineField.setFont(deadlineField.getFont().deriveFont(Font.ITALIC));
					deadlineField.setForeground(Color.GRAY);
					JLabel textLabel = new JLabel("Text:");
					JTextField textField = new JTextField(16);
					
					taskDataPanel.add(titleLabel);
					taskDataPanel.add(titleField);
					taskDataPanel.add(subjectLabel);
					taskDataPanel.add(subjectField);
					taskDataPanel.add(deadlineLabel);
					taskDataPanel.add(deadlineField);
					taskDataPanel.add(textLabel);
					taskDataPanel.add(textField);
					
					JPanel buttonPanel = new JPanel();
					buttonPanel.setLayout(new BorderLayout());
					JButton createButton = new JButton("Create");
					createButton.setPreferredSize(new Dimension(80,30));
					JButton cancelButton = new JButton("Cancel");
					cancelButton.setPreferredSize(new Dimension(80,30));
					
					buttonPanel.add(createButton,BorderLayout.WEST);
					buttonPanel.add(cancelButton,BorderLayout.EAST);
					newDialog.add(taskDataPanel);
					newDialog.add(buttonPanel);
					
					newDialog.setLocationRelativeTo(taskFrame);
					newDialog.setVisible(true);
					newDialog.pack();
					
					
					createButton.addActionListener(new ActionListener() {
	
						@Override
						public void actionPerformed(ActionEvent e) {
							Random random = new Random();
							boolean err = false;
							try {
								Task task = new Task(JDBC.getUser().getUID(), 0, titleField.getText(),1,subjectField.getText(),deadlineField.getText(),textField.getText());
								JDBC.saveTasks(task, true, false);

								
							} catch (TaskException e1) {
								String error = null;
								if(e1.getMessage().equalsIgnoreCase(TaskException.INVALID_TITLE)) {
									error = "<html><p align=\"center\">The title you have typed in is not accepted!</p></html>";
									
								} else if(e1.getMessage().equalsIgnoreCase(TaskException.INVALID_SUBJECT)) {
									error = "<html><p align=\"center\">The subject you have typed in is not accepted!</p></html>";
								} else if(e1.getMessage().equalsIgnoreCase(TaskException.INVALID_DEADLINE)) {
									error = "<html><p align=\"center\">The deadline you have typed in is not accepted!</p></html>";
								} else if(e1.getMessage().equalsIgnoreCase(TaskException.INVALID_TEXT)) {
									error = "<html><p align=\"center\">The text you have typed in is not accepted!</p></html>";
								}
								errorPopup(newDialog,"Error!",error);
								err = true;
								e1.printStackTrace();
							} catch (SQLException e1) {
								err = true;
								errorPopup(newDialog,"Error!","<html><p align=\"center\">An error has occured saving the task. Contact developers</p></html>");
								e1.printStackTrace();
							}
							if(!err) {
								successPopup(newDialog,"Task Created!","Task has been created successfully!");
							}
							String subject = subjectJTree.getLastSelectedPathComponent().toString();
							tasksListModel.clear();
							
							selectedTaskArea.selectAll();
							selectedTaskArea.replaceSelection("");
							
							List<Task> taskList = new ArrayList<Task>();
							JDBC.getUser().getTasks().inOrder(taskList);
							if(subject.equals("All")) {
								tasksListModel.addAll(taskList);
							} else if (!subject.equals("Subjects")){
								for(Task task : taskList) {
									if(task.getSubject().equalsIgnoreCase(subject)) {
										tasksListModel.addElement(task);
										System.out.println(task.toString());
									}
								}
							}
							
						}
						
						
					});
					
					createButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0 ,false), "OK");
					createButton.getActionMap().put("OK", new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
							createButton.doClick();
							
						}
						
					});
					
					cancelButton.addActionListener(new ActionListener() {
	
						@Override
						public void actionPerformed(ActionEvent e) {
							newDialog.dispose();
						}
						
					});
					
					cancelButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false), "Cancel");
					cancelButton.getActionMap().put("Cancel", new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
							cancelButton.doClick();
							
						}
						
					});
					
					deadlineField.addFocusListener(new FocusListener() {
	
						@Override
						public void focusGained(FocusEvent e) {
							JTextField src = (JTextField)e.getSource();
							if(src.getText().equals("dd/mm/yyyy")) {
								src.setText("");
								src.setFont(src.getFont().deriveFont(Font.PLAIN));
								src.setForeground(Color.BLACK);
							}
						}
	
						@Override
						public void focusLost(FocusEvent e) {
							JTextField src = (JTextField)e.getSource();
							if(src.getText().equals("")) {
								src.setText("dd/mm/yyyy");
								src.setFont(src.getFont().deriveFont(Font.ITALIC));
								src.setForeground(Color.GRAY);
							}
							
							
						}
						
					});
					
				} else {
					errorPopup(newDialog,"Error!","<html><p align=\"center\">You cannot create new tasks for this subject</p></html>");
				}
			}
		});
		
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean err = false;
				try {
					if(tasksJList.getSelectedIndex() == -1) {
						err = true;
						errorPopup(taskFrame,"Error!","No task has been selected!");
					} else {
						JDBC.deleteTask(tasksJList.getSelectedValue());
						
					}
				} catch (TaskException e1) {
					err = true;
					e1.printStackTrace();
					errorPopup(taskFrame,"Error!","Task could not have been deleted!");
					
				} catch (SQLException e1) {
					err = true;
					errorPopup(taskFrame,"Error!","<html><p align=\"center\">An error has occured while deleting the task. Contact developers</p></html>");
					e1.printStackTrace();
				}

				if(!err) {
					successPopup(taskFrame,"Task Deleted!","Task has been deleted successfully!");
				}
				String subject = subjectJTree.getLastSelectedPathComponent().toString();
				tasksListModel.clear();
				
				selectedTaskArea.selectAll();
				selectedTaskArea.replaceSelection("");
				
				List<Task> taskList = new ArrayList<Task>();
				JDBC.getUser().getTasks().inOrder(taskList);
				if(subject.equals("All")) {
					tasksListModel.addAll(taskList);
				} else if (!subject.equals("Subjects")){
					for(Task task : taskList) {
						if(task.getSubject().equalsIgnoreCase(subject)) {
							tasksListModel.addElement(task);
							System.out.println(task.toString());
						}
					}
				}
				
			}

			
			
		});
		
		changeStatusButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//creation of new Dialog for changing the status of the selected task
				if(tasksJList.getSelectedIndex() == -1) {
					errorPopup(taskFrame,"Error!","No task has been selected!");
				} else {
					newDialog = new JDialog(taskFrame, "Change status of the task!");
					newDialog.setSize(new Dimension(200,250));
					newDialog.getContentPane().setLayout(new BoxLayout(newDialog.getContentPane(),BoxLayout.Y_AXIS));
					newDialog.setResizable(false);
					JPanel taskDataPanel = new JPanel();
					taskDataPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
					ButtonGroup bg = new ButtonGroup();
					JRadioButton acceptedButton = new JRadioButton("Accepted");
					JRadioButton blockedButton = new JRadioButton("Blocked");
					JRadioButton doneButton = new JRadioButton("Done");
					bg.add(acceptedButton);
					bg.add(blockedButton);
					bg.add(doneButton);
					int selectedTaskStatus = tasksJList.getSelectedValue().getStatus();
					if(selectedTaskStatus == 2) {
						acceptedButton.setSelected(true);
					}else if(selectedTaskStatus == 3) {
						blockedButton.setSelected(true);
					}else if(selectedTaskStatus == 4) {
						doneButton.setSelected(true);
					}
					
					taskDataPanel.add(acceptedButton);
					taskDataPanel.add(blockedButton);
					taskDataPanel.add(doneButton);
					
					JPanel buttonPanel = new JPanel();
					buttonPanel.setLayout(new BorderLayout());
					//buttonPanel.setPreferredSize(new Dimension(50,250));
					JButton okButton = new JButton("Update");
					okButton.setPreferredSize(new Dimension(80,30));
					JButton cancelButton = new JButton("Cancel");
					cancelButton.setPreferredSize(new Dimension(80,30));
					
					buttonPanel.add(okButton,BorderLayout.WEST);
					buttonPanel.add(cancelButton,BorderLayout.EAST);
					newDialog.add(taskDataPanel);
					newDialog.add(buttonPanel);
					
					newDialog.setLocationRelativeTo(taskFrame);
					newDialog.setVisible(true);
					newDialog.pack();
					
					okButton.addActionListener(new ActionListener() {
	
						@Override
						public void actionPerformed(ActionEvent e) {
							Task taskStatusChange = tasksJList.getSelectedValue();
							int selectedStatus;
							if(acceptedButton.isSelected()) {
								selectedStatus = 2;
							} else if(blockedButton.isSelected()) {
								selectedStatus = 3;
							} else if(doneButton.isSelected()) {
								selectedStatus = 4;
							} else {
								selectedStatus = 1;
							}
							taskStatusChange.setStatus(selectedStatus);
							boolean err = false;
							try {
								JDBC.saveTasks(taskStatusChange, false, true);
								
							} catch (SQLException e1) {
								errorPopup(newDialog,"Error!","<html><p align=\"center\">An error has occured while updating the task. Contact developers</p></html>");
								err = true;
								e1.printStackTrace();
							} catch (TaskException e1) {
								err = true;
								errorPopup(newDialog,"Error!","<html><p align=\"center\">You have selected an invalid status!.</p></html>");
								e1.printStackTrace();
							}
							if(!err) {
								successPopup(newDialog,"Status updated successfully!","The status of the task has been updated!");
							}
							Task selectedTask = tasksJList.getSelectedValue();
							selectedTaskArea.selectAll();
							selectedTaskArea.replaceSelection(selectedTask.toStringAll());
						}	
					});
					
					okButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0 ,false), "OK");
					okButton.getActionMap().put("OK", new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
							okButton.doClick();
							
						}
						
					});
					
					cancelButton.addActionListener(new ActionListener() {
	
						@Override
						public void actionPerformed(ActionEvent e) {
							newDialog.dispose();
							
						}
						
					});
					
					cancelButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false), "Cancel");
					cancelButton.getActionMap().put("Cancel", new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
							cancelButton.doClick();
							
						}
						
					});
				}
			}
		});
		
		editButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//creation of new Dialog for editing the selected task
				if(tasksJList.getSelectedIndex() == -1) {
					errorPopup(newDialog,"Error!","No task has been selected!");
				} else {
					
					newDialog = new JDialog(taskFrame, "Edit the task!");
					newDialog.setSize(new Dimension(200,250));
					newDialog.getContentPane().setLayout(new BoxLayout(newDialog.getContentPane(),BoxLayout.Y_AXIS));
					newDialog.setResizable(false);
					JPanel taskDataPanel = new JPanel();
					taskDataPanel.setLayout(new GridLayout(4,2));
					JLabel titleLabel = new JLabel("Title:");
					JTextField titleField = new JTextField(16);
					titleField.setText(tasksJList.getSelectedValue().getTitle());
					JLabel subjectLabel = new JLabel("Subject:");
					JTextField subjectField = new JTextField(16);
					if(!subjectJTree.getLastSelectedPathComponent().toString().equals("All")) {
						subjectField.setText(subjectJTree.getLastSelectedPathComponent().toString());
					}
					subjectField.setEnabled(false);
					JLabel deadlineLabel = new JLabel("Deadline:");
					JTextField deadlineField = new JTextField("dd/mm/yyyy",16);
					deadlineField.setText(tasksJList.getSelectedValue().getDeadline().format("dd/mm/yyyy"));
					JLabel textLabel = new JLabel("Text:");
					JTextField textField = new JTextField(16);
					textField.setText(tasksJList.getSelectedValue().getText());
					
					taskDataPanel.add(titleLabel);
					taskDataPanel.add(titleField);
					taskDataPanel.add(subjectLabel);
					taskDataPanel.add(subjectField);
					taskDataPanel.add(deadlineLabel);
					taskDataPanel.add(deadlineField);
					taskDataPanel.add(textLabel);
					taskDataPanel.add(textField);
					
					JPanel buttonPanel = new JPanel();
					buttonPanel.setLayout(new BorderLayout());
					//buttonPanel.setPreferredSize(new Dimension(50,250));
					JButton updateButton = new JButton("Update");
					updateButton.setPreferredSize(new Dimension(80,30));
					JButton cancelButton = new JButton("Cancel");
					cancelButton.setPreferredSize(new Dimension(80,30));
					
					buttonPanel.add(updateButton,BorderLayout.WEST);
					buttonPanel.add(cancelButton,BorderLayout.EAST);
					newDialog.add(taskDataPanel);
					newDialog.add(buttonPanel);
					
					newDialog.setLocationRelativeTo(taskFrame);
					newDialog.setVisible(true);
					newDialog.pack();
					
					updateButton.addActionListener(new ActionListener() {
	
						@Override
						public void actionPerformed(ActionEvent e) {

							Task selectedTask = tasksJList.getSelectedValue();

							boolean err = false;
							try {
								selectedTask.setTitle(titleField.getText());
								selectedTask.setDeadline(new CustomDate(deadlineField.getText()));
								selectedTask.setText(textField.getText());

								JDBC.saveTasks(selectedTask, false, false);
							} catch (SQLException e1) {
								err = true;
								errorPopup(newDialog,"Error!","<html><p align=\"center\">An error has occured while updating the task. Contact developers</p></html>");
								e1.printStackTrace();
							} catch (TaskException e1) {
								e1.printStackTrace();
								String error = null;
								if(e1.getMessage().equalsIgnoreCase(TaskException.INVALID_TITLE)) {
									error = "<html><p align=\"center\">The title you have typed in is not accepted!</p></html>";
								} else if(e1.getMessage().equalsIgnoreCase(TaskException.INVALID_SUBJECT)) {
									error = "<html><p align=\"center\">The subject you have typed in is not accepted!</p></html>";
								} else if(e1.getMessage().equalsIgnoreCase(TaskException.INVALID_DEADLINE)) {
									error = "<html><p align=\"center\">The deadline you have typed in is not accepted!</p></html>";
								} else if(e1.getMessage().equalsIgnoreCase(TaskException.INVALID_TEXT)) {
									error = "<html><p align=\"center\">The text you have typed in is not accepted!</p></html>";
								}
								errorPopup(newDialog,"Error!",error);
								err = true;
							}
							

							if(!err) {
								successPopup(newDialog,"Task updated successfully!", "You have successfully updated the task!");
							}
							if(JDBC.getUser() instanceof Student) {
								selectedTaskArea.selectAll();
								selectedTaskArea.replaceSelection(selectedTask.toStringAll());
							} else {
								String selectedToString = new String(selectedTask.getTitle() + "\n" +
										selectedTask.getDeadline().format("dd. mm. yyyy.") + "\n" + selectedTask.getText() + "\n\n");
								try {
									for(Map.Entry<String, String> entry : JDBC.searchDistribution(selectedTask.getTaskid()).entrySet()) {
											selectedToString += entry.getKey() + ": " + entry.getValue() + "\n";
											System.out.println(entry.getKey() + " " + entry.getValue());
									}
								} catch (SQLException e1) {
									errorPopup(newDialog,"Error!","<html><p align=\"center\">An error has occured while showing the list of asignee students. Contact developers</p></html>");
									e1.printStackTrace();
								}
								System.out.println(selectedToString);
								selectedTaskArea.selectAll();
								selectedTaskArea.replaceSelection(selectedToString);
							}				
						}
						
					});
					
					updateButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0 ,false), "OK");
					updateButton.getActionMap().put("OK", new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
							updateButton.doClick();
							
						}
						
					});
					
					cancelButton.addActionListener(new ActionListener() {
	
						@Override
						public void actionPerformed(ActionEvent e) {
							newDialog.dispose();
							
						}
						
					});
					
					cancelButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0,false), "Cancel");
					cancelButton.getActionMap().put("Cancel", new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
							cancelButton.doClick();
							
						}
						
					});
				}
			}
			
		});
	}
	/**
	 * Function to create an error Pop-up Dialog
	 * @param title The title of the dialog
	 * @param text The text of the dialog
	 * @return 
	 */
	private <T extends Window> void errorPopup(T Owner, String title, String text) {
		JDialog errorDialog = new JDialog(Owner,title);
		errorDialog.setPreferredSize(new Dimension(250,100));
		errorDialog.getContentPane().setLayout(new BoxLayout(errorDialog.getContentPane(), BoxLayout.Y_AXIS));
		JPanel errorPanel = new JPanel();
		
		errorPanel.setLayout(new BoxLayout(errorPanel, BoxLayout.Y_AXIS));
		JLabel errorLabel = new JLabel(text);
		errorLabel.setAlignmentX(CENTER_ALIGNMENT);
		JButton okButton = new JButton("OK");
		okButton.setAlignmentX(CENTER_ALIGNMENT);
		errorPanel.add(errorLabel);
		errorPanel.add(okButton);
		errorDialog.add(errorPanel);
		errorDialog.setLocationRelativeTo(Owner);
		errorDialog.setVisible(true);
		errorDialog.setResizable(false);
		errorDialog.pack();
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				errorDialog.dispose();
			}
			
		});
		
		okButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0 ,false), "OK");
		okButton.getActionMap().put("OK", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				okButton.doClick();
				
			}
			
		});
		
	}
	/**
	 * Function to create a success Pop-up Dialog
	 * @param title The title of the dialog
	 * @param text The text of the dialog
	 */
	private <T extends Window> void successPopup(T Owner,String title, String text) {
		JDialog successDialog = new JDialog(Owner,title);
		successDialog.setPreferredSize(new Dimension(250,100));
		successDialog.getContentPane().setLayout(new BoxLayout(successDialog.getContentPane(), BoxLayout.Y_AXIS));
		JPanel successPanel = new JPanel();
		successPanel.setLayout(new BoxLayout(successPanel, BoxLayout.Y_AXIS));
		JLabel errorLabel = new JLabel(text);
		errorLabel.setAlignmentX(CENTER_ALIGNMENT);
		JButton okButton = new JButton("OK");
		okButton.setAlignmentX(CENTER_ALIGNMENT);
		successPanel.add(errorLabel);
		successPanel.add(okButton);
		successDialog.add(successPanel);
		successDialog.setLocationRelativeTo(Owner);
		successDialog.setVisible(true);
		successDialog.setResizable(false);
		successDialog.pack();
		okButton.addActionListener(new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				successDialog.dispose();
				if(Owner == newDialog) {
					Owner.dispose();
				}
				
			}
			
		});
		
		okButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0 ,false), "OK");
		okButton.getActionMap().put("OK", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				okButton.doClick();
				
			}
			
		});
	}

}
