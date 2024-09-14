package com.hopital.futur.autodiagnostic.documentation;

import com.hopital.futur.autodiagnostic.dto.DiagnosticDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Obtenir un diagnostic basé sur l'index de santé")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Diagnostic réussi",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = DiagnosticDTO[].class))),  // Utilisation d'un tableau de DiagnosticDTO
        @ApiResponse(responseCode = "400", description = "Index de santé invalide",
                content = @Content)
})
public @interface SwaggerDiagnosticOperation {
}
