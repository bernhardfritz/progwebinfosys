package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	
	public static final String ACCOUNT = "Account";
	
	public static final String BANKBOOK = "Bankbook";
	
	public static final List<String> TRANSFER_FROM_ACCOUNT_TYPES  = new ArrayList<>(Arrays.asList(new String[] { AccountType.ACCOUNT }));

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