package com.asinfo.as2.mantenimiento.configuracion.old;

import com.asinfo.as2.entities.mantenimiento.old.Actividad;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioActividad
{
  public abstract void guardar(Actividad paramActividad);
  
  public abstract void eliminar(Actividad paramActividad);
  
  public abstract Actividad buscarPorId(int paramInt);
  
  public abstract List<Actividad> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Actividad> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Actividad cargarDetalle(int paramInt);
  
  public abstract List<Actividad> autocompletarActividad(String paramString);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.old.ServicioActividad
 * JD-Core Version:    0.7.0.1
 */