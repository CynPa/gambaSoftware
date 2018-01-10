package com.asinfo.as2.mantenimiento.configuracion.old;

import com.asinfo.as2.entities.mantenimiento.old.OrdenServicioMantenimiento;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioOrdenServicioMantenimiento
{
  public abstract void guardar(OrdenServicioMantenimiento paramOrdenServicioMantenimiento);
  
  public abstract void eliminar(OrdenServicioMantenimiento paramOrdenServicioMantenimiento);
  
  public abstract OrdenServicioMantenimiento buscarPorId(int paramInt);
  
  public abstract List<OrdenServicioMantenimiento> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<OrdenServicioMantenimiento> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract OrdenServicioMantenimiento cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.old.ServicioOrdenServicioMantenimiento
 * JD-Core Version:    0.7.0.1
 */