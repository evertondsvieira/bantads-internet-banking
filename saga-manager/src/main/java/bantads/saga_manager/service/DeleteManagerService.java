package bantads.saga_manager.service;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import bantads.saga_manager.dto.ManagerDTO;
import bantads.saga_manager.response.ErrorMessage;
import bantads.saga_manager.response.SuccessMessage;
import bantads.saga_manager.store.CpfStore;
import bantads.saga_manager.store.EmailStore;

@Service
public class DeleteManagerService {

    @Autowired
    private RabbitTemplate template;

    @Autowired
	private TopicExchange receiverManagerTopic;

    @Autowired
    private EmailStore emailManagerStore;

    @Autowired
    private CpfStore cpfManagerStore;

    @Autowired
    private DeleteAuthService deleteAuthService;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void deleteManager(String email) {
        try {
            template.convertAndSend(receiverManagerTopic.getName(), "managerDeleteRequest", email);
            System.out.println("Solicitação de remoção de gerente enviada com sucesso");
        } catch (Exception e) {
            System.out.println("Erro no processo da solicitação: " + e.getMessage());
        }
    }

    @RabbitListener(queues = "managerDeleteReplyQueue")
    public void handleManager(String mensagem) {
        try {
            JsonNode node = objectMapper.readTree(mensagem);
            String type = node.get("type").asText();

            if ("sucesso".equals(type)) {
                SuccessMessage successMessage = objectMapper.treeToValue(node, SuccessMessage.class);
                String mensagemRetorno = successMessage.getData();
                ManagerDTO managerDTO = objectMapper.readValue(successMessage.getData(), ManagerDTO.class);

                cpfManagerStore.setCpfManagerStore(managerDTO.getCpf());

                System.out.println("Mensagem de remoção de gerente recebida com sucesso: ");
                System.out.println("Gerente removido com sucesso: " + mensagemRetorno);

                String emailManager = emailManagerStore.getEmailManagerStore();

                System.out.println("DeleteManagerService envia E-mail de Gerente para DeleteAuthService");
                deleteAuthService.deleteAuth(emailManager);      

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