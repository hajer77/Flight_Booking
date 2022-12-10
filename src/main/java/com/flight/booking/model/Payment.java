package com.flight.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Payment")

public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;


    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name ="user_id" , nullable= true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserEntity user;


    private Integer creditCardId;
    private LocalDate paymentDate;

    private Double paymentAmount;

    @OneToOne (fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name ="ticket_id" , nullable= false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Ticket ticket;





}
