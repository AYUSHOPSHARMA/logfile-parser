package com.example.logfile.parser.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
