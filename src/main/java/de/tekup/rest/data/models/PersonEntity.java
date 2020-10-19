package de.tekup.rest.data.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "Person")
public class PersonEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name =  "persoName", length = 50, nullable = false, unique = true)
	private String name;
	private LocalDate dateOfBirth;
	
	@OneToOne
	@JsonIgnore
	private AddressEntity address;
	
	// Relation One To Many
	private List<TelephoneNumberEntity> phones;
	

}
