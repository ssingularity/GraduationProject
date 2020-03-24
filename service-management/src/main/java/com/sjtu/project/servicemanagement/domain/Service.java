package com.sjtu.project.servicemanagement.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sjtu.project.common.domain.RootDescriptor;
import com.sjtu.project.common.util.JsonUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Document
@Data
@Slf4j
public class Service {
    @Id
    String id;

    String name;

    RootDescriptor input;

    RootDescriptor output;

    List<DataSource> targetDataSource;

    String uri;

    HttpMethod method;

    //TODO 监控Service存活情况
    String healthEndPoint;

    public boolean verifySelf() {
        return true;
    }

    public void generateDataSource() {
        // TODO
        log.info("生成DataSource");
    }

    public void generateOutputChannel() {
        // TODO
        log.info("生成OutputChannel");
    }

    public void callWithMessage(String message) {
        RestTemplate restTemplate = Constants.ctx.getBean(RestTemplate.class);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, "application/json");
        String inputJson = input.generateOutputFromJson(message);
        ResponseEntity<String> res = null;
        if (method == HttpMethod.POST) {
            HttpEntity<String> httpEntity = new HttpEntity<>(inputJson, httpHeaders);
            res = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
        }
        else if (method == HttpMethod.GET) {
            ObjectNode objectNode = JsonUtil.readTree(inputJson);
            Iterator<Map.Entry<String, JsonNode>> it = objectNode.fields();
            StringBuilder sb = new StringBuilder();
            sb.append("?");
            while (it.hasNext()) {
                Map.Entry<String, JsonNode> entry = it.next();
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue().asText());
                if (it.hasNext()) {
                    sb.append("&");
                }
            }
            String url = uri + sb.toString();
            log.info("url 为 {}", url);
            res = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);
        }
        String resOutput = output.generateOutputFromJson(res.getBody());
        // TODO 发送resOutput给OutputChannel
    }
}
