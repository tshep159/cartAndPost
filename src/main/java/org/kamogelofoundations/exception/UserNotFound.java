
package org.kamogelofoundations.exception;

import java.io.Serializable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Tshepo
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No User found!")
public class UserNotFound extends RuntimeException implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private String province;

	public UserNotFound() {
		this("Sorry please try again.");
	}

	public UserNotFound(String message) {
		this.message = message;
	}

	public UserNotFound(String message, String province) {
		this.message = message;
		this.province = province;
	}

	public String getMessage() {
		return message;
	}

	public String getProvince() {
		return province;
	}
    
}
