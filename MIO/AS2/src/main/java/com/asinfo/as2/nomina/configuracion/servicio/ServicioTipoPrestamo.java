package com.asinfo.as2.nomina.configuracion.servicio;

import com.asinfo.as2.entities.TipoPrestamo;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioTipoPrestamo
{
  public abstract void guardar(TipoPrestamo paramTipoPrestamo)
    throws ExcepcionAS2Nomina, AS2Exception;
  
  public abstract void eliminar(TipoPrestamo paramTipoPrestamo);
  
  public abstract TipoPrestamo buscarPorId(int paramInt);
  
  public abstract List<TipoPrestamo> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<TipoPrestamo> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract TipoPrestamo cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.ServicioTipoPrestamo
 * JD-Core Version:    0.7.0.1
 */