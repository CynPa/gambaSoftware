package com.asinfo.as2.financiero.contabilidad.configuracion.servicio;

import com.asinfo.as2.entities.TipoAsiento;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioTipoAsiento
{
  public abstract void guardar(TipoAsiento paramTipoAsiento);
  
  public abstract void eliminar(TipoAsiento paramTipoAsiento);
  
  public abstract TipoAsiento buscarPorId(Integer paramInteger);
  
  public abstract List<TipoAsiento> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<TipoAsiento> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract TipoAsiento buscarPorNombre(String paramString, int paramInt)
    throws ExcepcionAS2Financiero;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioTipoAsiento
 * JD-Core Version:    0.7.0.1
 */