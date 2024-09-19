/***********************************************************************
*Nome: CSprite
*Descri��o: classe respons�vel pela cria��o de sprites
*Autor: Silvano Malfatti
*Data: 07/06/08
************************************************************************/

/*Declara��o de pacote*/
package javaengine;

import java.awt.Composite;
/*Bibliotecas utilizadas*/
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Vector;

public class CSprite 
{
	/*Atributos da classe*/
	public CVetor2D velocidade = null;
	public CVetor2D posicao = null;
	public CVetor2D posicaoAnterior = null;
	public CVetor2D tamahoQuadro = null;
	public CVetor2D inicioMovimento = null;
	public CVetor2D fimMovimento = null;
	public BufferedImage imagemSprite = null;
	public Vector vetorAnimacoes = null;
	public CGerenciadorCentral pGerenciador = null;
	public CCamada camada = null;
	int iAnimacaoAtual;
	public boolean bVisivel;
	public boolean bPulo;
	public boolean bAutoDesenho;
	private boolean bEspelhado = false;
	public CTempo temporizadorMovitomento = null;
	public float fAngle = 0.0f;
	public float fScale = 1.0f;
	private AffineTransform transform1 = null;
	private AffineTransform transform2 = null;
	
	/***********************************************************
	*Nome: CSprite()
	*Descri��o: Construtor da classe
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public CSprite(CGerenciadorCentral gerenciador, CCamada pCamada, CVetor2D tamanhoQuadro)
	{
		velocidade = new CVetor2D();
		posicao = new CVetor2D();
		posicaoAnterior = new CVetor2D();
		inicioMovimento = new CVetor2D();
		fimMovimento = new CVetor2D();
		vetorAnimacoes = new Vector();
		iAnimacaoAtual=-1;
		bVisivel = true;
		bPulo=false;
		bAutoDesenho = false;
		pGerenciador = gerenciador;
		temporizadorMovitomento = new CTempo();
		temporizadorMovitomento.inicia(pGerenciador.gerenciadorTempo);
		temporizadorMovitomento.reinicia(0);
		camada = pCamada;
		tamahoQuadro = new CVetor2D(tamanhoQuadro.getX(), tamanhoQuadro.getY());
		transform1 = new AffineTransform();
		transform2 = new AffineTransform();
		fAngle = 0.0f;
	}
	
	/***********************************************************
	*Nome: retornaQuadro()
	*Descri��o: retorna o quadro que envolve o sprite
	*Parametros: Nenhum
	*Retorno: Rectangle
	************************************************************/
	public Rectangle retornaQuadro()
	{
		return new Rectangle((int)posicao.getX(), (int)posicao.getY(), (int)tamahoQuadro.getX(), (int)tamahoQuadro.getY());
	}
	
	/***********************************************************
	*Nome: movimentoEncerrado()
	*Descri��o: retorna o estado do interpolador de posi��o
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public boolean movimentoEncerrado()
	{
		return (temporizadorMovitomento.finalIntervalo == 0);
	}
	
	/***********************************************************
	*Nome: iniciaMovimento()
	*Descri��o: inicializa a interpola��o de posi��o
	*Parametros: int, UGEVector2D
	*Retorno: Nenhum
	************************************************************/
	public void iniciaMovimento(int tempo, CVetor2D novaPosicao)
	{
		inicioMovimento.setX(posicao.getX());
		inicioMovimento.setY(posicao.getY());
		
		fimMovimento = new CVetor2D(novaPosicao.getX(), novaPosicao.getY());
		
		if (tempo<=0 || (inicioMovimento == fimMovimento))
		{
			posicao.setX(fimMovimento.getX());
			posicao.setY(fimMovimento.getY());
			temporizadorMovitomento.reinicia(0);
		}
		else
		{
			temporizadorMovitomento.reinicia(tempo);
		}
	}
	
