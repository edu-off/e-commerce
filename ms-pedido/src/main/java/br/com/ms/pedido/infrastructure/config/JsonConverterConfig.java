package br.com.ms.pedido.infrastructure.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Converter
public class JsonConverterConfig implements AttributeConverter<List<Map<String, Object>>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Map<String, Object>> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException("Erro ao converter objeto para JSON: " + exception.getMessage());
        }
    }

    @Override
    public List<Map<String, Object>> convertToEntityAttribute(String value) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(value);
            for (JsonNode node : root) {
                list.add(Map.of("id", node.path("id").asLong(),
                        "quantidade", node.path("quantidade").asInt()));
            }
            return list;
        } catch (IOException exception) {
            throw new RuntimeException("Erro ao converter JSON para objeto: " + exception.getMessage());
        }
    }
}
