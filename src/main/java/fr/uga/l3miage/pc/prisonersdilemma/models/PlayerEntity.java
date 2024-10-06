package fr.uga.l3miage.pc.prisonersdilemma.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PlayerEntity {
    @Id
    private String username;


}
