package bantads.saga_register.service;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import bantads.saga_register.dto.AccountDTO;
import bantads.saga_register.dto.ClientAccountDTO;
import bantads.saga_register.dto.ClientDTO;
import bantads.saga_register.dto.UserDTO;
import bantads.saga_register.response.ErrorMessage;
import bantads.saga_register.response.SuccessMessage;

@Service
public class SagaAuthService {

    @Autowired
    private RabbitTemplate template;

    @Autowired
	private TopicExchange receiverTopic;

    @Autowired
    private ClientStore clientStoreDTO;

    @Autowired
    private SagaAccountService sagaAccountService;

    private ObjectMapper objectMapper = new ObjectMapper();    

    public void createAuth(UserDTO userDTO) {
        try {
            String userStr = objectMapper.writeValueAsString(userDTO);
            template.convertAndSend(receiverTopic.getName(), "authRequest", userStr);
            System.out.println("Solicitação de cadastro de autenticação enviada com sucesso");
        } catch (Exception e) {
            System.out.println("Erro no processo da solicitação: " + e.getMessage());
        }
    }

    @RabbitListener(queues = "authReplyQueue")
    public void handleAuth(String mensagem) {
        try {
            JsonNode node = objectMapper.readTree(mensagem);
            String type = node.get("type").asText();

            if ("sucesso".equals(type)) {
                SuccessMessage successMessage = objectMapper.treeToValue(node, SuccessMessage.class);
                UserDTO userDTO = objectMapper.readValue(successMessage.getData(), UserDTO.class);
                String userStr = objectMapper.writeValueAsString(userDTO);
                System.out.println("Mensagem de cadastro de autenticação recebida com sucesso: ");
                System.out.println(userStr);
                ClientDTO clientDTO = clientStoreDTO.getClientStoreDTO();

                ClientAccountDTO clientAccountDTO = new ClientAccountDTO(clientDTO.getName(), clientDTO.getCpf());

                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setSalary(clientDTO.getSalary());
                accountDTO.setClient(clientAccountDTO);

                System.out.println("SagaAuthService envia AccountDTO para SagaAccountService");
                sagaAccountService.createAccount(accountDTO);             
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