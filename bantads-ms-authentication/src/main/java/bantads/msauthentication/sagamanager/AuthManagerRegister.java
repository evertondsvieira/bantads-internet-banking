package bantads.msauthentication.sagamanager;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import bantads.msauthentication.dto.UserDTO;
import bantads.msauthentication.repository.UserRepository;
import bantads.msauthentication.model.User;
import bantads.msauthentication.sagaregister.response.ErrorMessage;
import bantads.msauthentication.sagaregister.response.SuccessMessage;
import bantads.msauthentication.services.GeneratePassword;

@Service
public class AuthManagerRegister {

    @Autowired
    private RabbitTemplate template;

    @Autowired
	private TopicExchange senderManagerTopic;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private JavaMailSender mailSender;

    private ObjectMapper objectMapper = new ObjectMapper();

    private SimpleMailMessage createTemplateMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("bantads@email.com");
        message.setSubject("BANTADS - Cadastro realizado com sucesso");
        return message;
    }

    @RabbitListener(queues = "authInsertRequestQueue")
    public void createAuth(String mensagem) {

        String msgErro;
        ErrorMessage errorMessage;
        String errorStr;

        try {
            UserDTO userDTO = objectMapper.readValue(mensagem, UserDTO.class);

            User existingUser = userRepository.findByLogin(userDTO.getLogin());
            if (existingUser == null) {
                GeneratePassword passwordGenerator = new GeneratePassword();
                String pass = passwordGenerator.generateRandomPassword();
                String salt = passwordGenerator.generateSalt();
                String hashedPassword = passwordGenerator.hashPassword(pass, salt);
                SimpleMailMessage msg = createTemplateMessage();
		        msg.setTo(userDTO.getLogin());
		        msg.setText("Prezado(a) Gerente, Cadastro realizado com sucesso. Sua senha de acesso é: " + pass);
                this.mailSender.send(msg);
                userDTO.setSalt(salt);
                userDTO.setPassword(hashedPassword);
                User createdUser = userRepository.save(mapper.map(userDTO, User.class));
                UserDTO createdUserDTO = mapper.map(createdUser, UserDTO.class);
                String userStr = objectMapper.writeValueAsString(createdUserDTO);
                SuccessMessage successMessage = new SuccessMessage(userStr);
                String successStr = objectMapper.writeValueAsString(successMessage);
                template.convertAndSend(senderManagerTopic.getName(), "authInsertReply", successStr);
                System.out.println("Cadastro de autenticação de gerente realizado com sucesso");
            } else {
                msgErro = "Erro no cadastro de autenticação de gerente. Login já cadastrado";
                errorMessage = new ErrorMessage(msgErro);
                errorStr = objectMapper.writeValueAsString(errorMessage);   
                template.convertAndSend(senderManagerTopic.getName(), "authInsertReply", errorStr);
                System.out.println(errorStr);
            }
        } catch (Exception e) {
            msgErro = "Erro no processamento do cadastro";
            errorMessage = new ErrorMessage(msgErro);

            try {
                errorStr = objectMapper.writeValueAsString(errorMessage);
                template.convertAndSend(senderManagerTopic.getName(), "authInsertReply", errorStr);
            } catch (Exception innerEx) {
                System.out.println("Erro ao enviar mensagem de erro: " + innerEx.getMessage());
                innerEx.printStackTrace();
            }
            System.out.println(msgErro);
            e.printStackTrace();
        }
    }    
}