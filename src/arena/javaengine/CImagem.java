/***********************************************************************
*Nome: CImagem
*Descri��o: classe respons�vel pelo carregamento de imagens
*Autor: Silvano Malfatti
*Data: 27/05/08
************************************************************************/

/*Declara��o de pacote*/
package javaengine;

/*Bibliotecas utilizadas*/
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;

public class CImagem 
{
	/*Atributos da classe*/
	private String sNome = null;
	private BufferedImage imagem = null;
	private int iTotalReferencias;
	
	/***********************************************************
	*Nome: CImagem()
	*Descri��o: Construtor da classe
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public CImagem()
	{
		sNome = "";
		iTotalReferencias = 0;
	}
	
	/***********************************************************
	*Nome: geNomeImagem()
	*Descri��o: retorna o nome da imagem
	*Parametros: Nenhum
	*Retorno: String
	************************************************************/
	public String getNomeImagem()
	{
		return sNome;
	}
	
	/***********************************************************
	*Nome: getImagem()
	*Descri��o: retorna a imagem
	*Parametros: Nenhum
	*Retorno: BufferedImage
	************************************************************/
	public BufferedImage getImagem()
	{
		return imagem;
	}
	
	/***********************************************************
	*Nome: getLarguraImagem()
	*Descri��o: retorna a largura da imagem
	*Parametros: Nenhum
	*Retorno: int
	************************************************************/
	public int getLarguraImagem()
	{
		return imagem.getWidth();
	}
	
	/***********************************************************
	*Nome: getAlturaImagem()
	*Descri��o: retorna a altura da imagem
	*Parametros: Nenhum
	*Retorno: int
	************************************************************/
	public int getAlturaImagem()
	{
		return imagem.getHeight();
	}
	
	/***********************************************************
	*Nome: carregaImagem(String pName)
	*Descri��o: carrega a imagem
	*Parametros: String
	*Retorno: boolean
	************************************************************/
	public boolean carregaImagem(URL pNome)
	{
		try
		{ 
			//Faz a leitura do arquivo
			imagem = ImageIO.read(pNome);
			
			//Cria o modelo de cores da imagem
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
			int transparency = imagem.getColorModel().getTransparency();
			BufferedImage copy = gc.createCompatibleImage(imagem.getWidth(),imagem.getHeight(), transparency);
			Graphics2D g2d = copy.createGraphics();
			g2d.drawImage(imagem,0,0,null);
			imagem = copy;
			sNome = pNome.getPath();
			g2d.dispose();
		}
		catch(Exception e)
		{
			CLog.escreveLog("Erro no carregamento da imagem "+pNome+"\n");
			return false;
		}
		
		return true;
	}
	
	/***********************************************************
	*Nome: liberaRecursos()
	*Descri��o: libera recursos da imagem
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void liberaRecursos()
	{
		imagem = null;
		sNome = null;
	}
	
	/***********************************************************
	*Nome: incReferencias()
	*Descri��o: aumenta o numero de referencias da imagem
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void incNumeroReferencias()
	{
		iTotalReferencias++;
	}
	
	/***********************************************************
	*Nome:decReferencias()
	*Descri��o: diminui o numero de referencias da imagem
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void decNumeroReferencias()
	{
		iTotalReferencias--;
	}
	
	/***********************************************************
	*Nome: getNumeroReferencias()
	*Descri��o: retorna o numero de referencias da imagem
	*Parametros: Nenhum
	*Retorno: int
	************************************************************/
	public int getNumeroReferencias()
	{
		return iTotalReferencias;
	}
}
