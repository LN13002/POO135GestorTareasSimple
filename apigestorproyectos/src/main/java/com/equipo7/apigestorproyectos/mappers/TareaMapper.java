package com.equipo7.apigestorproyectos.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.equipo7.apigestorproyectos.dto.respuesta.TareaResponseDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.TareaCreateDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.TareaUpdateDTO;
import com.equipo7.apigestorproyectos.models.Tarea;

@Mapper(componentModel = "spring")
public interface TareaMapper {

    // Entity → DTO detallado (para respuestas)
    @Mapping(source = "proyecto.id", target = "proyectoId")
    @Mapping(source = "proyecto.nombre", target = "proyectoNombre")
    @Mapping(source = "empleadoAsignado.id", target = "empleadoAsignadoId")
    @Mapping(source = "empleadoAsignado.nombre", target = "empleadoAsignadoNombre")
    TareaResponseDTO toResponseDTO(Tarea entity);

    // Lista Entity → Lista DTO
    List<TareaResponseDTO> toResponseDTOList(List<Tarea> entities);

    // CreateDTO → Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "proyecto", ignore = true)
    @Mapping(target = "empleadoAsignado", ignore = true)
    @Mapping(target = "registrosHoras", ignore = true)
    @Mapping(target = "fechaCreacion", expression = "java(java.time.LocalDateTime.now())")
    Tarea toEntity(TareaCreateDTO dto);

    // UpdateDTO → actualizar Entity existente
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "proyecto", ignore = true)
    @Mapping(target = "empleadoAsignado", ignore = true)
    @Mapping(target = "registrosHoras", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    void updateEntityFromDTO(TareaUpdateDTO dto, @MappingTarget Tarea entity);
}