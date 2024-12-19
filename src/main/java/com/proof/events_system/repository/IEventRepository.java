package com.proof.events_system.repository;

import com.proof.events_system.domain.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEventRepository extends JpaRepository<Event, Long> {

   // Filtrar eventos por ubicación, fecha, y disponibilidad.
}
