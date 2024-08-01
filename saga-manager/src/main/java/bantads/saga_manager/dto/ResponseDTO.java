package bantads.saga_manager.dto;

public class ResponseDTO {
    private boolean solicitacaoRecebida;
    private String mensagem;
    
    public ResponseDTO() {
    }

    public ResponseDTO(boolean solicitacaoRecebida, String mensagem) {
        this.solicitacaoRecebida = solicitacaoRecebida;
        this.mensagem = mensagem;
    }

    public boolean solicitacaoRecebida() {
        return solicitacaoRecebida;
    }

    public void setSolicitacaoRecebida(boolean solicitacaoRecebida) {
        this.solicitacaoRecebida = solicitacaoRecebida;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem (String mensagem) {
        this.mensagem = mensagem;
    }
}