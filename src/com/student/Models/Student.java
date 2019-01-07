package com.student.Models;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
public class Student {

//class variables
	
	private String studentId;
	
	private String courseId;
	
	private String studentName;
	
	private String studentAddress;
	
//------------------------------------------------	

//constructors
	
	public Student(){
		
	
	}//student
	
	
	public Student(String sid, String cid, String name, String address){
		this.studentId = sid;
		this.courseId = cid;
		this.studentName = name;
		this.studentAddress = address;
	
	}//student

//------------------------------------------------	

//variable Getters & Setters
	
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


	public String getStudentAddress() {
		return studentAddress;
	
	}//get


	public void setStudentAddress(String studentAddress) {
		this.studentAddress = studentAddress;
	
	}//set

	
	public String getCourseId() {
		return courseId;
	
	}//get

	
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	
	}//set

//------------------------------------------------	
	
}//student
