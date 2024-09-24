package TankImplementation;

import java.util.ArrayList;
import java.util.Random;

import TankSimulation.BlocoCenario;
import TankSimulation.Tank;
import TankSimulation.TankArena;
import javaengine.CSprite;
import javaengine.CVetor2D;

// rertornaTotalBalasTankInimigo() - retorna verdadeiro se o inimigo está zerado de balas
// 
// retornaBlocoTankInimigo() - retorna o bloco que está o inimigo agora
// 
// retornaAnguloCanhao() - retorna o angulo do canhao do meu tank no momento
// 
// retornaPosicaoAtual() - retorna o bloco da minha posicao atual
// 
// retornaBlocoPowerUP()  - retorna o bloco do powerUp
// 
// temParede(int linha, int coluna)  - verifica se tem parede nessa posicao
// 
// temPowerUp()  - verdadeiro se tem powerup no cenário
// 
// tankEmMovimento() - veradeiro se o tank está em movimento
// 
// temEscudo() - veradeiro se eu estou com o escudo
// 
// pararMovimento()  - pára o movimento atual
// 
// rotacionaCanhao(float angulo) - aponta o canhao para o angulo desejado
// 
// atirar()  - atira e diminui uma unidade de bala do tanque
// 
// movePara(BlocoCenario destino) - move para um bloco destino

public class MyTank extends Tank {
	public MyTank(CSprite[] sprite, TankArena arena) {
		super(sprite, arena);
	}

	float anguloAtual = retornaAnguloCanhao();
	BlocoCenario atualPosition = retornaPosicaoAtual();
	boolean state;

	boolean initState() {
		if (state == false) {
			atualizaMovimento();
			atualizaTempos();
			atualizaAtingido();
			atualizaEscudo();
			state = true;
		}
		return state;
	}

	public void myDeathTank() {
		BlocoCenario meuBloco = retornaPosicaoAtual();
		BlocoCenario blocoInimigo = retornaBlocoTankInimigo();

		coletarBalasSeProximo();

		if (inimigo.temEscudo() == true) {
			movePara(posicaoLivreAfastamento(blocoInimigo));
		}

		if (distanciaRelativa(meuBloco, blocoInimigo) <= 15) {
			moveParaAproximacao(blocoInimigo);
			if (!temParedeEntre(blocoInimigo)) {
				if (qtdTiros > 0) {
					atualizaMovimento();
					movePara(posicaoLivreParaAtirar());
					rotacionaCanhao(calculaAnguloParaBloco(blocoInimigo));
					atirar();
				}
				if (qtdTiros <= 0) {
					if (distanciaRelativa(meuBloco, blocoInimigo) <= 2) {
						if (temEscudo() == true) {
							atualizaEscudo();
							iniciaEscudo();
						}
					} else {
						movePara(posicaoLivreAfastamento(blocoInimigo));
					}
					if (temPowerUp() == true) {
						movePara(retornaBlocoPowerUP());
						if (temEscudo() == true) {
							atualizaEscudo();
							iniciaEscudo();
						}
					} else {
						moveParaAfastamento(escolheDestinoFuga(blocoInimigo));
					}
				}
			}
		} else {
			moveParaAproximacao(blocoInimigo);
			if (meuBloco.linha == blocoInimigo.linha && meuBloco.coluna + 1 == blocoInimigo.linha + 1) {
				moveParaAfastamento(blocoInimigo);
			}
		}

	}

