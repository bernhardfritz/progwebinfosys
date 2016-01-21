package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "Account")
public class Account implements Serializable {

	private static final long serialVersionUID = -2187053659640625361L;
	
	@Id
	@Column(name = "idAccount")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "accountType")
	private AccountType accountType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner")
	private User owner;
	
	@Column(name = "accountNumber")
	private String accountNumber;
	
	@Column(name = "lowerLimit")
	private BigDecimal lowerLimit;
	
	@Column(name = "balance")
	private BigDecimal balance;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "createUser")
	private User createUser;
	
	@Column(name = "createTimestamp")
	private Timestamp createTimestamp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updateUser")
	private User updateUser;
	
	@Column(name = "updateTimestamp")
	private Timestamp updateTimestamp;
	
	public Account() {}
	
	public Account(AccountType accountType, User owner, BigDecimal lowerLimit, BigDecimal balance, User createUser) {
		this.accountType = accountType;
		this.owner = owner;
		this.accountNumber = generateAccountNumber();
		if (accountType.getLabel().equals("Account")) {
			this.lowerLimit = lowerLimit;
		} else if (accountType.getLabel().equals("Bankbook")) {
			this.lowerLimit = BigDecimal.ZERO;
		}
		this.balance = balance;
		this.createUser = createUser;
		this.createTimestamp = new Timestamp(new Date().getTime());
		this.updateUser = createUser;
		this.updateTimestamp = new Timestamp(new Date().getTime());
	}
	
	private String generateAccountNumber() {
		int rand = (int) (Math.random() * 899999999.0 + 100000000.0);
		return (accountType.getLabel().substring(0, 1) + rand);
	}
}