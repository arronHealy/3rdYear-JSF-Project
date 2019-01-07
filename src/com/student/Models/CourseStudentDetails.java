package com.student.Models;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
public class CourseStudentDetails {

//class variables
	
	private String courseId;
	
	private String courseName;
	
	private int courseDuration;
	
	private String studentName;
	
	private String studentAddress;
	
//------------------------------------------------	

//constructors
	
	public CourseStudentDetails(){
		
	
	}//courseStudentDetails

	
	public CourseStudentDetails(String cid, String cname, int duration, String sname, String address){
		this.courseId = cid;
		this.courseName = cname;
		this.courseDuration = duration;
		this.studentName = sname;
		this.studentAddress = address;
	
	}//courseStudentDetails

//------------------------------------------------	

//variables Getters & Setters
	
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

	
	public String getStudentName() {
		return studentName;
	
	}//get


	public void setStudentName(String studentName) {
		this.studentName = studentName;
	
	}//set
	
	
	public String getStudentAddress() {
		return studentAddress;
	
	}//get


	public void setStudentAddress(String studentAddress) {
		this.studentAddress = studentAddress;
	
	}//set
	
//------------------------------------------------	

}//courseStudentDetails
