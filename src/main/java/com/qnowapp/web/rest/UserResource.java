package com.qnowapp.web.rest;


import com.qnowapp.config.Constants;
import com.qnowapp.domain.AllocationDetailsModel;
import com.qnowapp.domain.Blank;
import com.qnowapp.domain.Createuser;
import com.qnowapp.domain.ImEmployee;
import com.qnowapp.domain.ProjectClass;
import com.qnowapp.domain.ProjectDetailsModel;
import com.qnowapp.domain.QnowUser;
import com.qnowapp.domain.StringProject;
import com.qnowapp.domain.TimesheetDetailsModel;
import com.qnowapp.domain.User;
import com.qnowapp.domain.UserContact;
import com.qnowapp.repository.ImEmployeeRepository;
import com.qnowapp.repository.QnowUserRepository;
import com.qnowapp.repository.UserContactRepository;
import com.qnowapp.repository.UserRepository;
import com.qnowapp.security.AuthoritiesConstants;
import com.qnowapp.service.MailService;
import com.qnowapp.service.UserService;
import com.qnowapp.service.dto.UserDTO;
import com.qnowapp.web.rest.errors.BadRequestAlertException;
import com.qnowapp.web.rest.errors.EmailAlreadyUsedException;
import com.qnowapp.web.rest.errors.LoginAlreadyUsedException;
import com.qnowapp.web.rest.vm.ManagedUserVM;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

