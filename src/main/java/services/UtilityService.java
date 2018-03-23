
package services;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
public class UtilityService {

	// Managed repository ------------------------------------------------------

	// Supporting services -----------------------------------------------------
	
	@Autowired
	private ManagerService managerService;


	// Constructors ------------------------------------------------------------

	public UtilityService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------------

	// Other business methods --------------------------------------------------

	public String generateValidVAT() {
		String  result;
		Integer counter;
		Set<String> managerVAT;

		managerVAT = new HashSet<>(this.managerService.findAllVATs());
		counter = 0;

		do {
			result =  this.createRandomLetters();
			counter++;
		} while (managerVAT.contains(result) || counter < 6500);

		Assert.isTrue(counter == 6500);

		return result;
	}
	
	private String createRandomLetters() {
		String result, characters;
		Random randomNumber;

		result = "";
		randomNumber = new Random();
		characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-+";

		for (int i = 0; i <= 3; i++)
			result += characters.charAt(randomNumber.nextInt(characters.length()));

		return result;
	}

	

}
