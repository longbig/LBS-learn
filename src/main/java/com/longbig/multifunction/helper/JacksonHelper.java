package com.longbig.multifunction.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * json 帮助类
 *
 * @author
 * @date 22/01/2018
 */
public class JacksonHelper {

    private static final Logger log = LoggerFactory.getLogger(JacksonHelper.class);

    private static final JsonNodeFactory FACTORY = JsonNodeFactory.instance;
    private static final ObjectReader READER;
    private static final ObjectWriter WRITER;
    private static final ObjectMapper MAPPER;

    static {
        MAPPER = newMapper();
        READER = MAPPER.reader();
        WRITER = MAPPER.writer();
    }

    private JacksonHelper() {
    }

    /**
     * Return a preconfigured ObjectReader to read JSON inputs
     *
     * @return the reader
     */
    public static ObjectReader getReader() {
        return READER;
    }

    /**
     * Return a preconfigured JsonNodeFactory to generate JSON data as
     *
     * @return the factory
     */
    public static JsonNodeFactory nodeFactory() {
        return FACTORY;
    }

    /**
     * 获取mapper
     *
     * @return
     */
    public static ObjectMapper getMapper() {
        return MAPPER;
    }

    /**
     * Return a map out of an object's members
     *
     * @param node the node
     *
     * @return a map
     */
    public static Map<String, JsonNode> asMap(final JsonNode node) {
        if (!node.isObject()) {
            return Collections.emptyMap();
        }

        final Iterator<Map.Entry<String, JsonNode>> iterator = node.fields();
        final Map<String, JsonNode> ret = Maps.newHashMap();

        Map.Entry<String, JsonNode> entry;

        while (iterator.hasNext()) {
            entry = iterator.next();
            ret.put(entry.getKey(), entry.getValue());
        }

        return ret;
    }

    /**
     * json string 转换为 Object
     *
     * @param json
     * @param clazz
     * @param <T>
     *
     * @return
     */
    public static <T> T asObject(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json) || clazz == null) {
            return null;
        }
        try {
            return MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            String message = String.format("jsonString to Object error;jsonString=%s", json);
            log.error(message, e);
            return null;
        }
    }

    /**
     * object 转换为 json string
     *
     * @param object
     *
     * @return
     */
    public static String toJSONString(Object object) {
        if (object == null) {
            return StringUtils.EMPTY;
        }
        try {
            return object instanceof String ? (String) object : WRITER.writeValueAsString(object);
        } catch (Exception e) {
            String message = String.format("Object to jsonString error;object=%s", object.getClass());
            log.error(message, e);
            return StringUtils.EMPTY;
        }
    }

    /**
     * json string 转换为 json node
     *
     * @param json
     *
     * @return
     */
    public static JsonNode toJsonNode(String json) {
        try {
            return MAPPER.readTree(json);
        } catch (Exception e) {
            String message = String.format("jsonString to JsonNode error;jsonString=%s", json);
            log.error(message, e);
            return null;
        }
    }

    /**
     * json node 转换为 map
     *
     * @param json
     *
     * @return
     */
    public static Map<String, Object> asMap(String json) {
        if (StringUtils.isBlank(json)) {
            return Collections.emptyMap();
        }

        try {
            return MAPPER.readValue(json,
                    MAPPER.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class));
        } catch (IOException e) {
            String message = String.format("jsonString to Object error;jsonString=%s", json);
            log.error(message, e);
            return Collections.emptyMap();
        }
    }

    /**
     * json node 转换为 map
     *
     * @param json
     *
     * @return
     */
    public static <T> Map<String, T> asMap(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return Collections.emptyMap();
        }

        try {
            return MAPPER.readValue(json, MAPPER.getTypeFactory().constructMapType(HashMap.class, String.class, clazz));
        } catch (IOException e) {
            String message = String.format("jsonString to Object error;jsonString=%s", json);
            log.error(message, e);
            return Collections.emptyMap();
        }
    }

    /**
     * json string 转换为 list
     *
     * @param json
     * @param clazz
     * @param <T>
     *
     * @return
     */
    public static <T> List<T> toList(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json) || clazz == null) {
            return null;
        }
        try {
            return MAPPER.readValue(json, MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            String message = String.format("jsonString to Object error;jsonString=%s", json);
            log.error(message, e);
            return null;
        }
    }

    /**
     * json string 转换为 list
     *
     * @param json
     * @param typeReference
     * @param <T>
     *
     * @return
     */
    public static <T> T asObject(String json, TypeReference<T> typeReference) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (Exception e) {
            String message = String.format("jsonString to Object error;jsonString=%s", json);
            log.error(message, e);
            return null;
        }
    }

    /**
     * json 转换为 list
     *
     * @param node
     * @param clazz
     * @param <T>
     *
     * @return
     */
    public static <T> List<T> toList(JsonNode node, Class<T> clazz) {
        if (!node.isNull() || !node.isArray() || clazz == null) {
            return null;
        }
        try {
            List<T> list = Lists.newArrayList();
            for (JsonNode jsonNode : node) {
                list.add(MAPPER.treeToValue(jsonNode, clazz));
            }
            return list;
        } catch (Exception e) {
            String message = String.format("jsonString to Object error;jsonString=%s", toJSONString(node));
            log.error(message, e);
            return null;
        }

    }

    /**
     * Return a preconfigured ObjectMapper
     *
     * @return an {@link ObjectMapper}
     */
    private static ObjectMapper newMapper() {
        return new ObjectMapper().setNodeFactory(FACTORY)
                .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
                .enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
}
