package tools.challenge.api.controller.pagamento;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
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
import tools.challenge.api.core.controller.ApiListController;
import tools.challenge.core.error.InternalError;
import tools.challenge.domain.pagamentos.transacao.usecases.output.ListTransacaoOutput;
import tools.challenge.domain.pagamentos.transacao.usecases.pagamento.BuscarTodosPagamentosUseCase;

@Path("/pagamentos")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Pagamentos")
public class BuscarTodosPagamentosController extends ApiListController {

    @Inject
    BuscarTodosPagamentosUseCase useCase;

    @Inject
    UriInfo uriInfo;

    @Override
    @GET
    @Operation(
            summary = "Lista todos os pagamentos",
            description = "Lista todas as transações de pagamentos cadastrados em banco de dados"
    )
    @APIResponses(
            value = {
                    @APIResponse(
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = ListTransacaoOutput.class)),
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
    public Response execute() {
        return response(useCase.execute());
    }
}
