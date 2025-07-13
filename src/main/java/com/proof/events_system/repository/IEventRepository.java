package com.proof.events_system.repository;

import com.proof.events_system.domain.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IEventRepository extends JpaRepository<Event, Long> {

    String QUERY_FIND_BY_DATE = "SELECT e FROM Event e WHERE e.dateTime >= :dateStart AND e.dateTime < :dateEnd";

    String QUERY_FIND_BY_LOCATION = "SELECT e FROM Event e WHERE e.location = :location";

    String QUERY_FIND_AVAILABLE_EVENTS = "SELECT e FROM Event e WHERE e.availableSeats > 0";

    @Query(QUERY_FIND_BY_DATE)
    List<Event> findByDate(@Param("dateStart") LocalDateTime dateStart, @Param("dateEnd") LocalDateTime dateEnd);

    @Query(QUERY_FIND_BY_LOCATION)
    List<Event> findByLocation(@Param("location") String location);

    @Query(QUERY_FIND_AVAILABLE_EVENTS)
    List<Event> findAvailableEvents();


}