	/***********************************************************
	*Nome: atualizaMovimento()
	*Descri��o: atualiza a movimenta��o do sprite na interpola�ao
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	void atualizaMovimento()
	{
		temporizadorMovitomento.atualiza();

		if (temporizadorMovitomento.finalIntervalo !=0)
		{
			if (!temporizadorMovitomento.fimIntervalo())
			{
				posicao.setX(inicioMovimento.getX());
				posicao.setX(posicao.getX() + ((inicioMovimento.getX() < fimMovimento.getX()) ? -((inicioMovimento.getX() - fimMovimento.getX()) * temporizadorMovitomento.tempoAtual)/temporizadorMovitomento.finalIntervalo : -(Math.abs(inicioMovimento.getX() - fimMovimento.getX()) * temporizadorMovitomento.tempoAtual)/temporizadorMovitomento.finalIntervalo));

				posicao.setY(inicioMovimento.getY());
				posicao.setY(posicao.getY() + ((inicioMovimento.getY() < fimMovimento.getY()) ? -((inicioMovimento.getY() - fimMovimento.getY()) * temporizadorMovitomento.tempoAtual)/temporizadorMovitomento.finalIntervalo : -(Math.abs(inicioMovimento.getY() - fimMovimento.getY()) * temporizadorMovitomento.tempoAtual)/temporizadorMovitomento.finalIntervalo));
			}
			else
			{
				posicao = fimMovimento;
				temporizadorMovitomento.reinicia(0);
			}
		}
	}
	
	/***********************************************************
	*Nome: configuraImagemSprite()
	*Descri��o: configura a imagem que ser� utilizada pelo sprite
	*Parametros: String
	*Retorno: Nenhum
	************************************************************/
	public void configuraImagemSprite(URL nome)
	{
		imagemSprite = pGerenciador.gerenciadorGrafico.carregaImagem(nome).getImagem();
	}
	
	/***********************************************************
	*Nome: configuraEspelhamento()
	*Descri��o: configura se o sprite est� espelhado ou nao
	*Parametros: boolean
	*Retorno: Nenhum
	************************************************************/
	public void configuraEspelhamento(boolean espelhamento)
	{
		bEspelhado = espelhamento;
	}
	
	/***********************************************************
	*Nome: configuraAnimacaoAtual()
	*Descri��o: configura a anima��o que ser� disparada
	*Parametros: int
	*Retorno: Nenhum
	************************************************************/
	public void configuraAnimacaoAtual(int indiceAnimacao)
	{
		if (vetorAnimacoes.size()==0)
		{
			return;
		}
		
		if(indiceAnimacao != iAnimacaoAtual)
		{
			iAnimacaoAtual = indiceAnimacao;
			reiniciaAnimacao();
		}
	}
	
	/***********************************************************
	*Nome: getQuadroAtualAnimacao()
	*Descri��o: retorna o quadro da anima��o atual
	*Parametros: Nenhum
	*Retorno: int
	************************************************************/
	public int getQuadroAtualAnimacao()
	{
		return ((CAnimacao)vetorAnimacoes.elementAt(iAnimacaoAtual)).iQuadroAtual;
	}
	
	/***********************************************************
	*Nome: getIndiceAnimacaoAtual()
	*Descri��o: retorna a anima��o atual
	*Parametros: Nenhum
	*Retorno: int
	************************************************************/
	public int getIndiceAnimacaoAtual()
	{
		return iAnimacaoAtual;
	}
	
	/***********************************************************
	*Nome: getAnimacaoAtual()
	*Descri��o: retorna a anima��o atual
	*Parametros: Nenhum
	*Retorno: int
	************************************************************/
	public CAnimacao getAnimacaoAtual()
	{
		return ((CAnimacao)vetorAnimacoes.elementAt(iAnimacaoAtual));
	}
	
	/***********************************************************
	*Nome: getEspelhamento()
	*Descri��o: retorna o espelhamento do sprite
	*Parametros: Nenhum
	*Retorno: boolean
	************************************************************/
	public boolean getEspelhamento()
	{
		return bEspelhado;
	}
	
	/***********************************************************
	*Nome: reiniciaAnimacao()
	*Descri��o: reinicia a anima��o
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void reiniciaAnimacao()
	{
		if(iAnimacaoAtual >= 0 && iAnimacaoAtual < vetorAnimacoes.size())
		{
			((CAnimacao)vetorAnimacoes.elementAt(iAnimacaoAtual)).reinicia();
		}
	}
	
	/***********************************************************
	*Nome: atualizaSprite()
	*Descri��o: faz a atualiza��o do sprite
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void atualizaSprite()
	{
		atualizaMovimento();
		
		if((iAnimacaoAtual >= 0) && (iAnimacaoAtual < vetorAnimacoes.size()))
		{
			((CAnimacao)vetorAnimacoes.elementAt(iAnimacaoAtual)).atualiza();
		}
	}
	
	/***********************************************************
	*Nome:criaAnimacao()
	*Descri��o: cria uma anima��o para o sprite
	*Parametros: int, boolean, UGEQuad[]
	*Retorno: Nenhum
	************************************************************/
	public void criaAnimacao(int iFPS, boolean bRepeticao, CQuadro[] vetorQuadros)
	{
		CAnimacao animation= new CAnimacao(vetorQuadros);

		animation.bRepeticao = bRepeticao;
		animation.setIntervaloQuadro(iFPS, pGerenciador.gerenciadorTempo);

		vetorAnimacoes.addElement(animation);
		
		if (vetorAnimacoes.size()==1)
		{
			iAnimacaoAtual = 0;
		}
	}
	
