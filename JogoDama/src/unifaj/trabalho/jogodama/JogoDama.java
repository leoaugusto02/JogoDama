package unifaj.trabalho.jogodama;

import java.util.Scanner;

public class JogoDama {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		JogoDama jogoDama = new JogoDama();
		jogoDama.comecarJogo(sc);
	}

	private void comecarJogo(Scanner sc) {
		atualizarJogo(sc);
	}

	private String atualizarJogo(Scanner sc) {
		return "Está funfando";
	}
}
