package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Announcement;


public class AnnouncementToStringConverter implements Converter<Announcement, String> {
	
	@Override
	public String convert(final Announcement announcement) {
		String result;

		if (announcement == null)
			result = null;
		else
			result = String.valueOf(announcement.getId());

		return result;
	}

}
