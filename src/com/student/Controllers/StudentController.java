package com.student.Controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.neo4j.driver.v1.exceptions.Neo4jException;

import com.student.DAOs.MySQL_DAO;
import com.student.DAOs.Neo4j_DAO;
import com.student.Models.Course;
import com.student.Models.FullStudentDetails;
import com.student.Models.Student;

@SessionScoped
@ManagedBean
public class StudentController {
	
//class variables
	
	private ArrayList<Student> students;
	
	private ArrayList<Course> courses;
	
	private ArrayList<FullStudentDetails> fullDetails;
	
	private String studentId;
	
	private String courseId;
	
	private String studentName;
	
	private String studentAddress;
	
	private String selectedName;
	
	private MySQL_DAO dao;
	
	private Neo4j_DAO neoDao;
	
//------------------------------------------------	

//constructor
	
	public StudentController() throws Exception{
		super();
		
	}//studentController
	
//------------------------------------------------	

//returnNeo4jError - called if no neo4j DB connection
	
	private String returnNeo4jError(String name){
		
		FacesMessage error = new FacesMessage("Warning: Student " + name + " has not been added to Neo4j DB, as it is offline");
		
		FacesContext.getCurrentInstance().addMessage(null, error);
		
		return "ManageStudents.xhtml";
	
	}//returnNeo4jError
	
//------------------------------------------------	

//returnSQLError - called if no MySQL DB connection
	
	public String returnSQLError(){
		
		FacesMessage error = new FacesMessage("Error: Cannot connect to MySQL Database");
		
		FacesContext.getCurrentInstance().addMessage(null, error);
		
		return "ManageStudents.xhtml";
	
	}//returnSQLError

//------------------------------------------------	

//loadStudents - loads Students from MySQL DB
	
	public void loadStudents(){
	
		this.students = new ArrayList<>();
	
	//try for MySQL DB connection
		try {
			
			this.dao = new MySQL_DAO();
			
			this.students = this.dao.loadStudents();
			
		} catch (Exception e) {
			
		//call error method if no connection
				
			returnSQLError();
		
		}//catch
		
	//try neo4j connect
		try {
			
			this.neoDao = new Neo4j_DAO();
			
		} catch (Exception e) {
			
		//return if can't connect	
			return;
		
		}//catch
		
		//System.out.println("Students added to list");
	
	}//loadStudents
	
//------------------------------------------------	

//get Student - return student list
	
	public ArrayList<Student> getStudents() {
		return students;
	
	}//getStudents

//------------------------------------------------	

//addStudent - add Student to MySQL & Neo4j DB
	
	public String addStudent(){
	//error message var
		FacesMessage message;
		
	//try for Neo4j db connection
		if(this.neoDao == null){
			
			return returnNeo4jError(this.studentName);
		}//if
		
	//check MySQL DB object has been created
		if(this.dao != null){
			
			//set list
			this.courses = dao.loadCourses();
		}
		else
		{
			return "ManageStudents.xhtml";
		
		}//if
		
	//loop through list of students
		for(Student s: this.students){
		
		//check for valid student id
			if(s.getStudentId().equalsIgnoreCase(this.studentId)){
				
			//error message
				message = new FacesMessage("Error: Duplicate entry '" + s.getStudentId() + "' for key 'PRIMARY'");
				
				FacesContext.getCurrentInstance().addMessage(null, message);
				
				return "AddStudent.xhtml";
				
			}//if
			
		//check for valid student name
			if(s.getStudentName().equalsIgnoreCase(this.studentName)){
				
			//error message
				message = new FacesMessage("Error: Duplicate entry '" + s.getStudentName() + "' for key 'name'");
				
				FacesContext.getCurrentInstance().addMessage(null, message);
				
				return "AddStudent.xhtml";
				
			}//if
			
			//System.out.println("controller student course id: " + s.getCourseId());
			
		}//for
		
	//loop through list of courses
		for(Course c: this.courses){
			
		//check for valid course id
			if(c.getCourseId().equalsIgnoreCase(this.courseId)){
				
			//add to MySQL
				dao.addStudent(this.studentId, this.studentName, this.courseId.toUpperCase(), this.studentAddress);
				
			//add to neo4j
				neoDao.addStudentNode(this.studentName, this.studentAddress);
				
				//exit loop after adding
				break;
			}
			else
			{
			//error message if course id not valid
				message = new FacesMessage("Error: Course " + this.courseId + " does not exist");
				
				FacesContext.getCurrentInstance().addMessage(null, message);
				
				return "AddStudent.xhtml";
			
			}//if
			
		}//for
		
		return "ManageStudents.xhtml";
	
	}//addCourse
	
//------------------------------------------------	

//setStudentDetails - sets selected student name returns student details html page
	
	public String setStudentDetails(String studentName){
		
		this.selectedName = studentName;
		
		return "FullStudentDetails.xhtml";
	
	}//setStudentDetails

//------------------------------------------------	

//loadFullStudentDetails - loads full student detail list from MySQL query
	
	public void loadFullStudentDetails(){
		
	//populate list from DB object
		this.fullDetails = dao.loadFullStudentDetails(this.selectedName);
		
		//System.out.println("Full Student details loaded");
	
	}//loadFullStudentDetails
	
//------------------------------------------------	

//getFullStudentDetails - return student details list
	
	public ArrayList<FullStudentDetails> getFullStudentDetails(){
		return fullDetails;
	
	}//getFullStudentDetails
	
//------------------------------------------------	

//deleteStudent - deletes student from MySQL & Neo4j DBs	
	
	public String deleteStudent(String name, String studentId){
		
		boolean canDeleteNode = false;
		
		FacesMessage message;
		
	//check neo4j connection
		if(this.neoDao != null){
			
		//call method to see if can delete student
			canDeleteNode = this.neoDao.canDeleteNode(name);
		}
		else
		{
		//error if not connected
			return returnNeo4jError(name);
		
		}//if
		
	//if true
		if(canDeleteNode){
			
		//delete from neo4j
			neoDao.deleteStudent(name);
			
		//delete from MySQL
			dao.deleteStudent(studentId);
		}
		else
		{
		//error message
			message = new FacesMessage("Error: Student: " + name + " has not been deleted from any database as he/she has relationships in Neo4j");
			
			FacesContext.getCurrentInstance().addMessage(null, message);
			
		}//if
		
		return "ManageStudents.xhtml";
	
	}//deleteStudent
	
//------------------------------------------------	

//variable Getters & Setters
	
	public String getStudentId() {
		return studentId;
	
	}//get

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	
	}//set

	public String getCourseId() {
		return courseId;
	
	}//get

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	
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

}//studentController
