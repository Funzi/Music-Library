package cz.muni.fi.pa165.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeanMappingServiceImpl implements BeanMappingService {

	@Autowired
	private Mapper dozer;

	@Override
	public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
		return objects != null
				? objects.stream().map(u -> mapTo(u, mapToClass)).collect(Collectors.toList())
				: null;
	}

	@Override
	public <T> T mapTo(Object u, Class<T> mapToClass) {
		return u != null ? dozer.map(u, mapToClass) : null;
	}

	@Override
	public Mapper getMapper() {
		return dozer;
	}

}
