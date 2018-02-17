package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Administrator;


public class AdministratorToStringConverter implements Converter<Administrator, String> {
	
	@Override
	public String convert(final Administrator administrator) {
		String result;

		if (administrator == null)
			result = null;
		else
			result = String.valueOf(administrator.getId());

		return result;
	}

}
