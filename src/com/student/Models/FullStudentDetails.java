package com.student.Models;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
public class FullStudentDetails {

//class variables
	
	private String studentId;
	
	private String studentName;
	
	private String courseId;
	
	private String courseName;
	
	private int courseDuration;
	
//------------------------------------------------	

//constructors
	
	public FullStudentDetails(){
		
	
	}//fullStudentDetails

	
	public FullStudentDetails(String sid, String sname, String cid, String cname, int duration){
		
		this.studentId = sid;
		this.studentName = sname;
		this.courseId = cid;
		this.courseName = cname;
		this.courseDuration = duration;
	
	}//fullStudentDetails

//------------------------------------------------	

//variables Getters & Setters
	
	public String getStudentId() {
		return studentId;
	
	}//get


	public void setStudentId(String studentId) {
		this.studentId = studentId;
	
	}//set


	public String getStudentName() {
		return studentName;
	
	}//get


	public void setStudentName(String studentName) {
		this.studentName = studentName;
	
	}//set


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

}//fullStudentDetails
