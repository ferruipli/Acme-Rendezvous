package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Picture;

@Component
@Transactional
public class PictureToStringConverter implements Converter<Picture, String> {
	
	@Override
	public String convert(Picture picture){
		String result;
		
		if(picture == null){
			result = null;
		} else {
			result = String.valueOf(picture.getId());
		}
		
		return result;
	}

}
