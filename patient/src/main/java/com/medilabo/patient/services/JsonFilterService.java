package com.medilabo.patient.services;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

@Service
public class JsonFilterService {
    private static final Logger logger = LoggerFactory.getLogger(JsonFilterService.class);

    public MappingJacksonValue filterProperties(Object data, String filterName, String... excludedFields) {
        logger.debug("Applying filter on patient list to exclude createdAt and updatedAt");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept(excludedFields);
        FilterProvider filters = new SimpleFilterProvider().addFilter(filterName, filter);
        MappingJacksonValue mapping = new MappingJacksonValue(data);
        mapping.setFilters(filters);

        return mapping;
    }
}
