package com.student.Controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.student.DAOs.MySQL_DAO;
import com.student.Models.Course;
import com.student.Models.CourseStudentDetails;


@SessionScoped
@ManagedBean
public class CourseController {

//class variables
	
	ArrayList<Course> courses;
	
	ArrayList<CourseStudentDetails> students;
	
	MySQL_DAO dao;
	
	String courseID;
	
	String courseName;
	
	int courseDuration;
	
	String selectedID;
	
//------------------------------------------------	

//constructor
	
	public CourseController() throws Exception{
		super();
	}
		
//------------------------------------------------	

//class methods
	
//loadCourses - loads courses from mysql database
	
	public void loadCourses(){
		
		this.courses = new ArrayList<>();
		
		//try for database connection
		try {
			
			this.dao = new MySQL_DAO();
			
			this.courses = this.dao.loadCourses();
		
		} catch (Exception e) {
			
			//call method if connection not available
			returnSQLError();
		}
		
		//System.out.println("courses added to list...");
	
	}//loadCourses
	
//------------------------------------------------	
	
//getCourses - returns list of courses
	
	public ArrayList<Course> getCourses(){
		return courses;
	
	}//getCourses
	
//------------------------------------------------	

//returnSQLError - called if failed DB connection
	
	public String returnSQLError(){
		
	//output error message to html page
		FacesMessage error = new FacesMessage("Error: Cannot connect to MySQL Database");
		
		FacesContext.getCurrentInstance().addMessage(null, error);
		
		return "ManageCourses.xhtml";
	
	}//returnSQLError
	
//------------------------------------------------	

//addCourse - adds course to MySQL DB returns html page

	public String addCourse(){
		
//check for DB connection
		if(this.dao == null){
			return "ManageCourses.xhtml";
		
		}//if
		

		for(Course c: this.courses){
			
		//check course doesn't exist
			if(c.getCourseId().equalsIgnoreCase(this.courseID)){
		
			//error message
				FacesMessage message = new FacesMessage("Error Course ID " + c.getCourseId() + " already exists");
				
				FacesContext.getCurrentInstance().addMessage(null, message);
				
				return "AddCourse.xhtml";
				
			}//if
			
		}//for
		
		
	//add course to MySQL DB
		dao.addCourse(this.courseID.toUpperCase(), this.courseName, this.courseDuration);
		
		
		return "ManageCourses.xhtml";
	
	}//addCourse
	
//------------------------------------------------	

//getStudentDetails - sets student id and returns html page
	
	public String getStudentDetails(String id){
	
	//set selected id
		this.selectedID = id;
		
		//System.out.println("Selected id is: " + this.selectedID);
		
		return "StudentDetails.xhtml";
	
	}//getStudentDetails

//------------------------------------------------	

//loadStudentDetails - creates student details from DB query
	
	public void loadStudentDetails() throws SQLException{

		this.students = new ArrayList<>();
		
//call DB object method
		this.students = dao.loadCourseStudentDetails(this.selectedID);
		
		//System.out.println("Student details loaded");
	
	}//loadStudentDetails

//------------------------------------------------	

//studentCourseDetails - returns student course list
	
	public ArrayList<CourseStudentDetails> studentCourseDetails(){
		return students;
	
	}//studentCourseDetails
	
//------------------------------------------------	

//setDelteCourse - takes course id from course passes to DB for delete
	
	public String setDeleteCourse(String courseId){

//set list from DB object
		this.students = dao.loadCourseStudentDetails(courseId);
		
		//System.out.println("Delete course id: " + courseId + "\nstudent 0: " + this.studentCourseDetails().size());
		
		for(CourseStudentDetails student: this.students){
			
		//check if course has students
			if(student.getCourseId().equalsIgnoreCase(courseId)){
				
				FacesMessage message = new FacesMessage("Error: Cannot Delete Course: " + student.getCourseId() + " as there are associated Students");
				
				FacesContext.getCurrentInstance().addMessage(null, message);
			
				return "ManageCourses.xhtml";
			}//if
			
		}//for
		
	//delete if no students in course
		dao.deleteCourse(courseId);
		
		
		return "ManageCourses.xhtml";
		
	}//setDeleteCourse

//------------------------------------------------	
	
//variables Getters & Setters	
	
	public String getCourseID() {
		return courseID;
	}


	public void setCourseID(String courseID) {
		this.courseID = courseID;
	
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
	
}//CourseController
