package TankSimulation;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import TankImplementation.MyTank;
import TankImplementation.EnemyTank;
import javaengine.CFase;
import javaengine.CGerenciadorCentral;
import javaengine.CQuadro;
import javaengine.CSprite;
import javaengine.CTempo;
import javaengine.CVetor2D;


public class TankArena extends CFase
{
	public final static int QUANTIDADE_BLOCOS = 10;
	public final static int TAMANHO_QUADRO = 70;
	public BlocoCenario[][] matrizBlocos = new BlocoCenario[QUANTIDADE_BLOCOS][QUANTIDADE_BLOCOS];
	public CSprite powerUpSelecionado = null;
    public CSprite spriteEscudo = null;
	private final int VELTIRO = 5;
	private ArrayList<CSprite> vetTiros = null;
	private CSprite mouse = null;
	private Tank tank1 = null;
	private Tank tank2 = null;
	private CSprite powerUpMunicao = null;
	private CSprite powerUpEscudo = null;
	private CTempo tempoPowerUp = null;
	private CTempo tempoEscudo = null;
	private int estadoPowerUp = 0;
	private Random sorteio = new Random();
	private ArrayList<BlocoCenario> listaBlocos = new ArrayList();
	private boolean start = false;
	
	TankArena(CGerenciadorCentral pManager)
	{
		super(pManager);
	}
	
	public void inicializa()
	{
		criaCena();
		carregaPowerUpMunicao();
		carregaPowerUpEscudo();
		
		CSprite[][] tanques = carregaTanques();
		
		tank1 = new EnemyTank(tanques[0], this);
		tank1.configuraPosicaoInicialTanque(matrizBlocos[9][9].imagemBloco.posicao);
		
		tank2 = new MyTank(tanques[1], this);
		tank2.configuraPosicaoInicialTanque(matrizBlocos[1][0].imagemBloco.posicao);
		
		carregaSpriteEscudo();
		criaMouse();
		pGerenciador.gerenciadorGrafico.carregaImagem(getClass().getResource("imagens/Canhao4.png"));
		
		vetTiros = new ArrayList();
		
		tempoPowerUp = new CTempo(10000);
		tempoPowerUp.inicia(this.pGerenciador.gerenciadorTempo);
		
		tempoEscudo = new CTempo(0);
		tempoEscudo.inicia(this.pGerenciador.gerenciadorTempo);
		
		tank1.configuraInimigo(tank2);
		tank2.configuraInimigo(tank1);
	}
	
	private CSprite[][] carregaTanques() {
		CSprite[][] tanques = new CSprite[2][2];
		
		CQuadro[] quads0 = new CQuadro[1];
		quads0[0] = new CQuadro();
		quads0[0].setQuadro(0);
				
		//Cria os sprites dos tanques
		tanques[0][0] =  this.criaSprite(pGerenciador, null, new CVetor2D(57,50), true);
		tanques[0][1] =  this.criaSprite(pGerenciador, null, new CVetor2D(92,30), true);
		
		tanques[0][0].configuraImagemSprite(getClass().getResource("imagens/Tanque3.png"));
		tanques[0][1].configuraImagemSprite(getClass().getResource("imagens/Canhao3.png"));
		
		tanques[0][0].criaAnimacao(1,true,quads0);
		tanques[0][0].configuraAnimacaoAtual(0);
		
		tanques[0][1].criaAnimacao(1,true,quads0);
		tanques[0][1].configuraAnimacaoAtual(0);
		
		tanques[1][0] =  this.criaSprite(pGerenciador, null, new CVetor2D(57,50), true);
		tanques[1][1] =  this.criaSprite(pGerenciador, null, new CVetor2D(92,30), true);
		
		tanques[1][0].configuraImagemSprite(getClass().getResource("imagens/Tanque4.png"));
		tanques[1][1].configuraImagemSprite(getClass().getResource("imagens/Canhao4.png"));
		
		tanques[1][0].criaAnimacao(1,true,quads0);
		tanques[1][0].configuraAnimacaoAtual(0);
		
		tanques[1][1].criaAnimacao(1,true,quads0);
		tanques[1][1].configuraAnimacaoAtual(0);
		
		return tanques;
	}
	
