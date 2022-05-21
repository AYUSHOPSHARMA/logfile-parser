package com.example.logfile.parser.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.logfile.parser.entity.EventDetail;

/**
 * @author ayush.sharma
 *
 *  @Repository is a Spring annotation that indicates that the decorated class is a repository.
 *  A repository is a mechanism for encapsulating storage, retrieval, and search behavior which emulates a collection of objects 
 *  
 *  CrudRepository is a Spring Data interface for generic CRUD operations on a repository of a specific type. 
 *  It provides several methods out of the box for interacting with a database.
 *  */
@Repository
public interface EventDetailRepository extends CrudRepository<EventDetail, String> {

}

