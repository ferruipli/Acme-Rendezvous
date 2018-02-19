package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.PictureRepository;
import domain.Picture;

@Component
@Transactional
public class StringToPictureConverter implements Converter<String, Picture> {
	
	@Autowired
	PictureRepository	pictureRepository;


	@Override
	public Picture convert(final String text) {
		Picture result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.pictureRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
