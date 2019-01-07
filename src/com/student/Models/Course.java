package com.student.Models;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
public class Course {
	
//class Variables
	
	private String courseId;
	
	private String courseName;
	
	private int courseDuration;

//------------------------------------------------	

//constructors
	
	public Course(){
		
	
	}//course
	

	public Course(String id, String name, int duration){
		this.courseId = id;
		this.courseName = name;
		this.courseDuration = duration;
	
	}//course

//------------------------------------------------	

//variable Getters & Setters
	
	public String getCourseId() {
		return courseId;
	
	}//get

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	
	}//set

	public String getCourseName() {
		return courseName;
	
	}//get

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	
	}//set

	public int getCourseDuration() {
		return courseDuration;
	
	}//get

	public void setCourseDuration(int courseDuration) {
		this.courseDuration = courseDuration;
	
	}//set

//------------------------------------------------	
	
}//course
