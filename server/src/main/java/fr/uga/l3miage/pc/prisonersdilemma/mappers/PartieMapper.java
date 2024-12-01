package fr.uga.l3miage.pc.prisonersdilemma.mappers;

import fr.uga.l3miage.pc.prisonersdilemma.models.PartieEntity;
import fr.uga.l3miage.pc.prisonersdilemma.responseDTO.PartieEntityDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")  // Indiquer à MapStruct d'utiliser Spring pour la gestion des beans

public interface PartieMapper {

    PartieEntityDTO toDto(PartieEntity entity);


}

