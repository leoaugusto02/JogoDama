package unifaj.trabalho.jogodama;

public class PecaJogador {
	private Jogador jogador;
	private Peca peca;
	private int[] pos;
	
	public PecaJogador(Jogador jogador, String corPeca, int[] posPeca) {
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

	public int[] getPos() {
		return pos;
	}
	
	public void setPos(int[] pos) {
		this.pos = pos;
	}
	
}
