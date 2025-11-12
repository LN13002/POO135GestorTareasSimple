package com.equipo7.apigestorproyectos.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.equipo7.apigestorproyectos.dto.RegistroHorasDTO;
import com.equipo7.apigestorproyectos.dto.solicitud.RegistroHorasCreateDTO;
import com.equipo7.apigestorproyectos.models.RegistroHoras;

@Mapper(componentModel = "spring")
public interface RegistroHorasMapper {

    @Mapping(source = "tarea.id", target = "tareaId")
    @Mapping(source = "empleado.id", target = "empleadoId")
    RegistroHorasDTO toDTO(RegistroHoras entity);

    List<RegistroHorasDTO> toDTOList(List<RegistroHoras> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "empleado", ignore = true)
    @Mapping(target = "tarea", ignore = true)
    @Mapping(source = "fecha", target = "fecha") 
    @Mapping(target = "fechaRegistro", expression = "java(java.time.LocalDateTime.now())")
    RegistroHoras toEntity(RegistroHorasCreateDTO dto);
}