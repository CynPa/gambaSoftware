package com.asinfo.as2.configuracionbase.servicio;

import com.asinfo.as2.entities.PuntoDeVenta;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPuntoDeVenta
{
  public abstract void guardar(PuntoDeVenta paramPuntoDeVenta)
    throws ExcepcionAS2Financiero;
  
  public abstract void eliminar(PuntoDeVenta paramPuntoDeVenta)
    throws ExcepcionAS2Financiero;
  
  public abstract PuntoDeVenta buscarPorId(Integer paramInteger);
  
  public abstract List<PuntoDeVenta> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<PuntoDeVenta> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract PuntoDeVenta cargarDetalle(int paramInt);
  
  public abstract PuntoDeVenta buscarPuntoDeVenta(Map<String, String> paramMap)
    throws ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta
 * JD-Core Version:    0.7.0.1
 */