package model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "AccountType")
public class AccountType implements Serializable {

	private static final long serialVersionUID = -156422491287397310L;

	@Id
	@Column(name = "idAccountType")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "label")
	private String label;
	
	@Column(name = "createTimestamp")
	private Timestamp createTimestamp;
	
	public AccountType() {}
	
	public AccountType(String label) {
		this.label = label;
	}
}