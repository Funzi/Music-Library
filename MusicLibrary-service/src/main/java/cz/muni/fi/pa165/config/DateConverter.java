package cz.muni.fi.pa165.config;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.dozer.DozerConverter;

/**
 *
 * @author Jan Stourac
 */
public class DateConverter extends DozerConverter<Date, LocalDate> {

	public DateConverter() {
		super(Date.class, LocalDate.class);
	}

	@Override
	public LocalDate convertTo(Date a, LocalDate b) {
		return a != null ? a.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
	}

	@Override
	public Date convertFrom(LocalDate b, Date a) {
		return b != null ? Date.from(b.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null;
	}

}
