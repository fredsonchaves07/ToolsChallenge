package tools.challenge.api.controller.estorno;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import tools.challenge.api.core.controller.ApiPostController;
import tools.challenge.core.error.InternalError;
import tools.challenge.domain.pagamentos.transacao.error.TransacaoError;
import tools.challenge.domain.pagamentos.transacao.usecases.estorno.RealizarEstornoUseCase;
import tools.challenge.domain.pagamentos.transacao.usecases.input.TransacaoInput;
import tools.challenge.domain.pagamentos.transacao.usecases.output.TransacaoOutput;

@Path("/estornos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Estornos")
public class RealizarEstornoControler extends ApiPostController<TransacaoInput> {

    @Inject
    private RealizarEstornoUseCase useCase;

    @Override
    @POST
    @Operation(summary = "Cria um estorno", description = "Cria transação de estorno de acordo com os dados informados")
    @APIResponses(
            value = {
                    @APIResponse(
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = TransacaoOutput.class)),
                            responseCode = "200",
                            description = MENSAGEM_SUCESSO
                    ),
                    @APIResponse(
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = TransacaoError.class)),
                            responseCode = "400",
                            description = MENSAGEM_CLIENTE
                    ),
                    @APIResponse(
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = InternalError.class)),
                            responseCode = "500",
                            description = "Erro interno de servidor."
                    )
            }
    )
    public Response execute(TransacaoInput input) {
        return response(useCase.execute(input));
    }

}
