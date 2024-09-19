/***********************************************************************
*Nome: CGerenciadorJanela
*Descri��o: Classe responsavel pela cria��o e configura��o da Janela
*Autor: Silvano Malfatti
*Data: 27/05/08
************************************************************************/

/*Declara��o de pacote*/
package javaengine;

/*Bibliotecas Utilizadas*/
import java.awt.Cursor;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;


public class CGerenciadorJanela extends JFrame
{
	//Atributos
	private int largura, altura;
	private int posicaoX, posicaoY;
	private int coresPorPixel;
	private String tituloJanela=null;
	private boolean bTelaCheia;
	private GraphicsDevice dispositivoGrafico = null;
	private BufferedImage bufferSecundario = null;
	private Cursor cursor = null;
	private CGerenciadorCentral pGerenciador = null;
	
	/***********************************************************
	*Nome: CGerenciadorJanela()
	*Descri��o: Construtor da classe
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	CGerenciadorJanela(CGerenciadorCentral gerenciador)
	{
		tituloJanela = "UnderGames Engine";
		pGerenciador = gerenciador;
		dispositivoGrafico = null;
		largura = 800;
		altura = 600;
		coresPorPixel = 16;
		bTelaCheia = true;
		posicaoX = 0;
		posicaoY = 0;
	}
	
	/***********************************************************
	*Name: getTelaCheia()
	*Description: retorna se a janela est� ou nao em tela cheia
	*Parametros: Nenhum
	*Retorno: boolean
	************************************************************/
	public boolean getTelaCheia()
	{
		return bTelaCheia;
	}
	
	/***********************************************************
	*Name: getDispositivoGrafico()
	*Description: retorna o dispositivo gr�fico
	*Parametros: Nenhum
	*Retorno: GraphicsDevice
	************************************************************/
	public GraphicsDevice getDispositivoGrafico()
	{
		return dispositivoGrafico;
	}
	
	/***********************************************************
	*Nome: getBufferSecundario()
	*Descri��o: retorna o backbuffer
	*Parametros: Nenhum
	*Retorno: BufferedImage
	************************************************************/
	public BufferedImage getBufferSecundario()
	{
		return bufferSecundario;
	}
	
	/***********************************************************
	*Name: setPosicao()
	*Description: Configura a posicao da janela
	*Parametros: int, int
	*Retorno: Nenhum
	************************************************************/
	public void setPosicao(int pPosX, int pPosY)
	{
		posicaoX = pPosX;
		posicaoY = pPosY;
	}
	
	/***********************************************************
	*Name: setTituloJanela()
	*Description: Configura o titulo da janela
	*Parametros: String
	*Retorno: Nenhum
	************************************************************/
	public void setTituloJanela(String pTitle)
	{
		tituloJanela = pTitle;
	}
	
	/***********************************************************
	*Name: setTelaCheia()
	*Description: configura o modo fullscreen
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void setTelaCheia(boolean telaCheia)
	{
		bTelaCheia = telaCheia;
	}
	
	/***********************************************************
	*Name: setResolucao()
	*Descri��o: configura a resolu��o da janela
	*Parametros: int, int, int
	*Retorno: Nenhum
	************************************************************/
	public void setResolucao(int pLargura, int pAltura, int pPixelsPorCor)
	{
		largura = pLargura;
		altura = pAltura;
		coresPorPixel = pPixelsPorCor;
	}
	
	/***********************************************************
	*Nome: inicializaJanela()
	*Descri��o: faz as inicializa��es
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void inicializaJanela()
	{
		//Cria o backbuffer
		bufferSecundario = new BufferedImage(largura,altura,BufferedImage.TYPE_INT_RGB);
		
		//Esconde o ponteiro do mouse
		cursor = Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().getImage(""), new Point(0,0),"invisible");
		this.setCursor(cursor);
		
		//Solicita o foco para a janela
		requestFocus();
		
		/*Trata o bot�o fechar no caso de modo janela*/
		addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e)
			{		
				pGerenciador.terminaJogo();
			} 						
		}
		);
		
		//Exibe a janela
		mostraJanela();
	}
	
	/***********************************************************
	*Name: liberaRecursos()
	*Description: libera recursos
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void liberaRecursos()
	{
		if (bTelaCheia)
		{
			dispositivoGrafico.setFullScreenWindow(null);
			dispositivoGrafico = null;
			bufferSecundario = null;
			cursor = null;
			pGerenciador = null;
		}
	}
	
	/***********************************************************
	*Name: mostraJanela()
	*Description: mostra a janela
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	private void mostraJanela()
	{
		//Verifica o modo de v�deo
		if (bTelaCheia)
		{
			//Modo tela cheia
			//Configura o estilo da janela
			setUndecorated(true);
			setResizable(false);
			setLocation(0,0);
			
			//Configura o modo FullScreen
			DisplayMode displayMode = new DisplayMode(largura,altura,coresPorPixel,DisplayMode.REFRESH_RATE_UNKNOWN);
			GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
			dispositivoGrafico = environment.getDefaultScreenDevice();
			dispositivoGrafico.setFullScreenWindow(this);
			dispositivoGrafico.setDisplayMode(displayMode);
		}
		else
		{
			//Modo Janela
			setTitle(tituloJanela);
			setLocation(posicaoX,posicaoY);
			setSize(largura,altura);
			setVisible(true);
			setResizable(false);
		}
	}
	
	/***********************************************************
	*Name: paint()
	*Description: m�todo da classe JFrame que repinta a janela
	*Parametros: Graphics2D
	*Retorno: Nenhum
	************************************************************/
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(bufferSecundario,0,0,null);
	}
}
