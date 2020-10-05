package org.kamogelofoundations.dto;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Email
	@Size(min = 6, max = 250, message = "Email Requires Atleast Six Characters")
	@NotBlank(message = "Please enter Email!")
	@Column(name = "email")
	private String email;
	@Size(min = 2, max = 50, message = "First Name Requires Atleast Two Characters")
	@NotBlank(message = "Please enter First Name!")
	@Column(name = "firstname")
	private String firstname;

	@Size(min = 2, max = 50, message = "Last Name Requires Atleast Two Characters")
	@NotBlank(message = "Please enter Last Name!")
	@Column(name = "lastname")
	private String lastname;


	@NotBlank(message = "Please enter First Password!")
	@Column(name = "password")
	private String password;

	@Size(min = 10, max = 12, message = "Contact Number Must Atleast be 10")
	@Pattern(regexp = "[0-9.\\-+ ]*", message = "contact number requires only numbers")
	@NotBlank(message = "Please enter contact number!")
	private String cell;


	@NotBlank(message = "Please enter Address!")

	private String address;

	@Column(name = "active")
	private boolean active;

	private String token;

	@OneToOne
	private Cart cart;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/*
	 * @Id
	 * 
	 * @GeneratedValue(strategy = GenerationType.AUTO)
	 * 
	 * @Column(name = "user_id") private Long id;
	 * 
	 * @Column(name = "password")
	 * 
	 * @Length(min = 8, message = "Your Password Must Have Atleast 8 Characters")
	 * 
	 * @NotEmpty(message = "Please Enter Your password")
	 * 
	 * @org.springframework.data.annotation.Transient private String password;
	 * 
	 * @Column(name = "first_name")
	 * 
	 * @NotEmpty(message = "Please Enter Your First Name")
	 * 
	 * @Size(min = 2, max = 50, message =
	 * "First Name Must Atleast Have Two Characters")
	 * 
	 * @Pattern(regexp = "[A-Za-z. ]*", message =
	 * "First Name Requires Alphaberts Only") private String first_name;
	 * 
	 * @Size(min = 2, max = 50, message =
	 * "Username Requires Atleast Two Characters")
	 * 
	 * @Pattern(regexp = "[A-Za-z. ]*", message =
	 * "Username Requires Alphabets Only") private String username;
	 * 
	 * 
	 * 
	 * 
	 * @NotBlank(message = "Please Enter Email Address!")
	 * 
	 * @NotNull(message = "Email Requires Valid Value")
	 * 
	 * @NotEmpty(message = "Email Can Not Be Empty")
	 * 
	 * @Email(message = "Please Enter a Valid  Email Format") private String email;
	 * 
	 * 
	 * @Column(name = "last_name")
	 * 
	 * @NotEmpty(message = "Please Enter Your Last Name")
	 * 
	 * @Size(min = 2, max = 50, message =
	 * "Last Name Must Have Atleast Two Characters")
	 * 
	 * @Pattern(regexp = "[A-Za-z. ]*", message =
	 * "Last Name Requires Alphaberts Only") private String lastName;
	 * 
	 * @Column(name = "id_number")
	 * 
	 * @NotBlank(message = "Please enter ID Number!")
	 * 
	 * @NotEmpty(message = "Please Enter Your id ")
	 * 
	 * @Size(min = 13, max = 13, message = "ID Number Must be 13 Numbers")
	 * 
	 * @Pattern(regexp = "[0-9.\\-+ ]*", message =
	 * "ID Number Must Be Numbers Only!") private String idNumber;
	 * 
	 * @Size(min = 10, max = 12, message = "Contact Number Must Atleast be 10")
	 * 
	 * @Pattern(regexp = "[0-9.\\-+ ]*", message =
	 * "contact number requires only numbers")
	 * 
	 * @NotBlank(message = "Please enter contact number!")
	 * 
	 * @Column(name = "contact_number") private String contactNumber;
	 * 
	 * // User Address
	 * 
	 * @Size(min = 2, max = 50, message =
	 * "Line One Requires Atleast Two Characters")
	 * 
	 * @NotBlank(message = "Please Enter Address Line One")
	 * 
	 * @NotEmpty(message = "Address Line One Can Not Be Empty")
	 * 
	 * @Column(name = "address_line_one") private String addressLineOne;
	 * 
	 * @Size(min = 2, max = 50, message =
	 * "Line Two Requires Atleast Two Characters")
	 * 
	 * @NotBlank(message = "Please Enter Address Line Two!")
	 * 
	 * @NotEmpty(message = "Address Line Two Can Not Be Empty")
	 * 
	 * @Column(name = "address_line_two") private String addressLineTwo;
	 * 
	 * @Size(min = 2, max = 50, message = "City Requires Atleast Two Characters")
	 * 
	 * @NotEmpty(message = "City Can Not Be Empty")
	 * 
	 * @NotBlank(message = "Please enter City!") private String city;
	 * 
	 * @Pattern(regexp = "[0-9.\\-+ ]*", message =
	 * "Postal Code requires valid numaric characters")
	 * 
	 * @Column(name = "postal_code")
	 * 
	 * @NotBlank(message = "Please enter Postal Code!")
	 * 
	 * @Size(min = 3, max = 4, message = "Postal Code Requires Atleast 3 Numbers")
	 * 
	 * @NotEmpty(message = "Postal Code Can Not Be Empty") private String
	 * postalCode;
	 * 
	 * @Column(name = "active") private boolean active;
	 * 
	 * private String profilePicture = "none"; private String lastLogin; private
	 * String token;
	 * 
	 * private String role = "USER";
	 * 
	 * @Temporal(TemporalType.TIMESTAMP)
	 * 
	 * @Column(name = "create_date", nullable = false, updatable = false)
	 * 
	 * @CreationTimestamp private Date dateJoined;
	 * 
	 * @ManyToMany(cascade = CascadeType.ALL)
	 * 
	 * @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
	 * inverseJoinColumns = @JoinColumn(name = "role_id")) private Collection<Role>
	 * roles;
	 * 
	 * 
	 * 
	 * @OneToMany(mappedBy = "author") private Set<Post> posts = new HashSet<>();
	 * 
	 * 
	 * 
	 * public User() { }
	 * 
	 * 
	 * 
	 * public Long getId() { return id; }
	 * 
	 * 
	 * 
	 * public void setId(Long id) { this.id = id; }
	 * 
	 * 
	 * 
	 * public String getPassword() { return password; }
	 * 
	 * 
	 * 
	 * public void setPassword(String password) { this.password = password; }
	 * 
	 * 
	 * 
	 * public String getFirst_name() { return first_name; }
	 * 
	 * 
	 * 
	 * public void setFirst_name(String first_name) { this.first_name = first_name;
	 * }
	 * 
	 * 
	 * 
	 * public String getUsername() { return username; }
	 * 
	 * 
	 * 
	 * public void setUsername(String username) { this.username = username; }
	 * 
	 * 
	 * 
	 * public Set<Post> getPosts() { return posts; }
	 * 
	 * 
	 * 
	 * public void setPosts(Set<Post> posts) { this.posts = posts; }
	 * 
	 * 
	 * 
	 * public Collection<Role> getRoles() { return roles; }
	 * 
	 * 
	 * 
	 * public void setRoles(Collection<Role> roles) { this.roles = roles; }
	 * 
	 * 
	 * 
	 * public String getEmail() { return email; }
	 * 
	 * 
	 * 
	 * public void setEmail(String email) { this.email = email; }
	 * 
	 * 
	 * 
	 * public String getLastName() { return lastName; }
	 * 
	 * 
	 * 
	 * public void setLastName(String lastName) { this.lastName = lastName; }
	 * 
	 * 
	 * 
	 * public String getIdNumber() { return idNumber; }
	 * 
	 * 
	 * 
	 * public void setIdNumber(String idNumber) { this.idNumber = idNumber; }
	 * 
	 * 
	 * 
	 * public String getContactNumber() { return contactNumber; }
	 * 
	 * 
	 * 
	 * public void setContactNumber(String contactNumber) { this.contactNumber =
	 * contactNumber; }
	 * 
	 * 
	 * 
	 * public String getAddressLineOne() { return addressLineOne; }
	 * 
	 * 
	 * 
	 * public void setAddressLineOne(String addressLineOne) { this.addressLineOne =
	 * addressLineOne; }
	 * 
	 * 
	 * 
	 * public String getAddressLineTwo() { return addressLineTwo; }
	 * 
	 * 
	 * 
	 * public void setAddressLineTwo(String addressLineTwo) { this.addressLineTwo =
	 * addressLineTwo; }
	 * 
	 * 
	 * 
	 * public String getCity() { return city; }
	 * 
	 * 
	 * 
	 * public void setCity(String city) { this.city = city; }
	 * 
	 * 
	 * 
	 * public String getPostalCode() { return postalCode; }
	 * 
	 * 
	 * 
	 * public void setPostalCode(String postalCode) { this.postalCode = postalCode;
	 * }
	 * 
	 * 
	 * 
	 * public boolean isActive() { return active; }
	 * 
	 * 
	 * 
	 * public void setActive(boolean active) { this.active = active; }
	 * 
	 * 
	 * 
	 * public String getProfilePicture() { return profilePicture; }
	 * 
	 * 
	 * 
	 * public void setProfilePicture(String profilePicture) { this.profilePicture =
	 * profilePicture; }
	 * 
	 * 
	 * 
	 * public String getLastLogin() { return lastLogin; }
	 * 
	 * 
	 * 
	 * public void setLastLogin(String lastLogin) { this.lastLogin = lastLogin; }
	 * 
	 * 
	 * 
	 * public String getToken() { return token; }
	 * 
	 * 
	 * 
	 * public void setToken(String token) { this.token = token; }
	 * 
	 * 
	 * 
	 * public String getRole() { return role; }
	 * 
	 * 
	 * 
	 * public void setRole(String role) { this.role = role; }
	 * 
	 * 
	 * 
	 * public Date getDateJoined() { return dateJoined; }
	 * 
	 * 
	 * 
	 * public void setDateJoined(Date dateJoined) { this.dateJoined = dateJoined; }
	 * 
	 */

}
