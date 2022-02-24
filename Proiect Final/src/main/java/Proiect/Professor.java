package Proiect;

import java.util.List;
/**
 * The Professor class used to facilitate users who are professors
 * 
 * @author Nevezi-Strango David
 *
 */
public class Professor extends Person {
	/**
	 * The Professor's grade
	 */
	String grade;

	/**
	 * Constructor for Professors
	 * 
	 * @param uid User's Identification number
	 * @param name User's name
	 * @param faculty User's Faculty
	 * @param tasks User's list of tasks
	 * @param grade The Professor's grade
	 */
	public Professor(int uid, String name, String faculty, RedBlackTree tasks, String grade) {
		super(uid, name, faculty, tasks);
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "Professor [uid=" + uID + ", name=" + name + ", faculty=" + faculty + ", grade=" + grade + ", tasks="
				+ tasks + "]";
	}

	/**
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}

	/**
	 * @param grade the grade to set
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}

	
	
}
