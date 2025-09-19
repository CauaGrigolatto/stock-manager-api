package br.com.codebeans.stockapi.model.dto;

import java.time.LocalDate;

public record CreationDateFiltersDTO(LocalDate after, LocalDate before, boolean today) {

}
