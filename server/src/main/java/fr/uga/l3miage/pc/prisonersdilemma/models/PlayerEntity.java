package fr.uga.l3miage.pc.prisonersdilemma.models;
import lombok.*;
import javax.persistence.*;


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
