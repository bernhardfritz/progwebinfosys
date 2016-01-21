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
@Table(name = "Operation")
public class Operation implements Serializable {

	private static final long serialVersionUID = -755391313756328708L;
	
	@Id
	@Column(name = "idOperation")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fromAccount")
	private Account fromAccount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "toAccount")
	private Account toAccount;
	
	@Column(name = "amount")
	private BigDecimal amount;
	
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
	
	public Operation() {}
	
	public Operation(Account fromAccount, Account toAccount, BigDecimal amount, User createUser) {
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.amount = amount;
		this.createUser = createUser;
		this.createTimestamp = new Timestamp(new Date().getTime());
		this.updateUser = createUser;
		this.updateTimestamp = new Timestamp(new Date().getTime());
	}
}