package com.flight.booking.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="Passenger")

public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long passengerId;

    @Column( nullable=false)
    @NotBlank(message ="* First Name is required")
    private String firstName;

    @Column( nullable=false)
    @NotBlank(message ="* Last Name is required")
    private String lastName;

    @Column( nullable=false)
    @NotBlank(message ="* Phone Number is required")
    private String phoneNumber;

    @Column( nullable=false)
    @NotBlank(message ="* Passport Number is required")
    private String passportNumber;


    private Integer age;

    @Column(nullable=false)
    @NotBlank(message ="* Email is required")
    @Email(message = "{errors.invalid_email}")
    private String email;

    @Column( nullable=false)
    @NotBlank(message ="* Address is required")
    private String address;


    @OneToOne (fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name ="ticket_id" , nullable= false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Ticket ticket ;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name ="flight_id" , nullable= false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Flight flight ;




}
