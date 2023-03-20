package unifaj.trabalho.jogodama;

import java.util.Scanner;

public class JogoDama {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		JogoDama jogoDama = new JogoDama();
		jogoDama.comecarJogo(sc);
	}

	private void comecarJogo(Scanner sc) {
		PartidaDama partidaDama = new PartidaDama(new Jogador[] {new Jogador(sc.nextLine()), new Jogador(sc.nextLine())});
		partidaDama.iniciarJogo(sc);
		atualizarJogo(partidaDama, sc);
	}

	private String atualizarJogo(PartidaDama partidaDama, Scanner sc) {
		partidaDama.jogar(sc);
		if(String.valueOf(sc.nextLine()).equals("Sair")) {
			return "Partida Finalizada";
		}
		return atualizarJogo(partidaDama, sc);
	}
}