	/***********************************************************
	*Nome: desenhaSprite()
	*Descri��o: desenha o sprite
	*Parametros: Graphics2D
	*Retorno: Nenhum
	************************************************************/
	public void desenhaSprite(Graphics2D g2d)
	{
		if (!bVisivel)
		{
			return;
		}
		if (vetorAnimacoes.size() == 0)
		{
			g2d.drawImage(imagemSprite ,(int)posicao.getX(), (int)posicao.getY(), imagemSprite.getWidth(), imagemSprite.getHeight() ,null);
		}
		else
		{
			desenhaQuadroAtual(((CAnimacao)vetorAnimacoes.elementAt(iAnimacaoAtual)).getQuadroAtual().getQuadro(),(int)posicao.getX(), (int)posicao.getY(),g2d);
		}
	}
	
	/***********************************************************
	*Nome: desenhaSprite()
	*Descri��o: desenha o sprite
	*Parametros: Graphics2D
	*Retorno: Nenhum
	************************************************************/
	public void desenhaSprite(Graphics2D g2d, CVetor2D posicao)
	{
		if (!bVisivel)
		{
			return;
		}
		if (vetorAnimacoes.size() == 0)
		{
			g2d.drawImage(imagemSprite ,(int)posicao.getX(), (int)posicao.getY(), imagemSprite.getWidth(), imagemSprite.getHeight() ,null);
		}
		else
		{
			desenhaQuadroAtual(((CAnimacao)vetorAnimacoes.elementAt(iAnimacaoAtual)).getQuadroAtual().getQuadro(),(int)posicao.getX(), (int)posicao.getY(),g2d);
		}
	}
	
	/***********************************************************
	*Nome: desenhaQuadroAtual()
	*Descri��o: desenha o sprite conforme o quadro da anima��o
	*Parametros: int, int, int, Graphics2D
	*Retorno: Nenhum
	************************************************************/
	private void desenhaQuadroAtual(int quadroAtual, int x, int y, Graphics2D g2d)
	{
		int iColunas = (int)(imagemSprite.getWidth() / tamahoQuadro.getX());
		
		int tamanhoX = (int)tamahoQuadro.getX();
		int tamanhoY = (int)tamahoQuadro.getY();
		
		int fx = (int)((quadroAtual % iColunas) * tamanhoX);
		int fy = (int)((quadroAtual / iColunas) * tamanhoY);
		
		//Zera as transforma��es
		transform1.setToIdentity();
		transform2.setToIdentity();
		
		transform1.rotate(fAngle*Math.PI/180.0f);
		transform1.translate((fScale*-tamanhoX)/2,(fScale*-tamanhoY)/2);
		transform1.scale(fScale,fScale);
		
		transform2.translate(x,y);	
		transform2.concatenate(transform1);
		
		AffineTransform transformation = g2d.getTransform();
		g2d.setTransform(transform2);
		
		if (!bEspelhado)
		{
			g2d.drawImage(imagemSprite ,0, 0, tamanhoX, tamanhoY ,fx, fy, fx+tamanhoX, fy+tamanhoY, null);
		}
		else
		{
			g2d.drawImage(imagemSprite, tamanhoX, 0, 0, tamanhoY ,fx, fy, fx+tamanhoX, fy+tamanhoY, null);
		}
		
		//Salva o estado anterior
		g2d.setTransform(transformation);
	}
	
	/***********************************************************
	*Nome: getPosicaoDesenho()
	*Descri��o: retornaa posicao que o sprite ser� desenhado
	*Parametros: Nenhum
	*Retorno: CVetor2D
	************************************************************/
	public CVetor2D getPosicaoDesenho()
	{
		CVetor2D posicaoDesenho = new CVetor2D();
		double tamanhoCamada = (camada.getTamahoCamada().getX() * camada.getTamanhoBloco().getX());
		
		posicaoDesenho.setX(posicao.getX());
		
		if (tamanhoCamada > pGerenciador.gerenciadorJanela.getWidth())
		{
			posicaoDesenho.setX(posicaoDesenho.getX() + -(tamanhoCamada - camada.getDeslocamento().getX()));
			posicaoDesenho.setX((posicaoDesenho.getX() < -tamahoQuadro.getX()) ? (Math.ceil(Math.abs(posicaoDesenho.getX()/tamanhoCamada)) * tamanhoCamada) + posicaoDesenho.getX() : posicaoDesenho.getX());
		}
		
		posicaoDesenho.setY(posicao.getY());
		
		return posicaoDesenho;
	}
	
