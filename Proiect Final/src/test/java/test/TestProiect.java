package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import Proiect.RedBlackTree;
import Proiect.Student;
import Proiect.Task;
import Proiect.TaskException;


public class TestProiect {
	@Test
	public void testRemove1() throws TaskException {
		Task task1 = new Task(2222,123, "Test P3",1,"P3", "28/11/2021", "Test din lab1-8");
		List<Task> tasks = new ArrayList<Task>(List.of(
					new Task(2222,5462, "Test SO1",2,"SO1", "10/12/2021", "Test din lab1-8"),
					new Task(2222,6835, "Tema SO1",2,"SO1", "09/12/2021", "Tema din lab8"),
					task1,
					new Task(2222,5687, "Tema BD1",1,"BD1", "22/11/2021", "Tema din lab6"),
					new Task(2222,56862, "Test BD1",1,"BD1", "25/11/2021", "Test din lab1-8")
				));
		RedBlackTree redblack = new RedBlackTree();
		for(Task task : tasks) {
			redblack.insert(task);
		}
		Student student = new Student(1234, "Nevezi David", "UVT-FMI",redblack, 2, "Applied Computer Science");
		List<Task> tasksFiltered = tasks.stream().filter(task -> task.getTaskid() != 123).collect(Collectors.toList());
		Assertions.assertDoesNotThrow(() -> student.removeTask(task1));
		List<Task> inOrderList = new ArrayList<Task>();
		student.getTasks().inOrder(inOrderList);
		Assertions.assertTrue(inOrderList.containsAll(tasksFiltered));
	}
	
	@Test
	public void testRemove2() throws TaskException {
		Task task1 = new Task(2222,123, "Test P3",1,"P3", "28/11/2021", "Test din lab1-8");
		List<Task> tasks = new ArrayList<Task>(List.of(
				new Task(2222,5462, "Test SO1",2,"SO1", "10/12/2021", "Test din lab1-8"),
				new Task(2222,6835, "Tema SO1",2,"SO1", "09/12/2021", "Tema din lab8"),
				new Task(2222,5687, "Tema BD1",1,"BD1", "22/11/2021", "Tema din lab6"),
				new Task(2222,56862, "Test BD1",1,"BD1", "25/11/2021", "Test din lab1-8")
			));
		RedBlackTree redblack = new RedBlackTree();
		for(Task task : tasks) {
			redblack.insert(task);
		}
		Student student = new Student(1234, "Nevezi David", "UVT-FMI",redblack, 2, "Applied Computer Science");
		Assertions.assertThrows(TaskException.class, () -> student.removeTask(task1));
		List<Task> tasksFiltered = new ArrayList<Task>(List.copyOf(tasks));
		List<Task> inOrderList = new ArrayList<Task>();
		student.getTasks().inOrder(inOrderList);
		Assertions.assertTrue(tasksFiltered.containsAll(inOrderList));
	}
	
	@Test
	public void testReplace1() throws TaskException {
		Task task1 = new Task(2222,123, "Test P3",1,"P3", "28/11/2021", "Test din lab1-8");
		List<Task> tasks = new ArrayList<Task>(List.of(
				new Task(2222,5462, "Test SO1",2,"SO1", "10/12/2021", "Test din lab1-8"),
				new Task(2222,6835, "Tema SO1",2,"SO1", "09/12/2021", "Tema din lab8"),
				new Task(2222,5687, "Tema BD1",1,"BD1", "22/11/2021", "Tema din lab6"),
				new Task(2222,56862, "Test BD1",1,"BD1", "25/11/2021", "Test din lab1-8")
			));
		RedBlackTree redblack = new RedBlackTree();
		for(Task task : tasks) {
			redblack.insert(task);
		}
		Student student = new Student(1234, "Nevezi David", "UVT-FMI",redblack, 2, "Applied Computer Science");
		Assertions.assertThrows(TaskException.class, () -> student.replaceTask(task1));
		Assertions.assertFalse(student.getTasks().replace(task1));
	}
	
	@Test
	public void testReplace2() throws TaskException {
		Task task1 = new Task(2222,123, "Test P3",1,"P3", "28/11/2021", "Test din lab1-8");
		Task task2 = new Task(2222,123, "Test alabala",1,"P3", "28/11/2021", "Test");
		List<Task> tasks = new ArrayList<Task>(List.of(
					new Task(2222,5462, "Test SO1",2,"SO1", "10/12/2021", "Test din lab1-8"),
					new Task(2222,6835, "Tema SO1",2,"SO1", "09/12/2021", "Tema din lab8"),
					task1,
					new Task(2222,5687, "Tema BD1",1,"BD1", "22/11/2021", "Tema din lab6"),
					new Task(2222,56862, "Test BD1",1,"BD1", "25/11/2021", "Test din lab1-8")
				));
		RedBlackTree redblack = new RedBlackTree();
		for(Task task : tasks) {
			redblack.insert(task);
		}
		Student student = new Student(1234, "Nevezi David", "UVT-FMI",redblack, 2, "Applied Computer Science");
		Assertions.assertDoesNotThrow(() -> student.replaceTask(task2));
		Assertions.assertTrue(student.getTasks().replace(task2));
		Assertions.assertEquals(student.getTasks().search(task2).getTitle(),task2.getTitle());
		
	}
	
