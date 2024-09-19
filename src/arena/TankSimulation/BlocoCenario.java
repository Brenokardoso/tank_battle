package TankSimulation;

import javaengine.CSprite;

public class BlocoCenario implements Comparable<BlocoCenario> {
	public CSprite imagemBloco = null;
	public int linha, coluna;
	public int custoEstimadoParaDestino = 0;
	public int custoMovimento = Integer.MAX_VALUE;
	public BlocoCenario anterior = null;

	public BlocoCenario(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}

	public int retornaCustoBloco() {
		return imagemBloco.getIndiceAnimacaoAtual() > 0 ? 0 : Integer.MAX_VALUE;
	}

	public int retornaCustoTotal() {
		return custoMovimento + custoEstimadoParaDestino;
	}

	public boolean equals(Object objeto) {

		if (!(objeto instanceof BlocoCenario)) {
			return false;
		}
		BlocoCenario param = (BlocoCenario) objeto;
		return this.linha == param.linha && this.coluna == param.coluna;
	}

	public int compareTo(BlocoCenario bloco) {
		// TODO Auto-generated method stub
		return Integer.compare(this.retornaCustoTotal(), bloco.retornaCustoTotal());
	}
}
