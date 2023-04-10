package unifaj.trabalho.jogodama;

public class Jogador {
	private String apelido;
	private byte pecasCapturadas;
	
	public Jogador(String apelido) {
		this.apelido = apelido;
		this.pecasCapturadas = 0;
	}
	
	public void pecaCapturada() {
		this.pecasCapturadas++;
	}
	
	public String descricao() {
		return "Jogador apelido= " + apelido + "\npecasCapturadas= " + pecasCapturadas;
	}

	public String getApelido() {
		return this.apelido;
	}

	public byte getPecasCapturadas() {
		return this.pecasCapturadas;
	}
	
}
