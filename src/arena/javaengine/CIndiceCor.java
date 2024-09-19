/***********************************************************************
*Nome: CIndiceCor
*Descri��o: Classe responsavel pela representa��o de um quadro por cor
*Autor: Silvano Malfatti
*Data: 03/04/08
************************************************************************/

/*Declara��o de pacote*/
package javaengine;

/*Bibliotecas utilizadas*/
import java.awt.Color;

public class CIndiceCor 
{
	/*Atributos da classe*/
	private int iIndiceQuadro;
	private Color cor;
	
	/***********************************************************
	*Nome:CIndiceCor
	*Descri��o: Construtor da classe
	*Parametros: int, Color
	*Retorno: Nenhum
	************************************************************/
	public CIndiceCor(int pIndice, Color pCor)
	{
		iIndiceQuadro = pIndice;
		cor = pCor;
	}
	
	/***********************************************************
	*Nome: getCor()
	*Descri��o: retorna a cor do pixel
	*Parametros: Nenhum
	*Retorno: Color
	************************************************************/
	public Color getCor()
	{
		return cor;
	}
	
	/***********************************************************
	*Nome: getIndiceQuadro()
	*Descri��o: retorna o indice do quadro
	*Parametros: Nenhum
	*Retorno: int
	************************************************************/
	public int getIndiceQuadro()
	{
		return iIndiceQuadro;
	}
	
	/***********************************************************
	*Nome: liberaRecursos()
	*Descri��o: libera os recursos da classe
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void liberaRecursos()
	{
	}
}