	@Test
	public void testAdd1() throws TaskException {
		Task task1 = new Task(2222,123, "Test P3",1,"P3", "28/11/2021", "Test din lab1-8");
		List<Task> tasks = new ArrayList<Task>(List.of(
					new Task(2222,5462, "Test SO1",2,"SO1", "10/12/2021", "Test din lab1-8"),
					new Task(2222,6835, "Tema SO1",2,"SO1", "09/12/2021", "Tema din lab8"),
					task1,
					new Task(2222,5687, "Tema BD1",1,"BD1", "22/11/2021", "Tema din lab6"),
					new Task(2222,56862, "Test BD1",1,"BD1", "25/11/2021", "Test din lab1-8")
				));
		RedBlackTree redblack = new RedBlackTree();
		for(Task task : tasks) {
			redblack.insert(task);
		}
		Student student = new Student(1234, "Nevezi David", "UVT-FMI",redblack, 2, "Applied Computer Science");
		Assertions.assertThrows(TaskException.class,() -> student.addTask(task1));
		List<Task> inOrderList = new ArrayList<Task>();
		student.getTasks().inOrder(inOrderList);
		List<Task> tasksFiltered = new ArrayList<Task>(List.copyOf(tasks));
		student.getTasks().inOrder(inOrderList);
		Assertions.assertTrue(tasksFiltered.containsAll(inOrderList));
	}
	
