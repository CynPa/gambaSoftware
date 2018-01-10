package com.asinfo.as2.datosbase.servicio;

import com.asinfo.as2.entities.Secuencia;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

@Remote
public abstract interface ServicioSecuenciaRemote
{
  public abstract void guardar(Secuencia paramSecuencia)
    throws ExcepcionAS2Financiero;
  
  public abstract void eliminar(Secuencia paramSecuencia);
  
  public abstract Secuencia buscarPorId(Integer paramInteger);
  
  public abstract String obtenerSecuenciaDocumento(int paramInt, Date paramDate)
    throws ExcepcionAS2;
  
  public abstract String obtenerSecuenciaTipoAsiento(int paramInt, Date paramDate)
    throws ExcepcionAS2;
  
  public abstract List<Secuencia> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Secuencia> obtenerListaCombo();
  
  public abstract List<Secuencia> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract String obtenerSecuencia(Secuencia paramSecuencia, Date paramDate)
    throws ExcepcionAS2;
  
  public abstract void actualizarSecuencia(Secuencia paramSecuencia, String paramString);
  
  public abstract void detach(Secuencia paramSecuencia);
  
  public abstract void flush();
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.ServicioSecuenciaRemote
 * JD-Core Version:    0.7.0.1
 */