package unifaj.trabalho.jogodama;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PartidaDama {
	
	 private PecaJogador[][] tabuleiro;
	 private Map<Byte, Jogador> jogadores;
	 private byte jogadorTurno;
	 private List<PecaJogador> pecasCaptura;
	 
	 public PartidaDama(byte tamanho) {
		 this.tabuleiro = Arrays.stream(new PecaJogador[tamanho][tamanho]).toArray(PecaJogador[][]::new);
		 this.jogadores = new HashMap<>();
		 this.jogadorTurno = 1;
		 this.pecasCaptura = new ArrayList<>();
	 } 
	 
	 
	 public void iniciarJogo(Scanner sc) {
		 for(byte i = 1; i <= 2; i++) {
			 System.out.println("Apelido para o jogador " + i + ": ");
			 final Jogador jogador = new Jogador(sc.nextLine());
			 
			 System.out.println("CorPeca: ");
			 final String corPeca = sc.nextLine();
			 
			 final int limiteCasasJogador = i == 1 ? this.tabuleiro.length/2 - 1 : this.tabuleiro.length;
			 
			 for(int linha = (limiteCasasJogador - this.tabuleiro.length/2) + 1; linha < limiteCasasJogador; linha++) {
				 for(int coluna = 0; coluna < this.tabuleiro.length; coluna++) {
					 tabuleiro[linha][coluna] = linha % 2 == 0 && coluna % 2 == 0 ? addPeca(jogador, corPeca, linha, coluna)
							  : linha % 2 != 0 && coluna % 2 != 0 ? addPeca(jogador, corPeca, linha, coluna) : null;
				 }
			 }
			 
			 this.jogadores.put(i, jogador);
		 }
		 
	 }
	 
	 public void jogar(Scanner sc) {
		 for(byte linha = 0; linha < this.tabuleiro.length; linha++) {
			 System.out.println("[ ");
			 for(byte coluna = 0; coluna < this.tabuleiro.length; coluna++) {
				 System.out.println(tabuleiro[linha][coluna] + ", ");
				 
			 }
			 System.out.println("]\n");
		 }
		 
		 System.out.println("Turno Jogador " + jogadorTurno);
		 
		 final int[] origem = selecionarOrigem(sc);
		 final int[] destino = selecionarDestino(origem, sc);
		 
		 
		 moverPeca(origem, destino);
		 
		 this.jogadorTurno = (byte) (jogadorTurno == 1 ? 2 : 1);
	 }
	 
	 private int[] selecionarOrigem(Scanner sc){
		 while(true) {
			 System.out.println("Peça Origem: ");
			 final String pecaOrigem = converterCoordenada(sc.nextLine());
			 final int[] origem = {Integer.parseInt(pecaOrigem.substring(0, 1)), Integer.parseInt(pecaOrigem.substring(1))};
			 if(verificarMovimentoOrigem(origem)) {
				 return origem;
			 }
			 System.out.println("Movimento invalido");
		 }
	 }
	 
	 private boolean verificarMovimentoOrigem(int[] origem) {
		 PecaJogador pecaJogador = tabuleiro[origem[0]][origem[1]];
		 int linha = jogadorTurno == 1 ? origem[0] + 1 : origem[0] - 1;
		 if(pecaJogador == null ||
				 !pecaJogador.validarPeca(jogadores.get(jogadorTurno)) ||
				 (origem[1] - 1 > -1 && tabuleiro[linha][origem[1] - 1] != null) ||
				 (origem[1] + 1 < 8 && tabuleiro[linha][origem[1] + 1] != null)) {
			 return false;
		 }
		 
		 return true;
	 }
	 
	 private int[] selecionarDestino(int[] origem, Scanner sc){
		 while(true) {
			 System.out.println("Peça Destino: ");
			 final String pecaDestino = converterCoordenada(sc.nextLine());
			 final int[] destino = {Integer.parseInt(pecaDestino.substring(0, 1)), Integer.parseInt(pecaDestino.substring(1))};
			 if(verificarMovimentoDestino(origem, destino)) {
				 verificarCaptura(destino);
				 return destino;
			 }
			 System.out.println("Movimento invalido");
		 }
	 }
	 
	 private boolean verificarMovimentoDestino(int[] origem, int[] destino) {
		 int linha = jogadorTurno == 1 ? origem[0] + 1 : origem[0] - 1;
		 if(tabuleiro[destino[0]][destino[1]] != null ||
				 destino[0] != linha ||
				 (destino[1] != origem[1] - 1 && destino[1] != origem[1] + 1)) {
			 return false;
		 }
		 
		 return true;
	 }
	 
	 //Verifica se a peça pode ser capturada
	 private void verificarCaptura(int[] destino) {
		 final int linhaBaixo = destino[0] - 1;
		 final int linhaCima = destino[0] + 1;
		 final int colunaEsq = destino[1] - 1;
		 final int colunaDir =  destino[1] + 1;
		 
		 //Valida os cantos
		 if(linhaBaixo > -1 && linhaCima < 8 && colunaEsq > -1 && colunaDir < 8) {
			 PecaJogador pecaSuperiorEsq = tabuleiro[linhaCima][colunaEsq];
			 PecaJogador pecaInferiorEsq = tabuleiro[linhaBaixo][colunaEsq];
			 PecaJogador pecaSuperiorDir = tabuleiro[linhaCima][colunaDir];
			 PecaJogador pecaInferiorDir = tabuleiro[linhaBaixo][colunaDir];
			 
			 //Diagonal Esquerda
			 if(pecaInferiorDir == null && pecaSuperiorEsq != null && !pecaSuperiorEsq.validarPeca(jogadores.get(jogadorTurno))) {
				 pecasCaptura.add(pecaSuperiorEsq);
			 } else if(pecaSuperiorEsq == null && pecaInferiorDir != null && !pecaInferiorDir.validarPeca(jogadores.get(jogadorTurno))) {
				 pecasCaptura.add(pecaInferiorDir);
			 }
			 
			//Diagonal Direita
			 if(pecaInferiorEsq == null && pecaSuperiorDir != null && !pecaSuperiorDir.validarPeca(jogadores.get(jogadorTurno))) {
				 pecasCaptura.add(pecaSuperiorDir);
			 } else if(pecaSuperiorDir == null && pecaInferiorEsq != null && !pecaInferiorEsq.validarPeca(jogadores.get(jogadorTurno))) {
				 pecasCaptura.add(pecaInferiorEsq);
			 } 
		 }
	 }
	 
	 private String converterCoordenada(String coordenada){
		 return Integer.parseInt(coordenada.substring(0, 1)) - 1 + "" + ((int) coordenada.charAt(1) - 64 - 1);
	 }
	 
	 private PecaJogador addPeca(Jogador jogador, String corPeca, int linha, int coluna) {
		 return new PecaJogador(jogador, corPeca, linha + "" + coluna);
	 }
	 
	 private void moverPeca(int[] origem, int[] destino) {
		 PecaJogador pecaSelecionada = tabuleiro[origem[0]][origem[1]];
		 System.out.println(pecaSelecionada.descricao());
		 tabuleiro[origem[0]][origem[1]] = null;
		 
		 tabuleiro[destino[0]][destino[1]] = pecaSelecionada;
		 pecaSelecionada.setPos(destino[0] + "" + destino[1]);
	 }
	 
	 private boolean capturarPeca() {
		 return false;
	 }
	 
	 private boolean validarPecaCaptura() {
		 return false;
	 }
	 
}
