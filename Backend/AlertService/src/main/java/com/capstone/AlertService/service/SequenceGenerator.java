package com.capstone.AlertService.service;

import com.capstone.AlertService.model.DbSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
@Service
public class SequenceGenerator {
    @Autowired
    private MongoOperations mongoOperations;
    public int getSequence(String seq){
        Query q=new Query(Criteria.where("id").is(seq));
        Update update=new Update().inc("sequence",1);
        DbSequence counter=mongoOperations.findAndModify(q,update,options().returnNew(true).upsert(true),DbSequence.class);
        return !Objects.isNull(counter) ? counter.getSequence() : 1;
    }
}
