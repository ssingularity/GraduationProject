package com.sjtu.project.datasourceservice.domain;

import reactor.core.publisher.Flux;

public interface DataSourceListener {
    Flux<Void> onMessage(DataSource ds, String message);
}
