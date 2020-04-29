package com.sjtu.project.processservice.dao;

import com.sjtu.project.processservice.domain.Process;
import com.sjtu.project.processservice.dto.ProcessDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/29 20:33
 */
public interface ProcessDao extends MongoRepository<Process, String> {
    List<ProcessDTO> findAllByOwner(String username);

    Process findOneById(String id);
}
