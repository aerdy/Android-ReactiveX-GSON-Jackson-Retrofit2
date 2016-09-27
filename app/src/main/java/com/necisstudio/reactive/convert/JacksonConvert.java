package com.necisstudio.reactive.convert;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.Converter;

import java.io.IOException;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Created by vim on 27/09/16.
 */

public class JacksonConvert extends retrofit2.Converter.Factory implements retrofit.converter.Converter {
    private final ObjectMapper objectMapper;

    public JacksonConvert(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        JavaType javaType = objectMapper.getTypeFactory().constructType(type);
        try {
            return objectMapper.readValue(body.in(), javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TypedOutput toBody(Object object) {
        return null;
    }

}
