package bantads.msauthentication.sagaregister;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bantads.msauthentication.dto.UserDTO;
import bantads.msauthentication.repository.UserRepository;
import bantads.msauthentication.model.User;
import bantads.msauthentication.sagaregister.response.ErrorMessage;
import bantads.msauthentication.sagaregister.response.SuccessMessage;

@Service
public class AuthRegister {

    @Autowired
    private RabbitTemplate template;

    @Autowired
	private TopicExchange senderTopic;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    private ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "authRequestQueue")
    public void createAuth(String mensagem) {

        String msgErro;
        ErrorMessage errorMessage;
        String errorStr;

        try {
            UserDTO userDTO = objectMapper.readValue(mensagem, UserDTO.class);

            User existingUser = userRepository.findByLogin(userDTO.getLogin());
            if (existingUser == null) {
                User createdUser = mapper.map(userDTO, User.class);
                userRepository.save(createdUser);
                UserDTO createdUserDTO = mapper.map(createdUser, UserDTO.class);
                String userStr = objectMapper.writeValueAsString(createdUserDTO);
                SuccessMessage successMessage = new SuccessMessage(userStr);
                String successStr = objectMapper.writeValueAsString(successMessage);
                template.convertAndSend(senderTopic.getName(), "authReply", successStr);
                System.out.println("Cadastro de autenticação realizado com sucesso");
            } else {
                msgErro = "Erro no cadastro de autenticação. Login já cadastrado";
                errorMessage = new ErrorMessage(msgErro);
                errorStr = objectMapper.writeValueAsString(errorMessage);   
                template.convertAndSend(senderTopic.getName(), "authReply", errorStr);
                System.out.println(errorStr);
            }
        } catch (Exception e) {
            msgErro = "Erro no processamento do cadastro";
            errorMessage = new ErrorMessage(msgErro);

            try {
                errorStr = objectMapper.writeValueAsString(errorMessage);
                template.convertAndSend(senderTopic.getName(), "authReply", errorStr);
            } catch (Exception innerEx) {
                System.out.println("Erro ao enviar mensagem de erro: " + innerEx.getMessage());
                innerEx.printStackTrace();
            }
            System.out.println(msgErro);
            e.printStackTrace();
        }
    }    
}