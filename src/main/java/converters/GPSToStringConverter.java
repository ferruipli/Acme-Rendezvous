package converters;

import java.net.URLEncoder;

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
		StringBuilder builder;
		
		if(gps == null){
			result = null;
		} else {
			try {
				builder = new StringBuilder();
				builder.append(URLEncoder.encode(Double.toString(gps.getLatitude()), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(Double.toString(gps.getLongitude()), "UTF-8"));
				result = builder.toString();
			} catch (Throwable oops) {
				throw new RuntimeException(oops);
			}
		}
		return result;
	}
	

}
