/***********************************************************************
*Nome: CCamada
*Descri��o: classe respons�vel pela cria��o de uma camada no cen�rio
*Autor: Silvano Malfatti
*Data: 28/05/08
************************************************************************/

/*Declara��o de pacote*/
package javaengine;

/*Bibliotecas utilizadas*/
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.net.URL;

public class CCamada 
{
	/*Atributos da classe*/
	private CGerenciadorCentral pGerenciador;
	public CQuadro[] vetorBlocos;
	public CVetor2D tamanhoBloco;
	public CVetor2D deslocamento;
	public CVetor2D tamanhoCamada;
	private CVetor2D velocidade;
	private BufferedImage imagemTile;
	private CIndiceCor[] vetorIndicesCores;
	private boolean bVisivel;
	public boolean bDesenhoAutomatico;
	
	/***********************************************************
	*Nome: CCamada()
	*Descri��o: Construtor da classe
	*Parametros: CGerenciador, CVetor2D
	*Retorno: Nenhum
	************************************************************/
	public CCamada(CGerenciadorCentral gerenciador, CVetor2D pTamanhoBloco)
	{
		vetorBlocos = null;
		imagemTile = null;
		vetorIndicesCores = null;
		tamanhoBloco = pTamanhoBloco;
		pGerenciador = gerenciador;
		deslocamento = new CVetor2D();
		velocidade = new CVetor2D();
		tamanhoCamada = new CVetor2D();
		bVisivel = true;
		bDesenhoAutomatico = false;
	}
	
	/***********************************************************
	*Nome: CCamada
	*Descri��o: Construtor da classe
	*Parametros:CGerenciador, CVetor2D, CVetor2D
	*Retorno: Nenhum
	************************************************************/
	public CCamada(CGerenciadorCentral gerenciador, CVetor2D pTamanhoCamada, CVetor2D pTamanhoBloco)
	{
		pGerenciador = gerenciador;
		tamanhoCamada = pTamanhoCamada;
		vetorIndicesCores = null;
		imagemTile = null;
		vetorBlocos = new CQuadro[(int)(pTamanhoCamada.getX() * pTamanhoCamada.getY())];
		for (int iIndex=0; iIndex<vetorBlocos.length; iIndex++)
		{
			vetorBlocos[iIndex] = new CQuadro();
		}
		tamanhoBloco = pTamanhoBloco;
		velocidade = new CVetor2D();
		deslocamento = new CVetor2D();
		bVisivel = true;
		bDesenhoAutomatico = false;
	}
	
	/***********************************************************
	*Nome: setIndicesCores()
	*Descri��o: Configura o vetor de cores e �ndices
	*Parametros: CIndiceCor[]
	*Retorno: Nenhum
	************************************************************/
	public void setIndiceCores(CIndiceCor[] pIndicesCores)
	{
		vetorIndicesCores = pIndicesCores;
	}
	
	/***********************************************************
	*Nome: getTamanhoCamada()
	*Descri��o: retorna o tamanho da camada
	*Parametros: Nenhum
	*Retorno: CVetor2D
	************************************************************/
	public CVetor2D getTamahoCamada()
	{
		return tamanhoCamada;
	}
	
	/***********************************************************
	*Nome: getTamanhoBloco()
	*Descri��o: retorna o tamanho dos blocos da camada
	*Parametros: Nenhum
	*Retorno: CVetor2D
	************************************************************/
	public CVetor2D getTamanhoBloco()
	{
		return tamanhoBloco;
	}
	
	/***********************************************************
	*Nome: getDeslocamento()
	*Descri��o: retorna o deslocamento da camada
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public CVetor2D getDeslocamento()
	{
		return deslocamento;
	}
	
	/***********************************************************
	*Nome: getIndiceBloco()
	*Descri��o: Obtem o �ndice do tile conforme a cor RGB
	*Parametros: int, int, int
	*Retorno: int
	************************************************************/
	private int getIndiceBlocoPorCores(int R, int G, int B)
	{
		for (int iIndex=0; iIndex<vetorIndicesCores.length; iIndex++)
		{
			if (vetorIndicesCores[iIndex].getCor().getRed() == R && vetorIndicesCores[iIndex].getCor().getGreen() == G && vetorIndicesCores[iIndex].getCor().getBlue() == B)
			{
				return vetorIndicesCores[iIndex].getIndiceQuadro();
			}
		}
		return -1;
	}
	
