package com.example.demo.repository;

import com.example.demo.dto.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public class OrderDao {

    private final MongoTemplate mongoTemplate;

    public OrderDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Transactional
    public Order save(Order order) {
        return mongoTemplate.save(order);
    }

    public Order findById(String id) {
        return mongoTemplate.findOne(new Query(Criteria.where("_id").is(id)), Order.class);
    }

    public List<Order> findAll() {
        return mongoTemplate.findAll(Order.class);
    }

    public void deleteById(String id) {
        mongoTemplate.remove(new Query(Criteria.where("_id").is(id)), Order.class);
    }
}