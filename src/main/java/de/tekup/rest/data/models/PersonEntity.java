package de.tekup.rest.data.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.modelmapper.ModelMapper;

import de.tekup.rest.data.dto.AddressRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

// class save to DataBase (Serialize)
@Data
@Entity
@Table(name = "Person")
@EqualsAndHashCode(exclude = {"address","phones","games"})
@ToString(exclude = {"address","phones","games"})
public class PersonEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name =  "personName", length = 50, nullable = false, unique = true)
	private String name;
	private LocalDate dateOfBirth;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	private AddressEntity address;
	
	@OneToMany(mappedBy = "person",cascade = CascadeType.REMOVE)
	private List<TelephoneNumberEntity> phones;
	
	@ManyToMany(mappedBy = "persons",  cascade = CascadeType.REMOVE)
	private List<GamesEntity> games;
	
	public int getAge() {
		return LocalDate.now().getYear()-this.getDateOfBirth().getYear();
	}
	
	public String getFullAddress() {
		return address.getNumber() + " "+ address.getStreet()+", "+address.getCity()+".";
	}
	
	public void setAddressReq(AddressRequest address) {
		
		ModelMapper mapper = new ModelMapper();
		this.address = mapper.map(address, AddressEntity.class);
	}
	
	public void setAddress(AddressEntity address) {
		
		this.address = address;
	}

	
}
