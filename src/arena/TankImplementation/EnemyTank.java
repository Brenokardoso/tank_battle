package TankImplementation;

import TankSimulation.BlocoCenario;
import TankSimulation.Tank;
import TankSimulation.TankArena;
import javaengine.CSprite;
import javaengine.CTempo;

public class EnemyTank extends Tank {

	CTempo informaçõesCTempo = null;
	int estado = 1;
	BlocoCenario ponto1 = null;
	BlocoCenario ponto2 = null;
	BlocoCenario ponto3 = null;
	BlocoCenario ponto4 = null;
	boolean seAproximar = false;

	public EnemyTank(CSprite[] sprite, TankArena arena) {
		super(sprite, arena);

		informaçõesCTempo = new CTempo(2000);
		informaçõesCTempo.inicia(gerenciadorTempo);
		ponto1 = matrizBlocos[9][9];
		ponto2 = matrizBlocos[1][0];
		ponto4 = matrizBlocos[5][5];
	}

	@Override
	public void executa() {
		// Lógica do RocketTank para patrulhar e coletar PowerUps
		if (!this.tankEmMovimento()) {
			if (temPowerUp()) {
				ponto3 = matrizBlocos[retornaBlocoPowerUP().linha][retornaBlocoPowerUP().coluna];
				movePara(ponto3);
				estado = 3;
			} else if (estado == 1) {
				movePara(ponto2);
				estado = 2;
			} else if (estado == 3) {
				movePara(ponto4);
				estado = 2;
			} else {
				movePara(ponto1);
				estado = 1;
			}
		}

		// Lógica do RocketTank para atacar o inimigo
		apontarCanhaoParaInimigo();
		coletarBalasSeProximo();

		if (inimigo.qtdTiros > 0 && temEscudo()) {
			iniciaEscudo();
		} else {
			if (!temParede(retornaBlocoTankInimigo())) {
				tank[1].fAngle += 1;
				atirar();

			}

			double distancia = distanciaEuclidiana(retornaPosicaoAtual(), inimigo.retornaPosicaoAtual());

			if (distancia < 4) {
				seAproximar = true;
				recuarParaDistanciaSegura();
			} else {
				if (seAproximar) {
					pararMovimento();
					seAproximar = false;
				}
				avancarParaInimigo();
			}
		}

		// Printar informações
		printInformacoes();
	}

	private void apontarCanhaoParaInimigo() {
		Integer difX = inimigo.retornaPosicaoAtual().linha - this.retornaPosicaoAtual().linha;
		Integer difY = inimigo.retornaPosicaoAtual().coluna - this.retornaPosicaoAtual().coluna;
		Double anguloRadial = Math.atan2(difY, difX);
		Double anguloGraus = Math.toDegrees(anguloRadial);
		Float angulocanhao = (float) ((90 - anguloGraus) % 360);

		rotacionaCanhao(angulocanhao);
	}

	private void coletarBalasSeProximo() {
		BlocoCenario blocoAtual = retornaPosicaoAtual();
		BlocoCenario blocoBala = retornaBlocoPowerUP();

		if (blocoBala != null) {
			double distancia = distanciaEuclidiana(blocoAtual, blocoBala);

			if (distancia < 2) {
				temPowerUp();
			} else {
				movePara(blocoBala);
			}
		}
	}

	private boolean temParede(BlocoCenario retornaBlocoTankInimigo) {
		return false;
	}

	private void recuarParaDistanciaSegura() {
		Integer difX = this.retornaPosicaoAtual().linha - inimigo.retornaPosicaoAtual().linha;
		Integer difY = this.retornaPosicaoAtual().coluna - inimigo.retornaPosicaoAtual().coluna;
		Double anguloRadial = Math.atan2(difY, difX);
		Double anguloGraus = Math.toDegrees(anguloRadial);
		Float anguloFuga = (float) ((90 + anguloGraus) % 360);

		rotacionaCanhao(anguloFuga);
		movePara(trasDoInimigo());
	}

	private BlocoCenario trasDoInimigo() {
		return (null);
	}

	private void avancarParaInimigo() {
		movePara(retornaBlocoTankInimigo());
	}

	private double distanciaEuclidiana(BlocoCenario bloco1, BlocoCenario bloco2) {
		double dx = bloco1.coluna - bloco2.coluna;
		double dy = bloco1.linha - bloco2.linha;
		return Math.sqrt(dx * dx + dy * dy);
	}

	private void printInformacoes() {
		informaçõesCTempo.atualiza();
		if (informaçõesCTempo.fimIntervalo()) {
			informaçõesCTempo.reinicia();
			System.out.print("Inimigo" + " " + retornaBlocoTankInimigo().linha + " " + retornaBlocoTankInimigo().coluna
					+ " Total balas: " + rertornaTotalBalasTankInimigo());
			System.out.print(
					" | aliado" + " " + this.retornaPosicaoAtual().linha + " " + this.retornaPosicaoAtual().coluna
							+ " Total balas: " + qtdTiros + " Angulo do tiro do aliado " + retornaAnguloCanhao());

			if (temPowerUp()) {
				System.out.print(" | Power Up " + retornaBlocoPowerUP().linha + " " + retornaBlocoPowerUP().coluna);
			}
			System.out.println();
		}
	}

}
