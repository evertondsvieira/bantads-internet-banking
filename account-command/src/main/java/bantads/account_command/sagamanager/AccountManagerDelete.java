package bantads.account_command.sagamanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bantads.account_command.sagaregister.response.ErrorMessage;
import bantads.account_command.sagaregister.response.SuccessMessage;
import bantads.account_command.service.ManagerService;

@Service
public class AccountManagerDelete {

    @Autowired
    private RabbitTemplate template;

    @Autowired
	private TopicExchange senderManagerTopic;

    @Autowired
    private ManagerService managerService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "accountDeleteRequestQueue")
    public void deleteManager(String mensagem) {

        try {
            String cpfManager = mensagem;
            managerService.deleteManager(cpfManager);
            String mensagemRetorno = "Gerente com CPF " + cpfManager + " removido com sucesso do MS Account";
            SuccessMessage successMessage = new SuccessMessage(mensagemRetorno);
            String successStr = objectMapper.writeValueAsString(successMessage);             
            template.convertAndSend(senderManagerTopic.getName(), "accountDeleteReply", successStr);
            System.out.println(successStr);
        } catch (Exception e) {
            String msgErro = "Erro no processamento da remoção de gerente do MS Account" + e.getMessage();
            ErrorMessage errorMessage = new ErrorMessage(msgErro);

            try {
                String errorStr = objectMapper.writeValueAsString(errorMessage);
                template.convertAndSend(senderManagerTopic.getName(), "accountDeleteReply", errorStr);
            } catch (Exception innerEx) {
                System.out.println("Erro ao enviar mensagem de erro: " + innerEx.getMessage());
                innerEx.printStackTrace();
            }
            System.out.println(msgErro);
        }
    }    
}