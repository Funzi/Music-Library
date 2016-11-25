/**
 * ===========================================================================
 * IBA CZ Confidential
 * <p>
 * © Copyright IBA CZ 2016 ALL RIGHTS RESERVED
 * The source code for this program is not published or otherwise
 * divested of its trade secrets.
 * ===========================================================================
 */
package cz.muni.fi.pa165.config;

import java.time.LocalDate;
import java.util.Base64;

import org.dozer.DozerConverter;

/**
 *
 * Created by oldrichkonecny on 25.11.16.
 */
public class LocalDateConvert extends DozerConverter<LocalDate, LocalDate> {

    public LocalDateConvert() {
        super(LocalDate.class, LocalDate.class);
    }

    @Override
    public LocalDate convertTo(LocalDate source, LocalDate destination) {
        return source;
    }

    @Override
    public LocalDate convertFrom(LocalDate source, LocalDate destination) {
        return source;
    }

}
