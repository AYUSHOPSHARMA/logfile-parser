package com.example.logfile.parser.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * @author ayush.sharma
 *
 *  An entity is a lightweight persistence domain object. Typically, 
 *  an entity represents a table in a relational database, and each entity instance corresponds to a row in that table.
 *  
 *  Here EventDetail is representing a table event_details and all fields of this class represent the column of table
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "event_details")
public class EventDetail {

	@Id
	private String id;
	private String type;
	private Long eventDuration;
	private String host;
	private Boolean alert;
	
	



}
