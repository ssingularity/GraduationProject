package com.sjtu.project.processservice.dto;

import com.sjtu.project.processservice.domain.ProcessStatus;
import lombok.Data;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/29 20:36
 */
@Data
public class ProcessDTO {
    String id;

    String name;

    String description;

    String owner;

    ProcessStatus status;
}
