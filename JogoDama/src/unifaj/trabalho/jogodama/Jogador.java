package unifaj.trabalho.jogodama;

import java.util.ArrayList;
import java.util.List;

public class Jogador {
	private String apelido;
	private List<Peca> pecasJogador;
	private byte pecasCapturadas;
	
	public Jogador(String apelido) {
		this.apelido = apelido;
		pecasJogador = new ArrayList<>();
		this.pecasCapturadas = 0;
	}

	public String descricao() {
		return "Jogador apelido=" + apelido + "\npecasJogador=" + pecasJogador + "\npecasCapturadas=" + pecasCapturadas;
	}
	
	public void perderPeca(String peca) {
		
	}
	
	public void colocarPeca(String cor, String pos) {
		Peca peca = new Peca(cor, pos);
		pecasJogador.add(peca);
	}
	
	public void pecaCapturada() {
		this.pecasCapturadas += 1;
	}
	
	public boolean validarPeca() {
		return false;
	}
}
