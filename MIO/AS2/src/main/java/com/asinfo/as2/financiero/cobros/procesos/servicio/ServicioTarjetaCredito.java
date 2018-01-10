package com.asinfo.as2.financiero.cobros.procesos.servicio;

import com.asinfo.as2.entities.TarjetaCredito;
import com.asinfo.as2.excepciones.AS2Exception;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioTarjetaCredito
{
  public abstract void guardar(TarjetaCredito paramTarjetaCredito)
    throws AS2Exception;
  
  public abstract List<TarjetaCredito> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract TarjetaCredito cargarDetalle(int paramInt);
  
  public abstract List<TarjetaCredito> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioTarjetaCredito
 * JD-Core Version:    0.7.0.1
 */