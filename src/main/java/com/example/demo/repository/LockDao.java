package com.example.demo.repository;


import com.example.demo.dto.Lock;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class LockDao {

    private final MongoTemplate mongoTemplate;

    public LockDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Transactional
    public boolean acquireLock(String orderId, String owner) {
        Lock lock = new Lock(orderId, owner);
        try {
            mongoTemplate.insert(lock);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public void releaseLock(String orderId) {
        Query query = new Query(Criteria.where("_id").is(orderId));
        mongoTemplate.remove(query, Lock.class);
    }
}