import org.hibernate.service.spi.InjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.security.crypto.password.PasswordEncoder;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class UserResource {

	@Autowired
	QnowUserRepository qnowUserRepository;

	@Autowired
	UserContactRepository userContactRepository;

	@Autowired
	ImEmployeeRepository imEmployeeRepository;

	private static final String ENTITY_NAME = "qnowUser";
	private final Logger log = LoggerFactory.getLogger(UserResource.class);

	@Value("${jhipster.clientApp.name}")
	private String applicationName;
	private static Boolean fromTesting = false;
	private final UserService userService;

	private final UserRepository userRepository;

	private final MailService mailService;

	public UserResource(UserService userService, UserRepository userRepository, MailService mailService) {

		this.userService = userService;
		this.userRepository = userRepository;
		this.mailService = mailService;
	}

	public static void setFromTesting(Boolean bState) {
		fromTesting = bState;
	}

	public String removeQuotes(String MyEmail) {
		String doubleQuote = "\"";
		if (MyEmail.substring(0, 1).equals(doubleQuote)) {
			MyEmail = MyEmail.substring(1, MyEmail.length());
		}

		if (MyEmail.substring(MyEmail.length() - 1, MyEmail.length()).equals(doubleQuote)) {

			MyEmail = MyEmail.substring(0, MyEmail.length() - 1);

		}
		return MyEmail;
	}

	public Integer convertToInteger(String integer) {

		if (integer.equals("")) {
			return 0;
		} else {
			try {
				Float fl = Float.parseFloat(integer);
				Integer i = Math.round(fl);
				return i;
			} catch (Exception e) {
				System.out.println(e);
			}

		}
		return 0;

	}
	

	public BigDecimal convertToBigDecimal(String integer) {

		if (integer.equals("")) {
			return new BigDecimal(0);
		} else {
			try {
				Float fl = Float.parseFloat(integer);

				return new BigDecimal(fl);
			} catch (Exception e) {
				System.out.println(e);
			}

		}
		return new BigDecimal(0);

	}
	
	
	@CrossOrigin
    @PostMapping("/usersnewcreate")
    public ResponseEntity<StringProject> createUserNEW(@Valid @RequestBody Createuser createuser) throws URISyntaxException {
        log.debug("REST request to save User : {}", createuser);

     if (userRepository.findOneByEmailIgnoreCase(createuser.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {

    		StringProject msg = new StringProject();

        	AccountResource AccountResource = new AccountResource(userRepository, userService, mailService);
        	ManagedUserVM managedUserVM = new ManagedUserVM();
        	
        	
        	
	        UserDTO UserDTO = new UserDTO();
        	User user = new User();
      
        	managedUserVM.setLogin(createuser.getEmail());
        	managedUserVM.setEmail(createuser.getEmail());
        	managedUserVM.setFirstName(createuser.getFirstName());
        	managedUserVM.setLastName(createuser.getLastName());

        	
        	
        	managedUserVM.setPassword(createuser.getPassword());
        	
        	User newUser  = AccountResource.registermyAccount(managedUserVM);
        	

        	
   
			QnowUser qnowUser1 = new QnowUser();
			qnowUser1.setUser(newUser);
			QnowUser result9 = qnowUserRepository.save(qnowUser1);
			UserContact userContact1 = new UserContact();
			userContact1.setQnowUser(qnowUser1);
			UserContact result10 = userContactRepository.save(userContact1);
			ImEmployee imEmployee1 = new ImEmployee();
			imEmployee1.setQnowUser(qnowUser1);
			ImEmployee result20 = imEmployeeRepository.save(imEmployee1);
			mailService.sendCreationEmail(newUser);
			msg.setMsg("user created");

			return new ResponseEntity<StringProject>(msg, HttpStatus.OK);
        }
    }
	
	
	
	
	
	@CrossOrigin
    @PostMapping("/users")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save User : {}", userDTO);

        if (userDTO.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
        	User newUser = userService.createUser(userDTO);
			QnowUser qnowUser1 = new QnowUser();
			qnowUser1.setUser(newUser);
			QnowUser result9 = qnowUserRepository.save(qnowUser1);
			UserContact userContact1 = new UserContact();
			userContact1.setQnowUser(qnowUser1);
			UserContact result10 = userContactRepository.save(userContact1);
			ImEmployee imEmployee1 = new ImEmployee();
			imEmployee1.setQnowUser(qnowUser1);
			ImEmployee result20 = imEmployeeRepository.save(imEmployee1);
			mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert(applicationName,  "userManagement.created", newUser.getLogin()))
                .body(newUser);
        }
    }
    
	@CrossOrigin
	@PostMapping("/userscsv")
	public ResponseEntity<User> createUsercsv(@Valid @RequestBody Blank blank) throws URISyntaxException {
	
		User newUser = new User();
		UserDTO userDTO = new UserDTO();
			ResponseEntity<User> returnObj = ResponseEntity.created(new URI("/api/userscsv/" + newUser.getLogin()))
					.headers(HeaderUtil.createAlert(applicationName, "userManagement.created", newUser.getLogin()))
					.body(newUser);

			if (fromTesting == false) {
		
				String csvFile = "F:\\PMO1\\src\\main\\resources\\users06Jul2019.csv";
				
				BufferedReader br = null;
				String line = "";
				String cvsSplitBy = ";";

				try {
					br = new BufferedReader(new FileReader(csvFile));
					int count = 0;
					while ((line = br.readLine()) != null) {
						count++;
						if (count == 1)
							continue;
						String[] country = line.split(cvsSplitBy);
						try {
							if (country.length > 2) {
								userDTO.setLogin(removeQuotes(country[2]));
								userDTO.setFirstName(removeQuotes(country[0]));
								userDTO.setLastName(removeQuotes(country[1]));
								userDTO.setEmail(removeQuotes(country[2]));
					
								try {
									User newUserK = userService.createUser(userDTO);
									QnowUser qnowUser = new QnowUser();

									qnowUser.setUser(newUserK);

									QnowUser result = qnowUserRepository.save(qnowUser);
									ResponseEntity abc = ResponseEntity
											.created(new URI("/api/qnow-users/" + result.getId()))
											.headers(HeaderUtil.createEntityCreationAlert(applicationName, true,
													ENTITY_NAME, result.getId().toString()))
											.body(result);

									UserContact userContact = new UserContact();

									userContact.setWorkPhone(removeQuotes(country[6]));
									userContact.setCellPhone(removeQuotes(country[7]));
									// userContact.setPermentAddress(removeQuotes(country[2]));
									userContact.setHaLine1(removeQuotes(country[13]));
									userContact.setHaLine2(removeQuotes(country[14]));
									userContact.setHaPostal(removeQuotes(country[17]));
									userContact.setWaLine1(removeQuotes(country[19]));
									userContact.setWaLine2(removeQuotes(country[20]));
									userContact.setWaPostal(removeQuotes(country[23]));
									userContact.setUcNote(removeQuotes(country[25]));
									userContact.setPrimaryRole(removeQuotes(country[51]));
									userContact.setSecondaryRole(removeQuotes(country[52]));
									userContact.setInitiative(removeQuotes(country[47]));
									userContact.setTechnology(removeQuotes(country[50]));
									userContact.setTeamName(removeQuotes(country[48]));
									userContact.setQnowUser(qnowUser);
									
									UserContact result1 = userContactRepository.save(userContact);
									ResponseEntity ucercontact = ResponseEntity
											.created(new URI("/api/user-contacts/" + result.getId()))
											.headers(HeaderUtil.createEntityCreationAlert(applicationName, true,
													ENTITY_NAME, result.getId().toString()))
											.body(result);

									ImEmployee imEmployee = new ImEmployee();
								
									imEmployee.setJobTitle(removeQuotes(country[28]));
									
									imEmployee.setJobDescription(removeQuotes(country[29]));
								
									String availability = removeQuotes(country[30]);
									if (availability.equals("")) {
										imEmployee.setAvailability(100);
									} else {
										imEmployee.setAvailability(Integer.parseInt(availability));
									}

								
									imEmployee.setSsNumber(removeQuotes(country[32]));
									

									imEmployee.setSalary(convertToBigDecimal(removeQuotes(country[33])));
									
									imEmployee.setSocialSecurity(convertToInteger(removeQuotes(country[34])));

								

									imEmployee.setInsurance(convertToInteger(removeQuotes(country[35])));
									

									imEmployee.setOtherCosts(convertToInteger(removeQuotes(country[36])));
								
									imEmployee.setCurrency(removeQuotes(country[37]));
								
									imEmployee.setHourlyCost(new BigDecimal(20.00));
									
									imEmployee.setQnowUser(qnowUser);
							

									ImEmployee result2 = imEmployeeRepository.save(imEmployee);
							
									ResponseEntity imemployee = ResponseEntity
											.created(new URI("/api/im-employees/" + result.getId()))
											.headers(HeaderUtil.createEntityCreationAlert(applicationName, true,
													ENTITY_NAME, result.getId().toString()))
											.body(result2);

								} catch (Exception e) {
								
								}
							}
						} catch (Exception u) {
						}
					
					}
				} catch (Exception e) {
					//System.out.println(e);
				} finally {
					if (br != null) {
						try {
							br.close();
						} catch (Exception e) {
							System.out.println(e);
						}
					}
				}

			}
			
			return returnObj;
		}
	

	@CrossOrigin
	@PutMapping("/users")
	@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
		log.debug("REST request to update User : {}", userDTO);
		Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
		if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
			throw new EmailAlreadyUsedException();
		}
		existingUser = userRepository.findOneByLogin(userDTO.getLogin().toLowerCase());
		if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
			throw new LoginAlreadyUsedException();
		}
		Optional<UserDTO> updatedUser = userService.updateUser(userDTO);

		return ResponseUtil.wrapOrNotFound(updatedUser,
				HeaderUtil.createAlert(applicationName, "userManagement.updated", userDTO.getLogin()));
	}


	/**
	 * {@code GET /users} : get all users.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         all users.
	 */
	@CrossOrigin
	@GetMapping("/usersNew")
	public List<User> getAllUser() { log.debug("REST request to get all ProjectClasses");
	        return userRepository.findAll();
	}

	
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, Pageable pageable) {
        final Page<UserDTO> page = userService.getAllManagedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

	//List<UserDTO> getAllUser()
	/**
	 * Gets a list of all roles.
	 * 
	 * @return a string list of all roles.
	 */
	@CrossOrigin
	@GetMapping("/users/authorities")
	@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
	public List<String> getAuthorities() {
		return userService.getAuthorities();
	}

	/**
	 * {@code GET /users/:login} : get the "login" user.
	 *
	 * @param login the login of the user to find.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the "login" user, or with status {@code 404 (Not Found)}.
	 */
	@CrossOrigin
	@GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
	public ResponseEntity<UserDTO> getUser(@PathVariable String login) {
		log.debug("REST request to get User : {}", login);
		return ResponseUtil.wrapOrNotFound(userService.getUserWithAuthoritiesByLogin(login).map(UserDTO::new));
	}

	/**
	 * {@code DELETE /users/:login} : delete the "login" User.
	 *
	 * @param login the login of the user to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@CrossOrigin
	@DeleteMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
	@PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
	public ResponseEntity<Void> deleteUser(@PathVariable String login) {
		log.debug("REST request to delete User: {}", login);
		userService.deleteUser(login);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createAlert(applicationName, "userManagement.deleted", login)).build();
	}
}