	private void carregaPowerUpEscudo() {
		
		CQuadro[] quads0 = new CQuadro[1];
		quads0[0] = new CQuadro();
		quads0[0].setQuadro(0);
		
		powerUpEscudo = this.criaSprite(pGerenciador, null, new CVetor2D(60,60), true);
		powerUpEscudo.configuraImagemSprite(getClass().getResource("imagens/escudo.png"));
		powerUpEscudo.criaAnimacao(1,true,quads0);
		powerUpEscudo.configuraAnimacaoAtual(0);
		powerUpEscudo.bVisivel = false;
		//escudo.posicao.setXY(210, 140);
	}
	
		private void carregaSpriteEscudo() {
		
		CQuadro[] quads0 = new CQuadro[1];
		quads0[0] = new CQuadro();
		quads0[0].setQuadro(0);
		
		spriteEscudo = this.criaSprite(pGerenciador, null, new CVetor2D(75,75), true);
		spriteEscudo.configuraImagemSprite(getClass().getResource("imagens/radar.png"));
		spriteEscudo.criaAnimacao(1,true,quads0);
		spriteEscudo.configuraAnimacaoAtual(0);
		spriteEscudo.bVisivel = false;
		//escudo.posicao.setXY(210, 140);
	}
	
		private void carregaPowerUpMunicao() {
		
		CQuadro[] quads0 = new CQuadro[1];
		quads0[0] = new CQuadro();
		quads0[0].setQuadro(0);
		
		powerUpMunicao = this.criaSprite(pGerenciador, null, new CVetor2D(60,60), true);
		powerUpMunicao.configuraImagemSprite(getClass().getResource("imagens/municao.png"));
		powerUpMunicao.criaAnimacao(1,true,quads0);
		powerUpMunicao.configuraAnimacaoAtual(0);
		powerUpMunicao.bVisivel = false;
		//municao.posicao.setXY(420, 420);
	}
	
	public void criaTiro(CSprite tank, CSprite canhao)
	{
		
		CQuadro[] quads0 = new CQuadro[1];
		quads0[0] = new CQuadro();
		quads0[0].setQuadro(0);
		
		CSprite novoTiro = this.criaSprite(pGerenciador, null, new CVetor2D(16,16), true);
		novoTiro.configuraImagemSprite(getClass().getResource("imagens/tiro.png"));
		novoTiro.posicao.setXY(tank.posicao.getX(), tank.posicao.getY());
		novoTiro.fAngle = canhao.fAngle;
		novoTiro.posicao.setXY(novoTiro.posicao.getX() + 50 * Math.cos(Math.PI*novoTiro.fAngle/180.0), novoTiro.posicao.getY() + 50 * Math.sin(Math.PI*novoTiro.fAngle/180.0));
		novoTiro.velocidade.setXY(VELTIRO, VELTIRO);
		novoTiro.criaAnimacao(1,true,quads0);
		novoTiro.configuraAnimacaoAtual(0);
		vetTiros.add(novoTiro);
	}
	
	private BlocoCenario sorteiaPosicaoPowerUp() {
		listaBlocos.clear();
		
		for (int linha = 0; linha < matrizBlocos.length; linha++) {
			for (int coluna = 0; coluna < matrizBlocos.length; coluna++) {
				if (matrizBlocos[linha][coluna].retornaCustoBloco() == 0) {
					listaBlocos.add(matrizBlocos[linha][coluna]);
				}	
			}
		}
		return listaBlocos.get(sorteio.nextInt(listaBlocos.size()));
	}
	
