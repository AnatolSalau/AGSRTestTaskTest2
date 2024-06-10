package com.example.resource_server.entity;

import com.example.resource_server.entity.enums.GenderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.util.Date;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "patients")
public class Patient {
      public Patient(String name, GenderType gender, Date birthday) {
            this.name = name;
            this.gender = gender;
            this.birthday = birthday;
      }

      @Id
      @Column(name = "patient_id")
      @GeneratedValue(strategy = GenerationType.UUID)
      private UUID id;

      @Column(nullable = false, unique = true)
      private String name;

      @Enumerated(EnumType.STRING)
      @JdbcType(PostgreSQLEnumJdbcType.class)
      private GenderType gender;

      @Temporal(TemporalType.DATE)
      @Column(nullable = false)
      private Date birthday;

      @Column(name = "created_at", nullable = false)
      @CreationTimestamp
      private Date createdAt;

      @Column(name = "updated_at", nullable = false)
      @UpdateTimestamp
      private Date updatedAt;
}
