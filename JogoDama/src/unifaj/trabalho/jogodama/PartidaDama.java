package unifaj.trabalho.jogodama;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PartidaDama {
	
	 private Tabuleiro tabuleiro;
	 private Map<Integer, Jogador> jogadores;
	 private byte jogadorTurno = 1;
	
	 public PartidaDama(Jogador[] jogadores) {
		 this.tabuleiro = new Tabuleiro();
		 this.jogadores = IntStream.range(1, jogadores.length)
				                   .boxed()
				                   .collect(Collectors.toMap(Function.identity(), i -> jogadores[i]));
	 }
	 
	 public void iniciarJogo(Scanner sc) {
		 final int tamanhoTabuleiro = tabuleiro.getCasasDisponiveis().size();
		 final List<String> casasBrancas = tabuleiro.getCasasDisponiveis().subList(0, tamanhoTabuleiro / 2 - 4);
		 final List<String> casasPretas = tabuleiro.getCasasDisponiveis().subList(tamanhoTabuleiro / 2 + 4, tamanhoTabuleiro);
		 for(int i = 1; i <= this.jogadores.size(); i++) {
			 final String corPeca = sc.nextLine();
			 final Jogador jogador = jogadores.get(i);
			 for(String pos : i == 1 ? casasBrancas : casasPretas) {
				 jogador.colocarPeca(corPeca, pos);
			 }
		 }
		 
	 }
	 
	 public void jogar(Scanner sc) {
		 System.out.println("Peça Origem: ");
		 final String pecaOrigem = sc.nextLine();
		 System.out.println("Peça Destino: ");
		 final String pecaDestino = sc.nextLine();
		 
		 moverPeca(pecaOrigem, pecaDestino);
		 
		 jogadorTurno = (byte) (jogadorTurno == 1 ? 2 : 1);
	 }
	 
	 private boolean capturarPeca() {
		 return false;
	 }
	 
	 private boolean validarPecaCaptura() {
		 return false;
	 }
	 
	 private void moverPeca(String peca, String destino) {
		 des
	 }
}
