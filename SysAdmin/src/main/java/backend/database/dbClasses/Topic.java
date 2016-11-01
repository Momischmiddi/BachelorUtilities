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
	
	
}
