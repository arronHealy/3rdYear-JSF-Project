package com.student.DAOs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.student.Models.Course;
import com.student.Models.CourseStudentDetails;
import com.student.Models.FullStudentDetails;
import com.student.Models.Student;


public class MySQL_DAO {
	
//class variables
	
	private DataSource mysqlData;
	
	private ArrayList<Course> courses;
	
	private ArrayList<Student> students;
	
	private ArrayList<CourseStudentDetails> studentDetails;
	
	private ArrayList<FullStudentDetails> fullDetails;
	
	public Connection conn;

//------------------------------------------------	

//contructor - throws Exception
	
	public MySQL_DAO() throws Exception{
		
		Context context = new InitialContext();
		String jndiName = "java:comp/env/jdbc/collegeDB";
		mysqlData = (DataSource)context.lookup(jndiName);
		
		conn = mysqlData.getConnection();
		
	}//dao
	
//------------------------------------------------	

//loadCourses - query DB for all courses
	
	public ArrayList<Course> loadCourses(){
		
		courses = new ArrayList<>();
	
	//try populate list
		try {
			
			Statement myStmt = conn.createStatement();
			
			String query = "select * from course";
			
			ResultSet rs = myStmt.executeQuery(query);
			
			while(rs.next()){
				
				String id = rs.getString("cid");
				
				String name = rs.getString("cname");
				
				int duration = rs.getInt("duration");
				
				this.courses.add(new Course(id, name, duration));
				
				System.out.println("course id: " + id + " name: " + name);
				
			}//while
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		
		}//catch
		
		//System.out.println("items added");
		
		return this.courses;
		
	}//populateList
	
//------------------------------------------------	

//addCourse - add course to DB
	
	public void addCourse(String id, String name, int duration){
	
	//try and add to DB
		try {
			
			Statement myStmt = conn.createStatement();
			
			String query = "insert into course values('" + id + "', " + "'" + name + "', " + "'" + duration + "')";
			
			myStmt.executeUpdate(query);
			
			//System.out.println("Course added to mysql database");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		
		}//catch
		
	}//addCourse
	
//------------------------------------------------	

//loadCourseStudentDetails - loads db data from course & student table
	
	public ArrayList<CourseStudentDetails> loadCourseStudentDetails(String id){
		
		studentDetails = new ArrayList<>();
	
	//query MySQL for table join
		try {
			Statement stmt = conn.createStatement();
			
			String query = "select c.cid, c.cname, c.duration, s.name, s.address from course c inner join student s on c.cid = s.cid and c.cid ='" + id + "'";
			
			ResultSet rs = stmt.executeQuery(query);
		
		//populate list
			while(rs.next()){
				
				String cid = rs.getString("cid");
				
				String cname = rs.getString("cname");
				
				int duration = rs.getInt("duration");
				
				String name = rs.getString("name");
				
				String address = rs.getString("address");
				
				studentDetails.add(new CourseStudentDetails(cid, cname, duration, name, address));
				
				//System.out.println("course id: " + cid + " name: " + name);
				
			}//while
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		}//catch
		
		return studentDetails;
	
	}//loadCourseStudentDetails

//------------------------------------------------	

//addStudent - insert student to DB
	
	public void addStudent(String sid, String name, String cid, String address){
		
		try {
			
			Statement myStmt = conn.createStatement();
			
			String query = "insert into student values('" + sid + "', " + "'" + cid + "', " + "'" + name + "', " + "'" + address + "')";
			
			myStmt.executeUpdate(query);
			
			System.out.println("Student added to mysql database");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}//addStudent
	
//------------------------------------------------	

//loadStudents - load Student objects to list
	
	public ArrayList<Student> loadStudents(){
		
		students = new ArrayList<>();
		
		try {
			
			Statement stmt = conn.createStatement();
			
			String query = "select * from student";
			
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
			
				String sid = rs.getString("sid");
				
				String cid = rs.getString("cid");
				
				String name = rs.getString("name");
				
				String address = rs.getString("address");
				
				students.add(new Student(sid, cid, name, address));
				
				//System.out.println("Student id: " + sid + " name: " + name + " course id: " + cid);
			}//while
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return students;
	
	}//loadStudents

//------------------------------------------------	

//loadFullStudentDetails - load list of Full Student Detail objects

	public ArrayList<FullStudentDetails> loadFullStudentDetails(String studentName){
		
		fullDetails = new ArrayList<>();
		
	//try for table join
		try {
			
			Statement stmt = conn.createStatement();
			
			String query = "select s.sid, s.name, c.cid, c.cname, c.duration from student s inner join course c on c.cid = s.cid and s.name ='" + studentName + "'";
			
			ResultSet rs = stmt.executeQuery(query);
			
		//loop and add results to list
			while(rs.next()){
				
				String sid = rs.getString("sid");
				
				String sname = rs.getString("name");
				
				String cid = rs.getString("cid");
				
				String cname = rs.getString("cname");
				
				int duration = rs.getInt("duration");
				
				fullDetails.add(new FullStudentDetails(sid, sname, cid, cname, duration));
				
				//System.out.println("Student Full Details added: " + sid);
			
			}//while
		
		} catch (SQLException e) {
			e.printStackTrace();
		
		}//catch
		
		return fullDetails;
	
	}//loadFullStudentDetails
	
//------------------------------------------------	

//deleteCourse - delete course from DB
	
	public void deleteCourse(String id){
		
		try {
			
			Statement stmt = conn.createStatement();
			
			String sql = "Delete from course where cid like '" + id + "'";
			
			stmt.executeUpdate(sql);
			
			//System.out.println("Course deleted: " + id);
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		}//catch
		
	}//deleteCourse
	
//------------------------------------------------	

//deleteStudent - delete Student from DB
	
	public void deleteStudent(String studentId){
		
		try {
			
			Statement stmt = conn.createStatement();
		
			String sql = "delete from student where sid like '" + studentId + "'";
			
			stmt.executeUpdate(sql);
			
			System.out.println("Student " + studentId + " deleted");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}//deleteStudent
	
//------------------------------------------------	

}//MySQL_DAO
