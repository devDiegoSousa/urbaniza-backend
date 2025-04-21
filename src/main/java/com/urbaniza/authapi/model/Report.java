package com.urbaniza.authapi.model;

import com.urbaniza.authapi.enums.ReportStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;
    @NotBlank(message = "Description is required")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Latitude is required")
    @Column(nullable = false)
    private Double latitude;

    @NotNull(message = "Longitude is required")
    @Column(nullable = false)
    private Double longitude;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDateTime;

    @Column
    private String photoUrl;

    @Column(length = 255)
    private String photoPublicId; // TODO: para facilitar a exclusão no Cloudinary

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status;

    @ManyToOne(fetch = FetchType.LAZY) // Carregamento preguiçoso para evitar buscas desnecessárias
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User ID is required")
    private User user; // Relacionamento com o usuário que fez a denúncia

    @PrePersist
    protected void onCreate() {
        if (this.status == null) {
            this.status = ReportStatus.NEW; // Status padrão ao criar um report
        }
    }

}
