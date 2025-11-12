package com.equipo7.apigestorproyectos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.equipo7.apigestorproyectos.dto.respuesta.EmpleadoResponseDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.EmpleadoCreateDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.EmpleadoUpdateDTO;
import com.equipo7.apigestorproyectos.models.Empleado;

@Mapper(componentModel = "spring")
public interface EmpleadoMapper {

    // Entity → DTO
    EmpleadoResponseDTO toResponseDTO(Empleado entity);

    // CreateDTO → Entity
    @Mapping(target = "id", ignore = true)
    Empleado toEntity(EmpleadoCreateDTO dto);

    // UpdateDTO → actualizar Entity existente (solo campos no nulos)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nombre", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "email", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "cargo", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "fechaContratacion", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "activo", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(EmpleadoUpdateDTO dto, @MappingTarget Empleado entity);
}