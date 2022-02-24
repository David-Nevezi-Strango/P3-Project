package Proiect;

import java.util.List;
/**
 * The Student class used to facilitate users who are students
 * 
 * @author Nevezi-Strango David
 *
 */
public class Student extends Person {
	/**
	 * Year of Specialization
	 */
	int year;
	/**
	 * Specialization
	 */
	String specialization;

	/**
	 * Constructor for Students
	 * 
	 * @param uid User's Identification number
	 * @param name User's name
	 * @param faculty User's Faculty
	 * @param tasks User's list of tasks
	 * @param year User's Year of Specialization
	 * @param specialization User's Specialization
	 */
	public Student(int uid, String name, String faculty, RedBlackTree tasks, int year, String specialization) {
		super(uid, name, faculty, tasks);
		this.year = year;
		this.specialization = specialization;
	}

	@Override
	public String toString() {
		return "Student [uid=" + uID + ", name=" + name
				+ ", faculty=" + faculty + ", year=" + year + ", specialization=" + specialization + ", tasks=" + tasks + "]";
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the specialization
	 */
	public String getSpecialization() {
		return specialization;
	}

	/**
	 * @param specialization the specialization to set
	 */
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}


	
}
