package com.ardaslegends.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Represents a resource in the application.
 * This class is marked as {@link Entity} and is mapped to the "resources" table in the database.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "resources")
public class Resource implements Serializable {

    /**
     * The list of production sites that produce this resource.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "producedResource")
    List<ProductionSite> productionSites;
    /**
     * The unique identifier of the resource.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * The name of the resource.
     */
    @Column(name = "resource_name", unique = true)
    private String resourceName;
    /**
     * The Minecraft item ID associated with the resource.
     */
    private String minecraftItemId;
    /**
     * The type of the resource.
     */
    @Enumerated(EnumType.STRING)
    private ResourceType resourceType;
}