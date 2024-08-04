package bantads.account_command.sagashandler.sagaregister;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bantads.account_command.dto.AccountDTO;
import bantads.account_command.sagashandler.sagaregister.response.ErrorMessage;
import bantads.account_command.sagashandler.sagaregister.response.SuccessMessage;
import bantads.account_command.service.AccountService;

@Service
public class AccountRegister {

    @Autowired
    private RabbitTemplate template;

    @Autowired
	private TopicExchange senderTopic;

    @Autowired
    private AccountService accountService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "accountRequestQueue")
    public void createAccount(String mensagem) {

        try {
            AccountDTO accountDTO = objectMapper.readValue(mensagem, AccountDTO.class);            
            AccountDTO createdAccountDTO = accountService.createAccount(accountDTO);
            String accountStr = objectMapper.writeValueAsString(createdAccountDTO);
            SuccessMessage successMessage = new SuccessMessage(accountStr);
            String successStr = objectMapper.writeValueAsString(successMessage);
            template.convertAndSend(senderTopic.getName(), "accountReply", successStr);
            System.out.println("Cadastro de conta realizado com sucesso");
        } catch (Exception e) {
            String msgErro = "Erro no processamento do cadastro";
            ErrorMessage errorMessage = new ErrorMessage(msgErro);
            try {
                String errorStr = objectMapper.writeValueAsString(errorMessage);
                template.convertAndSend(senderTopic.getName(), "accountReply", errorStr);
            } catch (Exception innerEx) {
                System.out.println("Erro ao enviar mensagem de erro: " + innerEx.getMessage());
                innerEx.printStackTrace();
            }
            System.out.println(msgErro);
            e.printStackTrace();
        }
    }
    
}