	/***********************************************************
	*Nome: setIndiceBloco()
	*Descri��o: Permite configurar um bloco individualmente
	*Parametros: int, int
	*Retorno: Nenhum
	************************************************************/
	public void setIndiceBloco(int pIndiceBloco, int pIndiceTile)
	{
		vetorBlocos[pIndiceBloco].setQuadro(pIndiceTile);
	}
	
	/***********************************************************
	*Nome: getIndiceVetorBlocos()
	*Descri��o: retorna o �ndice do bloco requisitado no vetor de blocos
	*Parametros: int
	*Retorno: int
	************************************************************/
	public int getIndiceVetorBlocos(int pIndiceBloco)
	{
		return vetorBlocos[pIndiceBloco].getQuadro();
	}
	
	/***********************************************************
	*Nome: setVisivel()
	*Descri��o: Configura se a camada est� visivel ou n�o
	*Parametros: boolean
	*Retorno: Nenhum
	************************************************************/
	public void setVisivel(boolean pVisivel)
	{
		bVisivel = pVisivel;
	}
	
	/***********************************************************
	*Nome: getVisivel()
	*Descri��o: retorna se a camada est� visivel ou nao
	*Parametros: Nenhum
	*Retorno: boolean
	************************************************************/
	boolean getVisivel()
	{
		return bVisivel;
	}
	
	/***********************************************************
	*Nome: setImagemTile()
	*Descri��o: configura a imagem que ser� utilizada como tiles
	*Parametros: String
	*Retorno: void
	************************************************************/
	public void setImagemTile(URL nomeArquivo)
	{
		imagemTile = pGerenciador.gerenciadorGrafico.carregaImagem(nomeArquivo).getImagem();
	}
	
	/***********************************************************
	*Nome: setVelocidade()
	*Descri��o: configura a velocidade de deslocamento da camada
	*Parametros: CVetor2D
	*Retorno: Nenhum
	************************************************************/
	public void setVelocidade(CVetor2D pVelocidade)
	{
		velocidade = pVelocidade;
	}
	
	/***********************************************************
	*Nome: deslocaCamada()
	*Descri��o: desloca a camada conforme o vetor velocidade
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void deslocaCamada()
	{
		deslocamento.setX(deslocamento.getX() + velocidade.getX());
		deslocamento.setY(deslocamento.getY() + velocidade.getY());
	}
	
	/***********************************************************
	*Nome: desenhaBloco()
	*Descri��o: desenha um bloco no buffer gr�fico
	*Parametros: int, int, int, Graphics2D
	*Retorno: Nenhum
	************************************************************/
	private void desenhaBloco(int indiceBloco, int x, int y, Graphics2D g2d)
	{
		int iColunas = (int)(imagemTile.getWidth() / tamanhoBloco.getX());
		
		int iTamanhoX = (int)tamanhoBloco.getX();
		int iTamanhoY = (int)tamanhoBloco.getY();
		
		int fx = (int)((indiceBloco % iColunas) * iTamanhoX);
		int fy = (int)((indiceBloco / iColunas) * iTamanhoY);
		
		g2d.drawImage(imagemTile ,x, y, x+iTamanhoX, y+iTamanhoY ,fx, fy, fx+iTamanhoX, fy+iTamanhoY, null);
	}
	
