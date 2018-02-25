package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.GPS;

import repositories.GPSRepository;

@Service
@Transactional
public class GPSService {

	// Managed repository ---------------------------------------------
	@Autowired
	private GPSRepository gpsRepository;
	
	// Supporting services --------------------------------------------
	
	// Constructors ---------------------------------------------------
	public GPSService() {
		super();
	}
	
	// Simple CRUD methods --------------------------------------------
	public GPS create() {
		GPS result;
		
		result = new GPS();
		result.setLatitude(0.0);
		result.setLongitude(0.0);
		
		return result;
	}
	
	public Collection<GPS> findAll() {
		Collection<GPS> results;
		
		results = this.gpsRepository.findAll();
		
		return results;
	}
	
	public GPS save(GPS gps) {
		Assert.notNull(gps);
		
		GPS result;
		
		result = this.gpsRepository.save(gps);
		
		return result;
	}
	
	public void delete(GPS gps) {
		Assert.notNull(gps);
		Assert.isTrue(gps.getId() != 0);
		
		this.gpsRepository.delete(gps);
	}
	
	// Other business methods -----------------------------------------
	
	// privates methods -----------------------------------------------
	
}
