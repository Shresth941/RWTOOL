package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "report_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;
}
