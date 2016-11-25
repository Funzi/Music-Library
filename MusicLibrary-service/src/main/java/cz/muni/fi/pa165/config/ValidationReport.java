package cz.muni.fi.pa165.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Jan Stourac
 */
public class ValidationReport {

	private final boolean hasPassed;
	private final List<String> errors;

	public ValidationReport(boolean hasPassed, Collection<String> errors) {
		this.hasPassed = hasPassed;
		this.errors = new ArrayList<>(errors);
	}

	public boolean hasPassed() {
		return hasPassed;
	}

	public List<String> getErrors() {
		return Collections.unmodifiableList(errors);
	}
}
