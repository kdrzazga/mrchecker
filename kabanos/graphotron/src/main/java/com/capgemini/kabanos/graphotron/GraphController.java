package com.capgemini.kabanos.graphotron;

import org.springframework.web.bind.annotation.RestController;

import com.capgemini.kabanos.common.domain.Preposition;
import com.capgemini.kabanos.database.DataBase;
import com.capgemini.kabanos.graphotron.domain.tos.PrepositionTos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
public class GraphController {

	
	@CrossOrigin
    @RequestMapping("/prepositions")
    public List<PrepositionTos> someName() {
    	
    	List<Preposition> prepositions = new DataBase().getAllPrepositions();

        return prepositions.stream().map((Preposition el) -> {
        	PrepositionTos result = new PrepositionTos();
        	result.setId(el.getId());
        	result.setTotalNumber(el.getTotalNumber());
        	result.setLoggerStep(el.getLoggerStep());
        	result.setPredecessors(this.getIdList(el));
        	result.setImplementations(el.getImplementations().size());
        	
        	return result; 
        }).collect(Collectors.toList());
    }

    private Long[] getIdList(Preposition prep) {    	
    	return prep.getPredecessors().stream().map((Preposition pred) -> {
    		return pred.getId();
    	}).collect(Collectors.toList()).toArray(new Long[] {});
    }
}