	@Test
	public void testInOrder() throws TaskException {
		Task task1 = new Task(2222,123, "Test P3",1,"P3", "28/11/2021", "Test din lab1-8");
		List<Task> tasks = new ArrayList<Task>(List.of(
					new Task(2222,5462, "Test SO1",2,"SO1", "10/12/2021", "Test din lab1-8"),
					new Task(2222,6835, "Tema SO1",2,"SO1", "09/12/2021", "Tema din lab8"),
					task1,
					new Task(2222,5687, "Tema BD1",1,"BD1", "22/11/2021", "Tema din lab6"),
					new Task(2222,56862, "Test BD1",1,"BD1", "25/11/2021", "Test din lab1-8")
				));
		RedBlackTree redblack = new RedBlackTree();
		for(Task task : tasks) {
			redblack.insert(task);
		}
		Student student = new Student(1234, "Nevezi David", "UVT-FMI",redblack, 2, "Applied Computer Science");
		List<Task> inOrderList = new ArrayList<Task>();
		student.getTasks().inOrder(inOrderList);
		Assertions.assertTrue(inOrderList.containsAll(tasks));
	}
	@Test
	public void testAdd2() throws TaskException {
		Task task1 = new Task(2222,123, "Test P3",1,"P3", "28/11/2021", "Test din lab1-8");
		List<Task> tasks = new ArrayList<Task>(List.of(
				new Task(2222,5462, "Test SO1",2,"SO1", "10/12/2021", "Test din lab1-8"),
				new Task(2222,6835, "Tema SO1",2,"SO1", "09/12/2021", "Tema din lab8"),
				new Task(2222,5687, "Tema BD1",1,"BD1", "22/11/2021", "Tema din lab6"),
				new Task(2222,56862, "Test BD1",1,"BD1", "25/11/2021", "Test din lab1-8")
			));
		RedBlackTree redblack = new RedBlackTree();
		for(Task task : tasks) {
			redblack.insert(task);
		}
		Student student = new Student(1234, "Nevezi David", "UVT-FMI",redblack, 2, "Applied Computer Science");
		Assertions.assertDoesNotThrow(() -> student.addTask(task1));
		tasks.add(task1);
		List<Task> inOrderList = new ArrayList<Task>();
		student.getTasks().inOrder(inOrderList);
		Assertions.assertTrue(tasks.containsAll(inOrderList));
	}
	@Test
	public void testSearch1() throws TaskException {
		Task task1 = new Task(2222,123, "Test P3",1,"P3", "28/11/2021", "Test din lab1-8");
		List<Task> tasks = new ArrayList<Task>(List.of(task1,
				new Task(2222,5462, "Test SO1",2,"SO1", "10/12/2021", "Test din lab1-8"),
				new Task(2222,6835, "Tema SO1",2,"SO1", "09/12/2021", "Tema din lab8"),
				new Task(2222,5687, "Tema BD1",1,"BD1", "22/11/2021", "Tema din lab6"),
				new Task(2222,56862, "Test BD1",1,"BD1", "25/11/2021", "Test din lab1-8")
			));
		RedBlackTree redblack = new RedBlackTree();
		for(Task task : tasks) {
			redblack.insert(task);
		}
		Assertions.assertNotNull(redblack.search(task1));
	}
	@Test
	public void testSearch2() throws TaskException {
		Task task1 = new Task(2222,123, "Test P3",1,"P3", "28/11/2021", "Test din lab1-8");
		List<Task> tasks = new ArrayList<Task>(List.of(
				new Task(2222,5462, "Test SO1",2,"SO1", "10/12/2021", "Test din lab1-8"),
				new Task(2222,6835, "Tema SO1",2,"SO1", "09/12/2021", "Tema din lab8"),
				new Task(2222,5687, "Tema BD1",1,"BD1", "22/11/2021", "Tema din lab6"),
				new Task(2222,56862, "Test BD1",1,"BD1", "25/11/2021", "Test din lab1-8")
			));
		RedBlackTree redblack = new RedBlackTree();
		for(Task task : tasks) {
			redblack.insert(task);
		}
		Assertions.assertNull(redblack.search(task1));
	}
	@Test
	public void testSearchIf1() throws TaskException {
		Task task1 = new Task(2222,123, "Test P3",1,"P3", "28/11/2021", "Test din lab1-8");
		List<Task> tasks = new ArrayList<Task>(List.of(
				new Task(2222,5462, "Test SO1",2,"SO1", "10/12/2021", "Test din lab1-8"),
				new Task(2222,6835, "Tema SO1",2,"SO1", "09/12/2021", "Tema din lab8"),
				new Task(2222,5687, "Tema BD1",1,"BD1", "22/11/2021", "Tema din lab6"), 
				new Task(2222,56862, "Test BD1",1,"BD1", "25/11/2021", "Test din lab1-8")
			));
		RedBlackTree redblack = new RedBlackTree();
		for(Task task : tasks) {
			redblack.insert(task);
		}
		redblack.insert(task1);
		Assertions.assertTrue(redblack.searchIf(task1.getTaskid()));
	}
	@Test
	public void testSearchIf2() throws TaskException {
		Task task1 = new Task(2222,123, "Test P3",1,"P3", "28/11/2021", "Test din lab1-8");
		List<Task> tasks = new ArrayList<Task>(List.of(
				new Task(2222,5462, "Test SO1",2,"SO1", "10/12/2021", "Test din lab1-8"),
				new Task(2222,6835, "Tema SO1",2,"SO1", "09/12/2021", "Tema din lab8"),
				new Task(2222,5687, "Tema BD1",1,"BD1", "22/11/2021", "Tema din lab6"),
				new Task(2222,56862, "Test BD1",1,"BD1", "25/11/2021", "Test din lab1-8")
			));
		RedBlackTree redblack = new RedBlackTree();
		for(Task task : tasks) {
			redblack.insert(task);
		}
		Assertions.assertFalse(redblack.searchIf(task1.getTaskid()));
	}
	@Test
	public void testTask1() throws TaskException{
		Assertions.assertThrows(TaskException.class, () -> {
			new Task(2222,123,"",1,"P3", "28/12/2021","Test din");
		});
	}
	@Test
	public void testTask2() throws TaskException{
		Assertions.assertThrows(TaskException.class, () -> {
			new Task(2222,123,"Test",1, "P3","28/48/2021","Test din");
		});
	}
	@Test
	public void testTask3() throws TaskException{
		Assertions.assertThrows(TaskException.class, () -> {
			new Task(2222,123,"Test",1, "P3","28/10/5021","Test din");
		});
	}
	@Test
	public void testTask4() throws TaskException{
		Assertions.assertThrows(TaskException.class, () -> {
			new Task(2222,123,"Test",1,"P3", "58/8/2021","Test din");
		});
	}
	@Test
	public void testTask5() throws TaskException{
		Assertions.assertThrows(TaskException.class, () -> {
			new Task(2222,123,"Test",1,"P3", "28/4821","Test din");
		});
	}
	@Test
	public void testTask6() throws TaskException{
		Assertions.assertThrows(TaskException.class, () -> {
			new Task(2222,123,"Test",1, "P3","avas","Test din");
		});
	}
	@Test
	public void testTask7() throws TaskException{
		Assertions.assertThrows(TaskException.class, () -> {
			new Task(2222,123,"Test",1,"P3", "","Test din");
		});
	}
	@Test
	public void testTask8() throws TaskException{
		Assertions.assertThrows(TaskException.class, () -> {
			new Task(2222,123,"Test",1, "P3","28/12/2021","");
		});
	}
}
