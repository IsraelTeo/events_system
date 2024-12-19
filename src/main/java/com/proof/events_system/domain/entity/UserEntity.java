package com.proof.events_system.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 70, nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String password;

    @OneToOne(
            fetch = FetchType.EAGER,
            targetEntity = RoleEntity.class,
            cascade = CascadeType.PERSIST
    )
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @OneToMany(
            targetEntity = Reservation.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "reservation_id")
    private List<Reservation> reservations;

    @Temporal(TemporalType.DATE)
    @Column(name="create_at", columnDefinition = "DATE")
    private LocalDate createAt;

    @PrePersist
    public void prePersist(){
        this.createAt = LocalDate.now();
    }

    public UserEntity() {
    }

    public UserEntity(Long id, String firstName, String lastName, String password, RoleEntity role, List<Reservation> reservations, LocalDate createAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
        this.reservations = reservations;
        this.createAt = createAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }
}
