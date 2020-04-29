package com.sjtu.project.servicemanagement.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.sjtu.project.common.domain.Descriptor;
import com.sjtu.project.common.util.ContextUtil;
import com.sjtu.project.common.util.JsonUtil;
import com.sjtu.project.common.util.UUIDUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@Document
@Data
@Slf4j
public class Service {
    @Id
    String id;

    String name;

    @NotBlank
    String ip;

    @NotBlank
    Integer port;

    Descriptor requestBody;

    Descriptor response;

    List<Parameter> parameters;

    @NotBlank
    String path;

    @NotBlank
    HttpMethod method;

    String healthEndPoint;

    void verifySelf() {
    }

    public DataSource generateDataSource() {
        DataSource ds = createRelatedDataSource();
        return ContextUtil.ctx.getBean(DataSourceClient.class).createDataSource(ds).getData();
    }

    private DataSource createRelatedDataSource() {
        DataSource dataSource = new DataSource();
        dataSource.setTopic(name + "-" + UUIDUtils.generateUUID(8) + "-topic");
        log.info("生成topic为 {}", dataSource.getTopic());
        dataSource.setVisible(false);
        return dataSource;
    }

    public String invokeWith(String message) {
        JsonNode content = JsonUtil.readTree(message);
        String invokeAddress = createAddress();
        String parameterList = createParameterList(content);
        String generatedPath = createPath(content);
        String url = invokeAddress + generatedPath + parameterList;
        log.info("Request Url: {}", url);
        String request = requestBody.generateJsonNodeFromJson(content).toString();
        return doInvoke(url, request);
    }

    private String createAddress() {
        return "http://" + ip + ":" + port;
    }

    String createPath(JsonNode json) {
        String res = path;
        List<Parameter> inPathParameters = parameters
                .stream()
                .filter(parameter -> parameter.getIn().equals(ParamIn.PATH))
                .collect(Collectors.toList());
        for (Parameter parameter : inPathParameters) {
            Descriptor schema = parameter.getSchema();
            String value = schema.getValueFromJson(json).toString();
            res = res.replace("{" + schema.getKeyName() + "}", value);
        }
        return res;
    }

    String createParameterList(JsonNode json) {
        StringBuilder sb = new StringBuilder();
        sb.append("?");
        parameters.stream()
                .filter(parameter -> parameter.getIn().equals(ParamIn.QUERY))
                .forEach(parameter -> {
                    Descriptor schema = parameter.getSchema();
                    String value = schema.getValueFromJson(json).toString();
                    sb.append(schema.getKeyName());
                    sb.append("=");
                    sb.append(value);
                    sb.append("&");
                });
        String res = sb.toString();
        return res.substring(0, res.length() - 1);
    }

    String doInvoke(String url, String request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>(request, httpHeaders);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> res = ContextUtil.ctx.getBean(RestTemplate.class)
                .exchange(url, method, httpEntity, String.class);
        return res.getBody();
    }
}