	private void sorteiaPowerUp() {
		tempoPowerUp.atualiza();
		if (tempoPowerUp.fimIntervalo()) {
			if (estadoPowerUp == 0) {
				estadoPowerUp = 1;
				tempoPowerUp.reinicia(20000);
				if ((tank1.escudo || tank2.escudo) || sorteio.nextInt(100) > 60) {
					powerUpSelecionado = powerUpMunicao;
				} else {
					powerUpSelecionado = powerUpEscudo;
				}
				BlocoCenario bloco = sorteiaPosicaoPowerUp();
				powerUpSelecionado.bVisivel = true;
				powerUpSelecionado.posicao.setXY(bloco.imagemBloco.posicao.getX(), bloco.imagemBloco.posicao.getY());
			}
			else {
				estadoPowerUp = 0;
				tempoPowerUp.reinicia(10000);
				powerUpSelecionado.bVisivel = false;
				powerUpSelecionado = null;
			}
		}
	}
	
	private void verificaColisaoPowerUp() {
		if (powerUpSelecionado != null && powerUpSelecionado.bVisivel) {
			if (powerUpSelecionado.colide(tank1.tank[0])) {
				if (powerUpSelecionado == powerUpEscudo) {
					tank1.iniciaEscudo();
				} else {
					tank1.qtdTiros += 30;
				}
				powerUpSelecionado.bVisivel = false;
			} else if (powerUpSelecionado.colide(tank2.tank[0])) {
				if (powerUpSelecionado == powerUpEscudo) {
					tank2.iniciaEscudo();
				} else {
					tank2.qtdTiros += 30;
				}
				powerUpSelecionado.bVisivel = false;
			}
		}
	}
	
	private void atualizaTiros()
	{
		for (int iIndex = vetTiros.size()-1; iIndex>=0; iIndex--)
		{
			vetTiros.get(iIndex).posicao.setXY(vetTiros.get(iIndex).posicao.getX() + vetTiros.get(iIndex).velocidade.getX() * Math.cos(Math.PI*vetTiros.get(iIndex).fAngle/180.0), 
					                           vetTiros.get(iIndex).posicao.getY() + vetTiros.get(iIndex).velocidade.getY() * Math.sin(Math.PI*vetTiros.get(iIndex).fAngle/180.0));
			verificaColisaoTiroParedes(vetTiros.get(iIndex));
		}
		
		for (int iIndex = vetTiros.size()-1; iIndex >= 0; iIndex--) {
			
			if (spriteEscudo.bVisivel && spriteEscudo.colide(vetTiros.get(iIndex))) {
				vetTiros.get(iIndex).bVisivel = false;
				vetTiros.remove(vetTiros.get(iIndex));
				continue;
			}
			
			if (!tank1.invencivel && vetTiros.get(iIndex).colide(tank1.tank[0])) {
				if (!tank1.tankAtingido()){
					tank1.atingido();
				}
				vetTiros.get(iIndex).bVisivel = false;
				vetTiros.remove(vetTiros.get(iIndex));
				
			} else if (!tank2.invencivel && vetTiros.get(iIndex).colide(tank2.tank[0])) {
				if (!tank2.tankAtingido()) {
					tank2.atingido();
				}
				vetTiros.get(iIndex).bVisivel = false;
				vetTiros.remove(vetTiros.get(iIndex));
			}
		}
	}
	
	private void verificaColisaoTiroParedes(CSprite tiro) {
		for (int linha = 0; linha < matrizBlocos.length; linha++) {
			for (int coluna = 0; coluna < matrizBlocos.length; coluna++) {
				if (matrizBlocos[linha][coluna].imagemBloco.getIndiceAnimacaoAtual()==0 && tiro.colide(matrizBlocos[linha][coluna].imagemBloco)) {
					tiro.bVisivel = false;
					vetTiros.remove(tiro);
					return;
				}
			}
		}
	}
	
	private void limpaCaminho() {
		for (int linha = 0; linha < QUANTIDADE_BLOCOS; linha ++) {
			for (int coluna = 0; coluna < QUANTIDADE_BLOCOS; coluna++) { 
				
				if (matrizBlocos[linha][coluna].imagemBloco.getIndiceAnimacaoAtual() == 2) {
					matrizBlocos[linha][coluna].imagemBloco.configuraAnimacaoAtual(1);
				}
			
			}
		}
	}
	
