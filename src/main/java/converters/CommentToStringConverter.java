package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Comment;


public class CommentToStringConverter implements Converter<Comment, String> {
	
	@Override
	public String convert(Comment commet){
		String result;
		
		if(commet == null){
			result = null;
		} else {
			result = String.valueOf(commet.getId());
		}
		
		return result;
	}

}
