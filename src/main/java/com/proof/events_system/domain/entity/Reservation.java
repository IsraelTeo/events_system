package com.proof.events_system.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Muchas reservas pueden ser adquiridas por muchos usuarios
    @ManyToOne(
            targetEntity = UserEntity.class,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "user_id")
    private UserEntity user;

    //muchas reservas pueden tener un evento
    @ManyToOne(
            targetEntity = Event.class,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "event_id")
    private Event event;

    private boolean confirmation;

    public Reservation() {
    }

    public Reservation(Long id, UserEntity user, Event event, boolean confirmation) {
        this.id = id;
        this.user = user;
        this.event = event;
        this.confirmation = confirmation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public boolean isConfirmation() {
        return confirmation;
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }
}
