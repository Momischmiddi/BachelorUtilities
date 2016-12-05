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
}
