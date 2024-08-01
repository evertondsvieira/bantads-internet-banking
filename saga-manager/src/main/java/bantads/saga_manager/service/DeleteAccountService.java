package bantads.saga_manager.service;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteAccountService {

    @Autowired
    private RabbitTemplate template;

    @Autowired
	private TopicExchange receiverManagerTopic;

    public void deleteAccount(String cpf) {
        try {
            template.convertAndSend(receiverManagerTopic.getName(), "accountDeleteRequest", cpf);
            System.out.println("Solicitação de remoção de gerente de MS Account enviada com sucesso");
        } catch (Exception e) {
            System.out.println("Erro no processo da solicitação: " + e.getMessage());
        }
    }

    @RabbitListener(queues = "accountDeleteReplyQueue")
    public void handleAccount(String mensagem) {
        try {
            if (mensagem.contains("sucesso")) {
                System.out.println("Mensagem de remoção de gerente no MS Account recebida com sucesso: ");
                System.out.println(mensagem);
                
                System.out.println("SAGA de remoção de gerente finalizada com sucesso");                

            } else {
                System.out.println("Erro no processamento da remoção de gerente no MS Account");
            }
        } catch (Exception e) {
            System.out.println("Erro no processamento da resposta");
            e.printStackTrace();
        }
    }
}