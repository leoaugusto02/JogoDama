package unifaj.trabalho.jogodama;

public class PecaJogador {
	private Jogador jogador;
	private Peca peca;
	private String pos;
	
	public PecaJogador(Jogador jogador, String corPeca, String posPeca) {
		this.jogador = jogador;
		this.pos = posPeca;
		this.peca = new Peca(corPeca);
	}
	
	public String descricao() {
		return "PecaJogador [jogador=" + jogador + ", peca=" + peca + ", pos=" + pos + "]";
	}
	
	public boolean validarPeca(Jogador jogador) {
		return this.jogador == jogador;
	}


	public String getPos() {
		return pos;
	}
	
	public void setPos(String pos) {
		this.pos = pos;
	}
	
}
