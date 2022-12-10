package com.flight.booking.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="users")
public class UserEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long userId ;

   @Column(unique=true , nullable=false)
   @NotBlank(message ="* Username is required")
   private String username;
   @Column(unique=true , nullable=false)
   @NotBlank(message ="* password is required")
   @Size(min=8)
   private String password;
   @Column( nullable=false)
   @NotBlank(message ="* First Name is required")
   private String firstName;
   @Column( nullable=false)
   @NotBlank(message ="* Last Name is required")
   private String lastName;


   private Integer age;
   @Column(unique=true , nullable=false)
   @NotBlank(message ="* Email is required")
   @Email(message = "{errors.invalid_email}")
   private String email;
   @Column( nullable=false)
   @NotBlank(message ="* Phone Number is required")
   private String phoneNumber;


   @ManyToMany(fetch =FetchType.EAGER ,cascade = CascadeType.ALL)
   @JoinTable(name="user_roles" ,joinColumns = @JoinColumn(name="user_id",referencedColumnName = "userId"),
             inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "roleId"))

  private List<Role> roles = new ArrayList<>();


   @OneToMany(mappedBy="user",cascade=CascadeType.ALL,orphanRemoval = true)
   private List<Reservation> reservations = new ArrayList<Reservation>();

   @OneToMany(mappedBy="user",cascade=CascadeType.ALL,orphanRemoval = true)
   private List<Payment> payments = new ArrayList<Payment>();

}
