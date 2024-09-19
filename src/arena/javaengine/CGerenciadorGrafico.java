/***********************************************************************
*Nome: CGerenteGrafico
*Descri��o: Classe responsavel pelo gerenciamento de recursos gr�ficos
*Autor: Silvano Malfatti
*Data: 27/05/08
************************************************************************/

/*Declara��o de pacote*/
package javaengine;

/*Bibliotecas utilizadas*/
import java.net.URL;
import java.util.Vector;

public class CGerenciadorGrafico 
{
	/*Atributos da classe*/
	Vector vetorImagens = null;
	
	/***********************************************************
	*Nome: CGerenteGrafico()
	*Descri��o: Construtor da classe
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public CGerenciadorGrafico()
	{
		vetorImagens = new Vector();
	}
	
	/***********************************************************
	*Nome: carregaImagem()
	*Descri��o: carrega uma imagem no motor
	*Parametros: String
	*Retorno: Imagem
	************************************************************/
	public CImagem carregaImagem(URL pName)
	{
		//Verifica se a imagem j� n�o est� carregada
		for (int iIndex=0; iIndex<vetorImagens.size(); iIndex++)
		{
			if (pName.getPath().equals(((CImagem)vetorImagens.elementAt(iIndex)).getNomeImagem()))
			{
				((CImagem)vetorImagens.elementAt(iIndex)).incNumeroReferencias();
				return ((CImagem)vetorImagens.elementAt(iIndex));
			}
		}
		
		//Tenta carregar a imagem
		CImagem tempImage = new CImagem();
		if (tempImage.carregaImagem(pName))
		{
			tempImage.incNumeroReferencias();
			vetorImagens.addElement(tempImage);
			return tempImage;
		}
		else
		{
			return null;
		}
	}
	
	/***********************************************************
	*Nome: liberaRecursos()
	*Descri��o: libera os recursos do gerente gr�fico
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void liberaRecursos()
	{
		for (int iIndex=vetorImagens.size()-1; iIndex>=0; iIndex--)
		{
			((CImagem)vetorImagens.elementAt(iIndex)).liberaRecursos();
			vetorImagens.remove(iIndex);
		}
		vetorImagens = null;
	}
}
