package com.asinfo.as2.caja.procesos.servicio;

import com.asinfo.as2.entities.MovimientoCaja;
import java.util.List;
import java.util.Map;

public abstract interface ServicioMovimientoCaja
{
  public abstract void guardar(MovimientoCaja paramMovimientoCaja);
  
  public abstract void eliminar(MovimientoCaja paramMovimientoCaja);
  
  public abstract MovimientoCaja buscarPorId(int paramInt);
  
  public abstract List<MovimientoCaja> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<MovimientoCaja> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract MovimientoCaja cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.caja.procesos.servicio.ServicioMovimientoCaja
 * JD-Core Version:    0.7.0.1
 */