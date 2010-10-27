package org.signaut.camelback.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

public class ConfigurationLoader {
    private final ObjectMapper objectMapper = new ObjectMapper(new JsonFactory()
            .enable(JsonParser.Feature.ALLOW_COMMENTS).enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES));

    public <T> T loadJsonConfiguration(File src, Class<T> type) {
        try {
            return objectMapper.readValue(src, type);
        } catch (FileNotFoundException e) {
            try {
                return type.newInstance();
            } catch (Exception e1) {
                throw new IllegalArgumentException(String.format("While reading configuration of type %s from %s",
                                                                 type, src), e1);

            }
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("While reading configuration of type %s from %s", type,
                                                             src), e);
        }
    }

    public <T> T loadJsonConfiguration(InputStream input, Class<T> type) {
        try {
            return objectMapper.readValue(input, type);
        } catch (Exception e) {
            throw new IllegalArgumentException("While reading configuration of type " + type, e);
        }
    }
}
