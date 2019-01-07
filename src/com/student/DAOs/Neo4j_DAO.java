package com.student.DAOs;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import org.neo4j.driver.v1.exceptions.Neo4jException;

import static org.neo4j.driver.v1.Values.parameters;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Neo4j_DAO {

//class variables
	
	private Driver driver;
	
//------------------------------------------------	

//constructor

	public Neo4j_DAO() throws Exception{
		
		driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "neo4jdb")); 
	
	}//Neo4j_DAO

//------------------------------------------------	
	
//addStudentNode - add Student node to Neo4j DB
	
	public void addStudentNode(String name, String address){
		
		Session session = driver.session();
		
	//run query
		session.writeTransaction(new TransactionWork<String>() {

		//override method
			@Override
			public String execute(Transaction tx) {
				
				tx.run("CREATE (:STUDENT {name: $name, address: $address})", parameters("name", name, "address", address));
				
				
				return null;
			}
		
		});
		
	}//addStudent
	
//------------------------------------------------	

//canDeleteNode - checks to see if node has relationships
	
	public boolean canDeleteNode(String name){
		
		Session session = driver.session();
		
		StatementResult result = session.run("match(s:STUDENT{name: $name})-[:KNOWS]->(student) return student.name", parameters("name", name));
		
	//if result returns false no out going relationship
		
		if(result.hasNext() == false){
			//System.out.println("No outgoing relationship returned null");
			
			result = session.run("match(s:STUDENT{name: $name})<-[:KNOWS]-(student) return student.name", parameters("name", name));
		
		//if result false no incoming relationship
			
			if(result.hasNext() == false){
				//System.out.println("No incoming relationship can delete");
			
			//node can be deleted
				return true;
			}//if
			
		}//if
		
		return false;
	
	}//canDeleteNode
	
//------------------------------------------------	

//deleteStudent - delete student node from Neo4j DB
	
	public void deleteStudent(String name){
		
		Session session = driver.session();
		
	//run query
		session.writeTransaction(new TransactionWork<String>() {

		//override method
			@Override
			public String execute(Transaction tx) {
				
				tx.run("match (s:STUDENT {name: $name}) delete s", parameters("name", name));
				
				
				return null;
			}
		
		});
		
	}//deleteStudent
	
//------------------------------------------------	

	
}//Neo4j_DAO
