package com.sjtu.project.servicemanagement.daoImpl;

import com.sjtu.project.common.exception.ObjectNotFoundException;
import com.sjtu.project.servicemanagement.domain.CustomServiceDao;
import com.sjtu.project.servicemanagement.domain.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ServiceDaoImpl implements CustomServiceDao {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Service queryById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Service res = mongoTemplate.findOne(query, Service.class);
        if (res == null) {
            throw new ObjectNotFoundException("Service对象未找到");
        }
        else {
            return res;
        }
    }
}
