package de.tekup.rest.data.models;

// table name must be Student
// Turn this to Entity and  create the CRUD of this Entity
public class StudentEntity {
	
	private long id;
	
	private String name; // name not null, with 60 chars
	private int code; // unique code over 4 numbers
	private String classRoom;

}
