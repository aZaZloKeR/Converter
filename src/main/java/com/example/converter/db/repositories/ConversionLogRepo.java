package com.example.converter.db.repositories;

import com.example.converter.db.entities.ConversionLog;
import org.springframework.data.repository.CrudRepository;

public interface ConversionLogRepo extends CrudRepository<ConversionLog,Integer> {
}
