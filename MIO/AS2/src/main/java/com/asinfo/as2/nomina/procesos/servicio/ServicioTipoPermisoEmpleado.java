package com.asinfo.as2.nomina.procesos.servicio;

import com.asinfo.as2.entities.TipoPermisoEmpleado;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioTipoPermisoEmpleado
{
  public abstract void guardar(TipoPermisoEmpleado paramTipoPermisoEmpleado);
  
  public abstract void eliminar(TipoPermisoEmpleado paramTipoPermisoEmpleado);
  
  public abstract TipoPermisoEmpleado buscarPorId(int paramInt);
  
  public abstract List<TipoPermisoEmpleado> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<TipoPermisoEmpleado> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.ServicioTipoPermisoEmpleado
 * JD-Core Version:    0.7.0.1
 */