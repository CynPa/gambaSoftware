package com.asinfo.as2.mantenimiento.procesos.old;

import com.asinfo.as2.entities.mantenimiento.old.HistoricoArticuloServicio;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioHistoricoArticuloServicio
{
  public abstract void guardar(HistoricoArticuloServicio paramHistoricoArticuloServicio);
  
  public abstract void eliminar(HistoricoArticuloServicio paramHistoricoArticuloServicio);
  
  public abstract HistoricoArticuloServicio buscarPorId(int paramInt);
  
  public abstract List<HistoricoArticuloServicio> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<HistoricoArticuloServicio> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract HistoricoArticuloServicio obtenerHistoricoArticuloServicioActual(int paramInt);
  
  public abstract HistoricoArticuloServicio cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.procesos.old.ServicioHistoricoArticuloServicio
 * JD-Core Version:    0.7.0.1
 */