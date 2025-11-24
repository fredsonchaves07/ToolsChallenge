package tools.challenge.api.controller.pagamento;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import tools.challenge.api.core.controller.ApiGetController;
import tools.challenge.core.error.InternalError;
import tools.challenge.domain.pagamentos.transacao.entities.TransacaoID;
import tools.challenge.domain.pagamentos.transacao.usecases.input.TransacaoIdInput;
import tools.challenge.domain.pagamentos.transacao.usecases.output.TransacaoOutput;
import tools.challenge.domain.pagamentos.transacao.usecases.pagamento.BuscarPagamentoPorIdUseCase;

@Path("/pagamentos/{id}")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Pagamentos")
public class BuscarPagamentoPorIdController extends ApiGetController {

    @Inject
    BuscarPagamentoPorIdUseCase useCase;

    @Inject
    UriInfo uriInfo;

    @Override
    @GET
    @Operation(
            summary = "Obtem pagamento",
            description = "Obtem e recupera informações de transação de pagamento ao informar o id"
    )
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
                                    schema = @Schema(implementation = InternalError.class)),
                            responseCode = "500",
                            description = MENSAGEM_SERVIDOR
                    )
            }
    )
    public Response execute(@PathParam("id") String id) {
        return response(useCase.execute(TransacaoIdInput.criaInput(TransacaoID.of(id))));
    }
}
