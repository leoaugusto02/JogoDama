package unifaj.trabalho.jogodama;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Tabuleiro {
	 private List<String> casasVazias;
	 private List<String> casasDisponiveis;
	 
	 public Tabuleiro() {
		 final char[] colunasImpar = {'A', 'C', 'E', 'G'};
		 final char[] colunasPar = {'B', 'D', 'F', 'H'};
		 this.casasDisponiveis = new ArrayList<>();
		 for(byte linha = 0; linha < 8; linha++) {
	    	 for(char coluna : linha % 2 == 0 ? colunasPar: colunasImpar) {
	    		 this.casasDisponiveis.add(String.valueOf(linha) + coluna);
	    	 }
		 }
		 this.casasVazias = new LinkedList<>();
		 final int meioTabuleiro = this.casasDisponiveis.size() / 2;
		 this.casasVazias = this.casasDisponiveis.subList(meioTabuleiro - 4, meioTabuleiro + 4);
	 }
	 
	 
	public String descricao() {
		return "Tabuleiro [casasVazias=" + casasVazias + ", casasDisponiveis=" + casasDisponiveis + "]";
	}

	public boolean validarMovimento(String destino){
		return false;
	 }
	 
	 public void addCasaVazia(String casa) {
		 
	 }
	 
	 public void removerCasaVazia(String casa) {
		 
	 }


	public List<String> getCasasDisponiveis() {
		return casasDisponiveis;
	}
	 
	 
}
