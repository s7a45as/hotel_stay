package com.homestay.common.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonListDeserializer extends JsonDeserializer<List<String>> {
    @Override
    public List<String> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        List<String> list = new ArrayList<>();
        
        if (p.currentToken() == JsonToken.START_ARRAY) {
            while (p.nextToken() != JsonToken.END_ARRAY) {
                if (p.currentToken() == JsonToken.START_OBJECT) {
                    // 处理对象格式
                    while (p.nextToken() != JsonToken.END_OBJECT) {
                        if ("url".equals(p.getCurrentName())) {
                            p.nextToken();
                            list.add(p.getValueAsString());
                        }
                    }
                } else {
                    // 处理字符串格式
                    list.add(p.getValueAsString());
                }
            }
        } else if (p.currentToken() == JsonToken.START_OBJECT) {
            // 处理单个对象
            while (p.nextToken() != JsonToken.END_OBJECT) {
                if ("url".equals(p.getCurrentName())) {
                    p.nextToken();
                    list.add(p.getValueAsString());
                }
            }
        }
        
        return list;
    }
} 