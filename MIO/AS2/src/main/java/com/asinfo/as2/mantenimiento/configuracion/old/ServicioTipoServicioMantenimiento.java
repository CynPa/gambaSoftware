package com.asinfo.as2.mantenimiento.configuracion.old;

import com.asinfo.as2.entities.mantenimiento.old.TipoServicioMantenimiento;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioTipoServicioMantenimiento
{
  public abstract void guardar(TipoServicioMantenimiento paramTipoServicioMantenimiento);
  
  public abstract void eliminar(TipoServicioMantenimiento paramTipoServicioMantenimiento);
  
  public abstract TipoServicioMantenimiento buscarPorId(int paramInt);
  
  public abstract List<TipoServicioMantenimiento> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<TipoServicioMantenimiento> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.old.ServicioTipoServicioMantenimiento
 * JD-Core Version:    0.7.0.1
 */