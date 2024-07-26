package bantads.saga_register.service;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import bantads.saga_register.dto.AccountDTO;

@Service
public class SagaAccountService {
    @Autowired
    private RabbitTemplate template;

    @Autowired
	private TopicExchange receiverTopic;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void createAccount(AccountDTO accountDTO) {
        try {
            String accountStr = objectMapper.writeValueAsString(accountDTO);
            template.convertAndSend(receiverTopic.getName(), "accountRequest", accountStr);
            System.out.println("Solicitação de cadastro de conta enviada com sucesso");
        } catch (Exception e) {
            System.out.println("Erro no processo da solicitação: " + e.getMessage());
        }
    }

    @RabbitListener(queues = "accountReplyQueue")
    public void handleAccount(String mensagem) {
        try {
            if (mensagem.contains("sucesso")) {
                System.out.println("Mensagem de cadastro de conta recebida com sucesso: ");
                System.out.println(mensagem);
                
                System.out.println("SAGA de Autocadastro finalizada com sucesso");                

            } else {
                System.out.println("Erro no processamento do cadastro de conta");
            }
        } catch (Exception e) {
            System.out.println("Erro no processamento da resposta");
            e.printStackTrace();
        }
    }
}