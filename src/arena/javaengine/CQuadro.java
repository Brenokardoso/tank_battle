/***********************************************************************
*Nome: CQuadro
*Descri��o: classe respons�vel pela cria��o de um quadro
*Autor: Silvano Malfatti
*Data: 07/06/08
************************************************************************/

/*Declara��o de pacote*/
package javaengine;

public class CQuadro 
{
	/*Atributos da classe*/
	private int indiceQuadro;
	
	/***********************************************************
	*Nome: UGEQuad()
	*Descri��o: Construtor da classe
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public CQuadro()
	{
		indiceQuadro = 0;
	}
	
	/***********************************************************
	*Nome: seQuadro()
	*Descri��o: permite alterar o valor do indice do quadro
	*Parametros: int
	*Retorno: Nenhum
	************************************************************/
	public void setQuadro(int indice)
	{
		indiceQuadro = indice;
	}
	
	/***********************************************************
	*Nome: getQuadro()
	*Descri��o: retorna o valor do indice do quadro
	*Parametros: Nenhum
	*Retorno: int
	************************************************************/
	public int getQuadro()
	{
		return indiceQuadro;
	}
	
	/***********************************************************
	*Nome: liberaRecursos()
	*Descri��o: libera recursos
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void liberaRecursos()
	{
		
	}
}
