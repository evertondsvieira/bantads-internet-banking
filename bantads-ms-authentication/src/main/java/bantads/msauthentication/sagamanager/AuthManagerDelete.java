package bantads.msauthentication.sagamanager;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bantads.msauthentication.repository.UserRepository;
import bantads.msauthentication.model.User;
import bantads.msauthentication.sagaregister.response.ErrorMessage;
import bantads.msauthentication.sagaregister.response.SuccessMessage;

@Service
public class AuthManagerDelete {

    @Autowired
    private RabbitTemplate template;

    @Autowired
	private TopicExchange senderManagerTopic;

    @Autowired
    private UserRepository userRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "authDeleteRequestQueue")
    public void deleteAuth(String mensagem) {

        String msgErro;
        ErrorMessage errorMessage;
        String errorStr;

        try {
            String emailManager = mensagem;

            User existingUser = userRepository.findByLogin(emailManager);
            if (existingUser == null) {
                msgErro = "Erro. Registro de autenticação de gerente com email " + emailManager + " nao encontrado para remocao";
                errorMessage = new ErrorMessage(msgErro);
                errorStr = objectMapper.writeValueAsString(errorMessage);
                template.convertAndSend(senderManagerTopic.getName(), "authDeleteReply", errorStr);
                System.out.println(errorStr);
            } else {
                userRepository.delete(existingUser);
                String mensagemRetorno = "Registro de autenticação de gerente com email " + emailManager + " removido com sucesso";
                SuccessMessage successMessage = new SuccessMessage(mensagemRetorno);
                String successStr = objectMapper.writeValueAsString(successMessage);             
                template.convertAndSend(senderManagerTopic.getName(), "authDeleteReply", successStr);
                System.out.println(successStr);
            }
        } catch (Exception e) {
            msgErro = "Erro no processamento da remoção do registro de autenticação do gerente";
            errorMessage = new ErrorMessage(msgErro);

            try {
                errorStr = objectMapper.writeValueAsString(errorMessage);
                template.convertAndSend(senderManagerTopic.getName(), "authDeleteReply", errorStr);
            } catch (Exception innerEx) {
                System.out.println("Erro ao enviar mensagem de erro: " + innerEx.getMessage());
                innerEx.printStackTrace();
            }
            System.out.println(msgErro);
            e.printStackTrace();
        }
    }    
}