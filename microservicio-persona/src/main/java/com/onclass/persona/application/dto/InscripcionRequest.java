package com.onclass.persona.application.dto;

import java.util.List;

public record InscripcionRequest(Long idPersona, List<Long> idsBootcamps) {
}
