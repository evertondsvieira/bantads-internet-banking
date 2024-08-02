package bantads.saga_manager.service;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import bantads.saga_manager.response.ErrorMessage;
import bantads.saga_manager.response.SuccessMessage;
import bantads.saga_manager.store.CpfStore;

@Service
public class DeleteAuthService {

    @Autowired
    private RabbitTemplate template;

    @Autowired
	private TopicExchange receiverManagerTopic;

    @Autowired
    private CpfStore cpfManagerStore;

    @Autowired
    private DeleteAccountService deleteAccountService;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void deleteAuth(String email) {
        try {
            template.convertAndSend(receiverManagerTopic.getName(), "authDeleteRequest", email);
            System.out.println("Solicitação de remoção de autenticação de gerente enviada com sucesso");
        } catch (Exception e) {
            System.out.println("Erro no processo da solicitação: " + e.getMessage());
        }
    }

    @RabbitListener(queues = "authDeleteReplyQueue")
    public void handleAuth(String mensagem) {
        try {
            JsonNode node = objectMapper.readTree(mensagem);
            String type = node.get("type").asText();

            if ("sucesso".equals(type)) {
                SuccessMessage successMessage = objectMapper.treeToValue(node, SuccessMessage.class);
                String mensagemRetorno = successMessage.getData();

                System.out.println("Mensagem de remoção de autenticação de gerente recebida com sucesso: ");
                System.out.println(mensagemRetorno);

                String cpfManager = cpfManagerStore.getCpfManagerStore();

                System.out.println("DeleteAuthService envia E-mail de Gerente para DeleteAccountService");
                deleteAccountService.deleteAccount(cpfManager);      

            } else if ("erro".equals(type)) {
                ErrorMessage errorMessage = objectMapper.treeToValue(node, ErrorMessage.class);
                String msgErroRetorno = errorMessage.getMessage();

                System.out.println("Mensagem de retorno: " + msgErroRetorno);

            } else {
                System.out.println("Tipo de mensagem desconhecido: " + type);
            }
        } catch (Exception e) {
            System.out.println("Erro no processamento da resposta");
            e.printStackTrace();
        }
    }
}