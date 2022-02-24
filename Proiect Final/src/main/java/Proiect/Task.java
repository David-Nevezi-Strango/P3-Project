package Proiect;

/**
 * Task class, used to facilitate tasks for students or professors
 * 
 * @author Nevezi-Strango David
 *
 */
public class Task {
	/**
	 * Task Identification Number
	 */
	int taskID;
	/**
	 * Task senderID
	 */
	int senderID;
	/**
	 * Task Status
	 */
	int status;
	/**
	 * Task Title
	 */
	String title;
	/**
	 * Task text
	 */
	String text;
	/**
	 * Task subject
	 */
	String subject;
	/**
	 * Task deadline
	 */
	CustomDate deadline;
	
	/**
	 * Constructor for the class Task
	 * @param senderID The ID of the task sender
	 * @param taskid Task Identification Number
	 * @param title Task's Title
	 * @param status Task's Status
	 * @param text Task's Text
	 * @param subject Task's Subject
	 * @param deadline Task's Deadline
	 * @throws TaskException Exception thrown when invalid title, text or deadline has been typed in
	 */
	public Task(int senderID, int taskid, String title, int status,  String subject, String deadline, String text) throws TaskException {
		this.senderID = senderID;
		this.taskID = taskid;
		this.status = status;
		if(subject.equals("")) {
			throw new TaskException(TaskException.INVALID_SUBJECT);
		} else {
			this.subject = subject;
		}
		if(title.equals("")) {
			throw new TaskException(TaskException.INVALID_TITLE);
		} else {
			this.title = title;

		}
		if(text.equals("")) {
			throw new TaskException(TaskException.INVALID_TEXT);
		} else {
			this.text = text;
		}
		if(deadline.equals("")) {
			throw new TaskException(TaskException.INVALID_DEADLINE);
		} else {
			this.deadline = new CustomDate(deadline);
		}
	}
	
	
	/**
	 * Method that returns only the Title of the task;
	 * @return Title
	 */
	@Override
	public String toString() {
		//return "Task [taskid=" + taskID + "\n status=" + status + "\n title=" + title + "\n text=" + text + "\n deadline="
				//+ deadline + "]";
		return title;
	}
	/**
	 * Method that returns all information relevant to the end-User
	 * @return String composed of Title, Status in String form, Deadline and Text
	 */
	public String toStringAll() {
		String statusString = new String();
		int i = this.status;
			if(i == 1) {
				statusString = "Open";
			}else if(i == 2) {
				statusString = "Accepted";
			}else if(i == 3) {
				statusString = "Blocked";
			}else if(i == 4) {
				statusString = "Done";
			}
		return  "Title: " + title +"\nStatus: " + statusString  + "\nDeadline: "+ deadline.format("dd. mm. yyyy.") +"\n\n" + text;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 * @throws TaskException if subject is an empty string
	 */
	public void setSubject(String subject) throws TaskException {
		if(!subject.equals("")) {
			this.subject = subject;
		} else {
			throw new TaskException(TaskException.INVALID_SUBJECT);
		}
	}
	
	/**
	 * @return the taskid
	 */
	public int getTaskid() {
		return taskID;
	}

	/**
	 * @param taskid the taskid to set
	 */
	public void setTaskid(int taskid) {
		this.taskID = taskid;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 * @throws TaskException if the title is an empty string
	 */
	public void setTitle(String title) throws TaskException {
		if(!title.equals("")) {
			this.title = title;
		} else {
			throw new TaskException(TaskException.INVALID_TITLE);
		}
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 * @throws TaskException if the text is an empty string
	 */
	public void setText(String text) throws TaskException {
		if(!text.equals("")) {
			this.text = text;
		} else {
			throw new TaskException(TaskException.INVALID_TEXT);
		}
	}

	/**
	 * @return the deadline
	 */
	public CustomDate getDeadline() {
		return deadline;
	}

	/**
	 * @param deadline the deadline to set
	 */
	public void setDeadline(CustomDate deadline) {
		this.deadline = deadline;
	}



	/**
	 * @return the senderID
	 */
	public int getSenderID() {
		return senderID;
	}



	/**
	 * @param senderID the senderID to set
	 */
	public void setSenderID(int senderID) {
		this.senderID = senderID;
	}
	
	
}
