package com.zhou.demo.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author ZhouMeng
 * @version 1.0.0
 * @date 2023/4/22 下午7:39
 */
public class JsonUtils<T> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // 统一日期格式yyyy-MM-dd HH:mm:ss
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).registerModule(timeModule);
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
    }

    /**
     * 将对象转换成json字符串。
     */
    @SneakyThrows
    public static String toString(Object data) {
        if (data == null) {
            return null;
        }
        if (data instanceof String) {
            return (String) data;
        }
        return OBJECT_MAPPER.writeValueAsString(data);
    }

    /**
     * 将对象转换成json字符串。
     */
    @SneakyThrows
    public static String toStringAlways(Object data) {
        return OBJECT_MAPPER.writeValueAsString(data);
    }

    /**
     * @param data   data
     * @param format for example: yyyy-MM-dd
     * @return String
     */
    @SneakyThrows
    public static String toString(Object data, String format) {
        if (data == null) {
            return null;
        }
        if (data instanceof String) {
            return (String) data;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return OBJECT_MAPPER.writer(sdf).writeValueAsString(data);
    }


    @SneakyThrows
    public static <T> T parse(String jsonData, Class<T> beanType) {
        if (!StringUtils.hasText(jsonData)) {
            return null;
        }
        return OBJECT_MAPPER.readValue(jsonData, beanType);
    }

    /**
     * 处理 嵌套的范型
     */
    @SneakyThrows
    public static <T> T parseWithTypeReference(String jsonData, TypeReference<T> beanType) {
        return OBJECT_MAPPER.readValue(jsonData, beanType);
    }

    /**
     * 将json数据转换成pojo对象list
     */
    @SneakyThrows
    public static <T> List<T> ofList(String jsonData, Class<T> type) {
        if (!StringUtils.hasText(jsonData)) {
            return null;
        }
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(List.class, type);
        return OBJECT_MAPPER.readValue(jsonData, javaType);
    }

}
