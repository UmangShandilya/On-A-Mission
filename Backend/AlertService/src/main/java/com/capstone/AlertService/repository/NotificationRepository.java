package com.capstone.AlertService.repository;

import com.capstone.AlertService.model.UserNotification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<UserNotification,String> { }
