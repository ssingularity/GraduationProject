package com.sjtu.project.channelservice.domain;

import com.sjtu.project.common.domain.RootDescriptor;
import lombok.Data;
import org.springframework.http.HttpMethod;

@Data
public class Service {
    String id;

    RootDescriptor input;

    RootDescriptor output;

    String uri;

    HttpMethod method;
}
