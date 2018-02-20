package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.RSVPRepository;
import domain.RSVP;

@Component
@Transactional
public class StringToRSVPConverter implements Converter<String, RSVP> {
	
	@Autowired
	RSVPRepository	rsvpRepository;


	@Override
	public RSVP convert(final String text) {
		RSVP result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.rsvpRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
