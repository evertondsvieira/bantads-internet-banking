package bantads.saga_register.service;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import bantads.saga_register.dto.ClientDTO;
import bantads.saga_register.dto.UserDTO;
import bantads.saga_register.response.ErrorMessage;
import bantads.saga_register.response.SuccessMessage;

@Service
public class SagaClientService {

    @Autowired
    private RabbitTemplate template;

    @Autowired
	private TopicExchange receiverTopic;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private SagaAuthService sagaAuthService;

    @Autowired
    private ClientStore clientStoreDTO;

    public void createClient(ClientDTO clientDTO) {
        try {
            String clientStr = objectMapper.writeValueAsString(clientDTO);
            template.convertAndSend(receiverTopic.getName(), "clientRequest", clientStr);
            System.out.println("Solicitação de cadastro de cliente enviada com sucesso");
        } catch (Exception e) {
            System.out.println("Erro no processo da solicitação: " + e.getMessage());
        }
    }

    @RabbitListener(queues = "clientReplyQueue")
    public void handleClient(String mensagem) {
        System.out.println(mensagem);
        try {
            JsonNode node = objectMapper.readTree(mensagem);
            String type = node.get("type").asText();

            if ("sucesso".equals(type)) {
                SuccessMessage successMessage = objectMapper.treeToValue(node, SuccessMessage.class);
                ClientDTO clientDTO = objectMapper.readValue(successMessage.getData(), ClientDTO.class);
                String clientStr = objectMapper.writeValueAsString(clientDTO);
                // Armazena o clientDTO no ClientStore
                clientStoreDTO.setClientStoreDTO(clientDTO);

                System.out.println("Mensagem de cadastro de cliente recebida com sucesso: ");
                System.out.println(clientStr);
                UserDTO userDTO = new UserDTO();
                userDTO.setLogin(clientDTO.getEmail());
                userDTO.setRole("CLIENT");

                System.out.println("SagaClientService envia UsuarioDTO para SagaAuthService"); 
                sagaAuthService.createAuth(userDTO);               

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