package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "User")
public class User implements Serializable {

	private static final long serialVersionUID = -4964070823800984482L;

	@Id
	@Column(name = "idUser")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "username")
	private String username;
	
	@JsonIgnore
	@Column(name = "password")
	private String password;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "county")
	private String county;
	
	@Column(name = "postcode")
	private String postcode;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "streetname")
	private String streetname;
	
	@Column(name = "housenumber")
	private String housenumber;
	
	@Column(name = "categoryRead")
	private boolean categoryRead;
	
	@Column(name = "categoryWrite")
	private boolean categoryWrite;
	
	@Column(name = "categoryDelete")
	private boolean categoryDelete;
	
	@Column(name = "itemRead")
	private boolean itemRead;
	
	@Column(name = "itemWrite")
	private boolean itemWrite;
	
	@Column(name = "itemDelete")
	private boolean itemDelete;
	
	@Column(name = "itemCommentRead")
	private boolean itemCommentRead;
	
	@Column(name = "itemCommentWrite")
	private boolean itemCommentWrite;
	
	@Column(name = "itemCommentDelete")
	private boolean itemCommentDelete;
	
	@Column(name = "userPromote")
	private boolean userPromote;
	
	@Column(name = "userDemote")
	private boolean userDemote;
	
	@Column(name = "userDelete")
	private boolean userDelete;
	
	@Column(name = "createTimestamp")
	Timestamp createTimestamp;
	
	public User() {}

	public User(String username, String password, boolean categoryRead, boolean categoryWrite, boolean categoryDelete,
			boolean itemRead, boolean itemWrite, boolean itemDelete, boolean itemCommentRead, boolean itemCommentWrite,
			boolean itemCommentDelete, boolean userPromote, boolean userDemote, boolean userDelete) {
		this.username = username;
		this.password = password;
		this.categoryRead = categoryRead;
		this.categoryWrite = categoryWrite;
		this.categoryDelete = categoryDelete;
		this.itemRead = itemRead;
		this.itemWrite = itemWrite;
		this.itemDelete = itemDelete;
		this.itemCommentRead = itemCommentRead;
		this.itemCommentWrite = itemCommentWrite;
		this.itemCommentDelete = itemCommentDelete;
		this.userPromote = userPromote;
		this.userDemote = userDemote;
		this.userDelete = userDelete;
		this.createTimestamp = new Timestamp(new Date().getTime());
	}
	
	public boolean isSuperAdmin() {
		return (isAdmin() && 
				userPromote && userDemote);
	}
	
	public boolean isAdmin() {
		 return (isUser() && 
				 categoryWrite && categoryDelete && 
				 itemWrite && itemDelete && 
				 itemCommentDelete && 
				 userDelete);
	}
	
	public boolean isUser() {
		return (isGuest() && itemCommentWrite);
	}
	
	public boolean isGuest() {
		return (categoryRead && itemRead && itemCommentRead);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreetname() {
		return streetname;
	}

	public void setStreetname(String streetname) {
		this.streetname = streetname;
	}

	public String getHousenumber() {
		return housenumber;
	}

	public void setHousenumber(String housenumber) {
		this.housenumber = housenumber;
	}

	public boolean isCategoryRead() {
		return categoryRead;
	}

	public void setCategoryRead(boolean categoryRead) {
		this.categoryRead = categoryRead;
	}

	public boolean isCategoryWrite() {
		return categoryWrite;
	}

	public void setCategoryWrite(boolean categoryWrite) {
		this.categoryWrite = categoryWrite;
	}

	public boolean isCategoryDelete() {
		return categoryDelete;
	}

	public void setCategoryDelete(boolean categoryDelete) {
		this.categoryDelete = categoryDelete;
	}

	public boolean isItemRead() {
		return itemRead;
	}

	public void setItemRead(boolean itemRead) {
		this.itemRead = itemRead;
	}

	public boolean isItemWrite() {
		return itemWrite;
	}

	public void setItemWrite(boolean itemWrite) {
		this.itemWrite = itemWrite;
	}

	public boolean isItemDelete() {
		return itemDelete;
	}

	public void setItemDelete(boolean itemDelete) {
		this.itemDelete = itemDelete;
	}

	public boolean isItemCommentRead() {
		return itemCommentRead;
	}

	public void setItemCommentRead(boolean itemCommentRead) {
		this.itemCommentRead = itemCommentRead;
	}

	public boolean isItemCommentWrite() {
		return itemCommentWrite;
	}

	public void setItemCommentWrite(boolean itemCommentWrite) {
		this.itemCommentWrite = itemCommentWrite;
	}

	public boolean isItemCommentDelete() {
		return itemCommentDelete;
	}

	public void setItemCommentDelete(boolean itemCommentDelete) {
		this.itemCommentDelete = itemCommentDelete;
	}

	public Timestamp getCreateTimestamp() {
		return createTimestamp;
	}

	public void setCreateTimestamp(Timestamp createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public boolean isUserPromote() {
		return userPromote;
	}

	public void setUserPromote(boolean userPromote) {
		this.userPromote = userPromote;
	}

	public boolean isUserDemote() {
		return userDemote;
	}

	public void setUserDemote(boolean userDemote) {
		this.userDemote = userDemote;
	}

	public boolean isUserDelete() {
		return userDelete;
	}

	public void setUserDelete(boolean userDelete) {
		this.userDelete = userDelete;
	}
}