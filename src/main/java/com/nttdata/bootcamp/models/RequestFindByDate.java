package com.nttdata.bootcamp.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RequestFindByDate {
	
	private String productName;
	private String to;
	private String from;

}