	private double distanciaEuclidiana(BlocoCenario bloco1, BlocoCenario bloco2) {
		double dx = bloco1.coluna - bloco2.coluna;
		double dy = bloco1.linha - bloco2.linha;
		return Math.sqrt(dx * dx + dy * dy);
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

	private double distanciaEntreBlocos(BlocoCenario bloco1, BlocoCenario bloco2) {
		return Math.abs(bloco1.linha - bloco2.linha) + Math.abs(bloco1.coluna - bloco2.coluna);
	}

	private void moveParaAproximacao(BlocoCenario blocoInimigo) {
		if (!temParedeEntre(blocoInimigo)) {
			movePara(blocoInimigo);
		} else {
			movePara(posicaoLivreParaAtirar());
		}
	}

	private void moveParaAfastamento(BlocoCenario blocoInimigo) {
		BlocoCenario blocoAfastado = posicaoLivreAfastamento(blocoInimigo);
		movePara(blocoAfastado);
	}

	private BlocoCenario posicaoLivreAproximacao(BlocoCenario blocoInimigo) {
		for (BlocoCenario vizinho : getVizinhos(retornaPosicaoAtual())) {
			if (!temParedeEntre(vizinho) && distanciaEntreBlocos(vizinho,
					blocoInimigo) < distanciaEntreBlocos(retornaPosicaoAtual(), blocoInimigo)) {
				return vizinho;
			}
		}
		return retornaPosicaoAtual(); 
	}

	private BlocoCenario posicaoLivreAfastamento(BlocoCenario blocoInimigo) {
		for (BlocoCenario vizinho : getVizinhos(retornaPosicaoAtual())) {
			if (!temParedeEntre(vizinho) && distanciaEntreBlocos(vizinho,
					blocoInimigo) > distanciaEntreBlocos(retornaPosicaoAtual(), blocoInimigo)) {
				return vizinho;
			}
		}
		return retornaPosicaoAtual(); 
	}

	private boolean temParedeEntre(BlocoCenario blocoInimigo) {
		BlocoCenario posicaoAtual = retornaPosicaoAtual();
		int linhaAtual = posicaoAtual.linha;
		int colunaAtual = posicaoAtual.coluna;
		int linhaInimigo = blocoInimigo.linha;
		int colunaInimigo = blocoInimigo.coluna;

		while (linhaAtual != linhaInimigo || colunaAtual != colunaInimigo) {
			if (temParede(linhaAtual, colunaAtual)) {
				return true;
			}

			if (linhaAtual < linhaInimigo && colunaAtual < colunaInimigo) {
				linhaAtual++;
				colunaAtual++;
			} else if (linhaAtual < linhaInimigo && colunaAtual > colunaInimigo) {
				linhaAtual++;
				colunaAtual--;
			} else if (linhaAtual > linhaInimigo && colunaAtual < colunaInimigo) {
				linhaAtual--;
				colunaAtual++;
			} else if (linhaAtual > linhaInimigo && colunaAtual > colunaInimigo) {
				linhaAtual--;
				colunaAtual--;
			}
		
			else if (linhaAtual < linhaInimigo) {
				linhaAtual++;
			} else if (linhaAtual > linhaInimigo) {
				linhaAtual--;
			} else if (colunaAtual < colunaInimigo) {
				colunaAtual++;
			} else if (colunaAtual > colunaInimigo) {
				colunaAtual--;
			}
		}

		return false; 
	}

	private BlocoCenario posicaoLivreParaAtirar() {
		for (BlocoCenario vizinho : getVizinhos(retornaPosicaoAtual())) {
			if (!temParedeEntre(vizinho)) {
				return vizinho;
			}
		}
		return retornaPosicaoAtual(); 
	}

	private int distanciaRelativa(BlocoCenario blocoAtual, BlocoCenario blocoDestino) {
		if (blocoAtual != null && blocoDestino != null) {
			return Math.abs(blocoAtual.linha - blocoDestino.linha) + Math.abs(blocoAtual.coluna - blocoDestino.coluna);
		}
		return Integer.MAX_VALUE;
	}

	private float calculaAnguloParaBloco(BlocoCenario blocoDestino) {
		CVetor2D posicaoTank = tank[0].posicao;
		CVetor2D posicaoInimigo = blocoDestino.imagemBloco.posicao;

		double deltaX = posicaoInimigo.getX() - posicaoTank.getX();
		double deltaY = posicaoInimigo.getY() - posicaoTank.getY();

		return (float) Math.toDegrees(Math.atan2(deltaY, deltaX));
	}

	private BlocoCenario escolheDestinoFuga(BlocoCenario blocoInimigo) {
		ArrayList<BlocoCenario> vizinhos = getVizinhos(retornaPosicaoAtual());

		BlocoCenario melhorDestino = null;
		int maiorDistancia = -1;

		for (BlocoCenario vizinho : vizinhos) {
			int distancia = distanciaRelativa(vizinho, blocoInimigo);
			if (distancia > maiorDistancia && !temParede(vizinho.linha, vizinho.coluna)) {
				melhorDestino = vizinho;
				maiorDistancia = distancia;
			}
		}
		return melhorDestino;
	}

	private ArrayList<BlocoCenario> getVizinhos(BlocoCenario blocoAtual) {
		ArrayList<BlocoCenario> vizinhos = new ArrayList<>();

		int[][] direcoes = {
				{ -1, 0 }, // Norte
				{ 1, 0 }, // Sul
				{ 0, -1 }, // Oeste
				{ 0, 1 } // Leste
		};

		for (int[] direcao : direcoes) {
			int linhaVizinho = blocoAtual.linha + direcao[0];
			int colunaVizinho = blocoAtual.coluna + direcao[1];
			if (validaPosicao(linhaVizinho, colunaVizinho)) {
				BlocoCenario vizinho = matrizBlocos[linhaVizinho][colunaVizinho];
				if (vizinho.retornaCustoBloco() != Integer.MAX_VALUE) {
					vizinhos.add(vizinho);
				}
			}
		}
		return vizinhos;
	}

	private boolean validaPosicao(int linha, int coluna) {
		return linha >= 0 && linha < matrizBlocos.length && coluna >= 0 && coluna < matrizBlocos[0].length;
	}

	@Override
	public void executa() {
		initState();
		myDeathTank();
	}

}
