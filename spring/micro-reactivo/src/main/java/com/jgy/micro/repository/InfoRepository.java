package com.jgy.micro.repository;

import com.jgy.micro.model.Info;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface InfoRepository extends ReactiveCrudRepository<Info,String> { }
