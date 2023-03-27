package unifaj.trabalho.jogodama;

public class Jogador {
	private String apelido;
	private byte pecasCapturadas;
	
	public Jogador(String apelido) {
		this.apelido = apelido;
		this.pecasCapturadas = 0;
	}
	
	public String descricao() {
		return "Jogador apelido= " + apelido + "\npecasCapturadas= " + pecasCapturadas;
	}
}
