package cz.muni.fi.pa165.config;

/**
 *
 * @author Jan Stourac
 */
public interface PasswordStrengthValidator {

	ValidationReport validate(String password);
}
