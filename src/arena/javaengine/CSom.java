/***********************************************************************
*Nome: CSom
*Descri��o: classe respons�vel carregamento de sons
*Autor: Silvano Malfatti
*Data: 07/06/08
************************************************************************/

/*Declara��o de pacote*/
package javaengine;

/*Bibliotecas*/
import java.net.URL;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class CSom 
{
	/*Atributos da classe*/
	Sequence sequencia = null;
	Sequencer sequenciador = null;
	int iNumeroRepeticoes = 0;
	
	/***********************************************************
	*Nome: CSom
	*Descri��o: Construtor da classe
	*Parametros: String
	*Retorno: Nenhum
	************************************************************/
	public CSom(URL nome, int repeticoes)
	{
		iNumeroRepeticoes = repeticoes;
		try
		{
			sequencia = MidiSystem.getSequence(nome);
			sequenciador = MidiSystem.getSequencer();
			sequenciador.setSequence(sequencia);
			sequenciador.open();
		}
		catch(Exception e)
		{
			CLog.escreveLog("Erro no carregamento do som "+nome+"\n");
		}
	}
	
	/***********************************************************
	*Nome: tocaSom()
	*Descri��o: toca o som
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void tocaSom()
	{
		if (sequenciador!=null)
		{
			if (iNumeroRepeticoes >=0)
			{
				sequenciador.setLoopCount(iNumeroRepeticoes);
			}
			else
			{
				sequenciador.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			}
			
			sequenciador.start();
		}
	}
	
	/***********************************************************
	*Nome: pausaSom()
	*Descri��o: para o som
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void pausaSom()
	{
		sequenciador.stop();
	}
	
	/***********************************************************
	*Nome: reiniciaSom()
	*Descri��o: reinicia o som()
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void reiniciaSom()
	{
		sequenciador.setTickPosition(0);
	}
	
	/***********************************************************
	*Nome: liberaRecursos()
	*Descri��o: libera recursos
	*Parametros: Nenhum
	*Retorno: Nenhum
	************************************************************/
	public void liberaRecursos()
	{
		sequencia = null;
		sequenciador.close();
		sequenciador = null;
	}
}
