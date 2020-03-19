package com.sjtu.project.datasourceservice.daoImp;

import com.sjtu.project.common.exception.ObjectNotFoundException;
import com.sjtu.project.datasourceservice.domain.CustomDataSourceDao;
import com.sjtu.project.datasourceservice.domain.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class DataSourceDaoImpl implements CustomDataSourceDao {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public DataSource queryById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        DataSource res = mongoTemplate.findOne(query, DataSource.class);
        if (res == null) {
            throw new ObjectNotFoundException("DataSource Not Found");
        }
        else {
            return res;
        }
    }
}
