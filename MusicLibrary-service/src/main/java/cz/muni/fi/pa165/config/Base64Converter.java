package cz.muni.fi.pa165.config;

import java.util.Base64;
import org.dozer.DozerConverter;

/**
 *
 * @author Jan Stourac
 */
public class Base64Converter extends DozerConverter<byte[], String> {

	public Base64Converter() {
		super(byte[].class, String.class);
	}

	@Override
	public String convertTo(byte[] source, String destination) {
		return Base64.getEncoder().encodeToString(source);
	}

	@Override
	public byte[] convertFrom(String source, byte[] destination) {
		return Base64.getDecoder().decode(source);
	}

}
