package com.franquicias.repository;

import com.franquicias.model.Franquicia;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface FranquiciaRepository extends ReactiveMongoRepository<Franquicia, String> {
    Mono<Franquicia> findByNombre(String nombre);
}
