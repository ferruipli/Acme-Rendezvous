package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.GPS;

@Component
@Transactional
public class GPSToStringConverter implements Converter<GPS, String> {
	
	@Override
	public String convert(GPS gps){
		String result;
		
		if (gps == null) {
			result = null;
		} else {
			result = String.valueOf(gps.getId());
		}
		
		return result;
	}
	

}