	/***********************************************************
	*Nome: colisaoVertical()
	*Descri��o: testa a colisao em Y do sprite com os blocos da camada
	*Parametros: Nenhum
	*Retorno: boolean
	************************************************************/
	public boolean colisaoVertical()
	{
		int spriteX1 = (int)posicao.getX();
		int spriteX2 = (int)(spriteX1 + tamahoQuadro.getX());
		int spriteY1 = (int)posicao.getY();
		int spriteY2 = (int)(spriteY1 + tamahoQuadro.getY());
				
		for (int iIndex=0; iIndex < camada.getTamahoCamada().getX(); iIndex++)
		{
			for (int jIndex=0; jIndex < camada.getTamahoCamada().getY(); jIndex++)
			{
				if (camada.vetorBlocos[(jIndex*(int)camada.getTamahoCamada().getX()) + iIndex]!=null)
				{
					int blocoX1   =   (int)(iIndex * camada.getTamanhoBloco().getX());
					int blocoX2    =   (int) (blocoX1 + camada.getTamanhoBloco().getX());
					
					int blocoY1 = (int)(jIndex * camada.getTamanhoBloco().getY());
					int blocoY2 = (int)(blocoY1 + camada.getTamanhoBloco().getY());
					
					if ((spriteX2 > blocoX1 && blocoX2 > spriteX1) && (spriteY1 < blocoY2 && spriteY2 > blocoY1))
					{
						int centroSprite = (spriteY1 + spriteY2)/2;
						int centroBloco = (blocoY1 + blocoY2)/2;
						
						//Lado superior
						if (centroSprite < centroBloco)
						{
							posicao.setY(blocoY1 - tamahoQuadro.getY());
							bPulo = false;
						}
						else
						{
							posicao.setY(blocoY2);
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/***********************************************************
	*Nome: colisaoHorizontal()
	*Descri��o:  testa a colisao em X do sprite com os blocos da camada
	*Parametros: Nenhum
	*Retorno: boolean
	************************************************************/
	public boolean colisaoHorizontal()
	{
		int spriteX1 = (int)posicao.getX();
		int spriteX2 = (int)(spriteX1 + tamahoQuadro.getX());
		int spriteY1 = (int)posicao.getY();
		int spriteY2 = (int)(spriteY1 + tamahoQuadro.getY());
				
		for (int iIndex=0; iIndex < camada.getTamahoCamada().getX(); iIndex++)
		{
			for (int jIndex=0; jIndex < camada.getTamahoCamada().getY(); jIndex++)
			{
				if (camada.vetorBlocos[(jIndex*(int)camada.getTamahoCamada().getX()) + iIndex]!=null)
				{
					int blocoX1   =    iIndex * (int)camada.getTamanhoBloco().getX();
					int blocoX2    =    blocoX1 + (int)camada.getTamanhoBloco().getX();
					
					int blocoY1 = jIndex * (int)camada.getTamanhoBloco().getY();
					int blocoY2 = blocoY1 + (int)camada.getTamanhoBloco().getY();
					
					if ((spriteX2 > blocoX1 && blocoX2 > spriteX1) && (spriteY1 < blocoY2 && spriteY2 > blocoY1))
					{
						int centroSprite = (spriteX1 + spriteX2)/2;
						int centroBloco = (blocoX1 + blocoX2)/2;
						
						//Lado esquerdo
						if (centroSprite < centroBloco)
						{
							posicao.setX((blocoX1)-tamahoQuadro.getX());
						}
						else
						{
							posicao.setX((blocoX2));
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/***********************************************************
	*Nome: liberaRecursos()
	*Descri��o: libera recursos
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void liberaRecursos()
	{
		velocidade = null;
		posicao = null;
		posicaoAnterior = null;
		inicioMovimento = null;
		fimMovimento = null;
		pGerenciador = null;
		temporizadorMovitomento = null;
		camada = null;
		tamahoQuadro = null;
		
		for (int iIndex=vetorAnimacoes.size()-1; iIndex>=0; iIndex--)
		{
			((CAnimacao)vetorAnimacoes.elementAt(iIndex)).liberaRecursos();
			vetorAnimacoes.removeElementAt(iIndex);
		}
		vetorAnimacoes = null;
	}
	
	private Rectangle getQuadro()
	{
		return new Rectangle((int)(posicao.getX()-fScale*tamahoQuadro.getX()/2 + 3), (int)(posicao.getY()-fScale*tamahoQuadro.getY()/2), (int)(fScale*tamahoQuadro.getX()) - 3, (int)(fScale*tamahoQuadro.getY()));
	}
	
	public boolean colide(CSprite sprite) {
		return this.getQuadro().intersects(sprite.getQuadro());
	}
}
