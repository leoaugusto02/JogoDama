package unifaj.trabalho.jogodama;

import java.util.Scanner;

public class JogoDama {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		JogoDama jogoDama = new JogoDama();
		jogoDama.comecarJogo(sc);
	}

	private void comecarJogo(Scanner sc) {
		PartidaDama partidaDama = new PartidaDama((byte) 8);
		partidaDama.iniciarJogo(sc);
		System.out.println(atualizarJogo(partidaDama, sc));
	}

	private String atualizarJogo(PartidaDama partidaDama, Scanner sc) {
		partidaDama.jogar(sc);
		String avalicaoPartida = partidaDama.avaliarPartida();
		return !avalicaoPartida.isEmpty() ? avalicaoPartida : atualizarJogo(partidaDama, sc);
	}
}
