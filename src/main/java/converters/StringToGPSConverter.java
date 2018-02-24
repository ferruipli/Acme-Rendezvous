package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.GPSRepository;

import domain.GPS;

@Component
@Transactional
public class StringToGPSConverter implements Converter<String, GPS> {
	
	@Autowired
	GPSRepository gpsRepository;
	
	@Override
	public GPS convert(String text){
		GPS result;
		int id;
		
		try {
			if (text.isEmpty()) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = this.gpsRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}

}
