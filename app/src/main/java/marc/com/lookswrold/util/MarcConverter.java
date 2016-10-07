package marc.com.lookswrold.util;


import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;

/**
 * Created by Broderick on 16/9/22.
 */
public class MarcConverter implements Converter {

	@Override
	public Object convert(Object value) {
		return null;
	}

	@Override
	public JavaType getInputType(TypeFactory typeFactory) {
		return null;
	}

	@Override
	public JavaType getOutputType(TypeFactory typeFactory) {
		return null;
	}
}