	private void criaCena() {
		for (int linha = 0; linha < QUANTIDADE_BLOCOS; linha ++) {
			for (int coluna = 0; coluna < QUANTIDADE_BLOCOS; coluna++) { 
				matrizBlocos[linha][coluna] = new BlocoCenario(linha, coluna);
				matrizBlocos[linha][coluna].imagemBloco = criaSpriteCenario();
				matrizBlocos[linha][coluna].imagemBloco.posicao.setXY(TAMANHO_QUADRO / 2 + TAMANHO_QUADRO * coluna, TAMANHO_QUADRO / 2 + TAMANHO_QUADRO * linha);
			}
		}
	}
	
	private void criaMouse() {
		CQuadro[] quads0 = new CQuadro[1];
		quads0[0] = new CQuadro();
		quads0[0].setQuadro(0);
				
		mouse =  this.criaSprite(pGerenciador, null, new CVetor2D(35,35), true);
		mouse.configuraImagemSprite(getClass().getResource("imagens/hand.png"));
		mouse.criaAnimacao(1,true,quads0);
		mouse.configuraAnimacaoAtual(0);
	}
	
	private CSprite criaSpriteCenario() {
		
		CQuadro[] bloco0 = new CQuadro[1];
		bloco0[0] = new CQuadro();
		bloco0[0].setQuadro(0);
		
		CQuadro[] bloco1 = new CQuadro[1];
		bloco1[0] = new CQuadro();
		bloco1[0].setQuadro(1);
		
		CQuadro[] bloco2 = new CQuadro[1];
		bloco2[0] = new CQuadro();
		bloco2[0].setQuadro(2);
		
		CSprite sprite = this.criaSprite(pGerenciador, null, new CVetor2D(70,70), true);
		sprite.configuraImagemSprite(getClass().getResource("imagens/tiles.png"));
		sprite.criaAnimacao(1,true,bloco0);
		sprite.criaAnimacao(1,true,bloco1);
		sprite.criaAnimacao(1,true,bloco2);
		sprite.configuraAnimacaoAtual(1);
		return sprite;
	}
	
	private void atualizaMouse() {
		mouse.posicao.setXY(this.pGerenciador.gerenciadorDispositivos.getPosicaoMouse().getX() , this.pGerenciador.gerenciadorDispositivos.getPosicaoMouse().getY());
		
		int linha = (int)mouse.posicao.getY() / TAMANHO_QUADRO;
		int coluna = (int)mouse.posicao.getX() / TAMANHO_QUADRO;
		
		if (linha >= QUANTIDADE_BLOCOS || coluna >= QUANTIDADE_BLOCOS) {
			return;
		}
		
		if (pGerenciador.gerenciadorDispositivos.teclaJaPressionada(KeyEvent.VK_P)) {
			matrizBlocos[linha][coluna].imagemBloco.configuraAnimacaoAtual(0);
		}
		else if (pGerenciador.gerenciadorDispositivos.teclaJaPressionada(KeyEvent.VK_G)) {
			matrizBlocos[linha][coluna].imagemBloco.configuraAnimacaoAtual(1);
		}
	}

	public void executa()
	{
		atualizaTeclas();
		atualizaMouse();
		tank1.atualizaPosicaoSprites();
		tank1.atualizaTempos();
		tank2.atualizaPosicaoSprites();
		tank2.atualizaTempos();
		
		if (!start)
			return;
		
		atualizaTiros();
		sorteiaPowerUp();
		verificaColisaoPowerUp();
		
		tank1.executa();
		tank1.atualizaMovimento();
		tank1.atualizaAtingido();
		tank1.atualizaEscudo();
		
		tank2.executa();
		tank2.atualizaMovimento();
		tank2.atualizaAtingido();
		tank2.atualizaEscudo();
	}
	
	private void atualizaTeclas() {
		if (pGerenciador.gerenciadorDispositivos.teclaJaPressionada(KeyEvent.VK_SPACE)) {
			start = true;
		}
		
		if (pGerenciador.gerenciadorDispositivos.teclaJaPressionada(KeyEvent.VK_ENTER)) {
			start = false;
		}
	}
	
	public void liberaRecursos()
	{
		super.liberaRecursos();
	}
}
