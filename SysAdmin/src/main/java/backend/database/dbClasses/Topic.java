package backend.database.dbClasses;

import java.util.ArrayList;

public class Topic {

	private int ID;
	private String title;
	private String description;
	private Author author;
	private Grade grade;
	private ArrayList<Date> date;
	private ExpertOpinion expertOpinion;
	private SecondOpinion secondOpinion;
	private int isFinished;
	
	public Topic() {}
	
	/**
	 * Copy-Constructor
	 * @param topic copy
	 */
	public Topic (Topic topic) {
		title = topic.getTitle();
		description = topic.getDescription();
		author = topic.getAuthor();
		grade = topic.getGrade();
		date = topic.getDate();
		expertOpinion = topic.getExpertOpinion();
		secondOpinion = topic.getSecondOpinion();
		isFinished = topic.isFinished();
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public Grade getGrade() {
		return grade;
	}
	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	public ArrayList<Date> getDate() {
		return date;
	}
	public void setDate(ArrayList<Date> date) {
		this.date = date;
	}
	public ExpertOpinion getExpertOpinion() {
		return expertOpinion;
	}
	public void setExpertOpinion(ExpertOpinion expertOpinion) {
		this.expertOpinion = expertOpinion;
	}
	public SecondOpinion getSecondOpinion() {
		return secondOpinion;
	}
	public void setSecondOpinion(SecondOpinion secondOpinion) {
		this.secondOpinion = secondOpinion;
	}
	public int isFinished() {
		return isFinished;
	}
	public void setFinished(int isFinished) {
		this.isFinished = isFinished;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ID: " + ID + "\n");
		builder.append("Title: " + title + "\n");
		builder.append("Desrciption: " + description + "\n");
		if (null != author) {
			builder.append("Author: " + author.getName() + "\n");
		}
		builder.append("Grade: " + grade + "\n");
		builder.append("Date: " + date + "\n");
		if (null != expertOpinion) { 
			builder.append("Expertopinion: " + expertOpinion.getOpinion() + "\n");
		}
		if (null != secondOpinion) { 
			builder.append("Second-opinion: " + secondOpinion.getOpinion() + "\n");
		}
		boolean boolIsFinished = (isFinished == 1) ? true : false;
		builder.append("Stand: " + ((boolIsFinished) ? "Abgeschlossen" : "in Bearbeitung"));
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((expertOpinion == null) ? 0 : expertOpinion.hashCode());
		result = prime * result + ((grade == null) ? 0 : grade.hashCode());
		result = prime * result + isFinished;
		result = prime * result + ((secondOpinion == null) ? 0 : secondOpinion.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Topic other = (Topic) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (expertOpinion == null) {
			if (other.expertOpinion != null)
				return false;
		} else if (!expertOpinion.equals(other.expertOpinion))
			return false;
		if (grade == null) {
			if (other.grade != null)
				return false;
		} else if (!grade.equals(other.grade))
			return false;
		if (isFinished != other.isFinished)
			return false;
		if (secondOpinion == null) {
			if (other.secondOpinion != null)
				return false;
		} else if (!secondOpinion.equals(other.secondOpinion))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
}