	/***********************************************************
	*Nome: criaCamada()
	*Descri��o: Cria uma camada com base em uma imagem
	*Parametros: String
	*Retorno: Nenhum
	************************************************************/
	public void criaCamada(URL nomeArquivo)
	{
		BufferedImage imagemCores = pGerenciador.gerenciadorGrafico.carregaImagem(nomeArquivo).getImagem();
		
		//Testa se a imagem foi carregada
		if (imagemCores==null)
		{
			return;
		}
		
		//Configura o tamanho da layer conforme a imagem
		tamanhoCamada.setX(imagemCores.getWidth());
		tamanhoCamada.setY(imagemCores.getHeight());
		
		//Cria o vetor de bricks
		vetorBlocos = new CQuadro[(int)(tamanhoCamada.getX() * tamanhoCamada.getY())];
		
		//Obtem o modelo de cores da imagem
		ColorModel modeloCores = imagemCores.getColorModel();
		
		//Configura os bricks da layer conforme a cor da imagem
		for (int iIndex = 0;  iIndex < imagemCores.getWidth(); iIndex++)
		{
			for (int jIndex = 0; jIndex < imagemCores.getHeight(); jIndex++)
			{
				int index = getIndiceBlocoPorCores(modeloCores.getRed(imagemCores.getRGB(iIndex,jIndex)),modeloCores.getGreen(imagemCores.getRGB(iIndex,jIndex)), modeloCores.getBlue(imagemCores.getRGB(iIndex,jIndex)));
				if (index >= 0)
				{
					vetorBlocos[iIndex + (jIndex*imagemCores.getWidth())] = new CQuadro();
					vetorBlocos[iIndex + (jIndex*imagemCores.getWidth())].setQuadro(index);
				}
				else
				{
					vetorBlocos[iIndex + (jIndex*imagemCores.getWidth())] = null;
				}
			}
		}
	}
	
	/***********************************************************
	*Nome:desenhaCamada()
	*Descri��o: desenha a camada no buffer gr�fico
	*Parametros: Graphics2D
	*Retorno: Nenhum
	************************************************************/
	public void  desenhaCamada(Graphics2D g2d)
	{
		int iBlocoX = 0;
		int iBlocoY = 0;
		double fPosicaoX = 0.0f;
		double deslocamentoX = deslocamento.getX();
		double deslocamentoY = deslocamento.getY();
		double tamanhoCamadaX = tamanhoCamada.getX();
		double tamanhoCamadaY = tamanhoCamada.getY();
		
		//Retorna se a layer n�o estiver vis�vel
		if (!bVisivel)
		{
			return;
		}
		
		//Calcula o in�cio da layer em x caso offset seja menor que zero
		if (deslocamentoX > 0)
		{	
			int mult = (int)Math.ceil(Math.abs(deslocamentoX)/tamanhoBloco.getX());
			iBlocoX = ((int)tamanhoCamadaX - ((mult % (int)tamanhoCamadaX))) == (int)tamanhoCamadaX ? 0 : ((int)tamanhoCamadaX - ((mult % (int)tamanhoCamadaX))); 
			deslocamentoX -= mult * tamanhoBloco.getX();
		}
		//Guarda o in�cio do offset e o brick inicial em X
		fPosicaoX = deslocamentoX;
		
		//Calcula o in�cio da layer em y caso offset seja menor que zero
		if (deslocamentoY > 0)
		{
			int mult = (int)Math.ceil(Math.abs(deslocamentoY)/tamanhoBloco.getY());
			iBlocoY = ((int)tamanhoCamadaY - ((mult % (int)tamanhoCamadaY))) == (int)tamanhoCamadaY ? 0 : ((int)tamanhoCamadaY - ((mult % (int)tamanhoCamadaY))); 
			deslocamentoY -= mult * tamanhoBloco.getY();
		}
		
		//Desenha todos os bricks da layer
		for(int iStartX = iBlocoX; deslocamentoY < pGerenciador.gerenciadorJanela.getHeight(); iBlocoY = (iBlocoY+1)%(int)tamanhoCamadaY, deslocamentoY += tamanhoBloco.getY(),iBlocoX = iStartX,deslocamentoX = (int)fPosicaoX)
		{
			for( ; deslocamentoX < pGerenciador.gerenciadorJanela.getWidth(); iBlocoX = (iBlocoX+1)%(int)tamanhoCamadaX, deslocamentoX += tamanhoBloco.getX())
			{	
				if (vetorBlocos[iBlocoX + (iBlocoY*(int)tamanhoCamadaX)]!=null)
				{
					desenhaBloco(vetorBlocos[iBlocoX + (iBlocoY*(int)tamanhoCamadaX)].getQuadro(), (int)deslocamentoX, (int)deslocamentoY, g2d);	
				}
			}
		}
	}
	
	/***********************************************************
	*Name:liberaRecursos()
	*Description: Libera recursos
	*Params: void
	*Return: Nenhum
	************************************************************/
	public void liberaRecursos()
	{
		pGerenciador = null;
		vetorBlocos = null;
		tamanhoBloco = null;
		deslocamento = null;
		tamanhoCamada = null;
		velocidade = null;
		imagemTile = null;
		vetorIndicesCores = null;
	}
}
