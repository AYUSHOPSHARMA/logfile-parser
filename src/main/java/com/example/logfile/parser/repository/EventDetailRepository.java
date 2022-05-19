package com.example.logfile.parser.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.logfile.parser.entity.EventDetail;

@Repository
public interface EventDetailRepository extends CrudRepository<EventDetail, String> {

}

