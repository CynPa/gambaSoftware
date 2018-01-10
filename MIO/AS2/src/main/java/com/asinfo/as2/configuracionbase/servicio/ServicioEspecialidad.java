package com.asinfo.as2.configuracionbase.servicio;

import com.asinfo.as2.entities.Especialidad;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioEspecialidad
{
  public abstract void guardar(Especialidad paramEspecialidad);
  
  public abstract void eliminar(Especialidad paramEspecialidad);
  
  public abstract Especialidad buscarPorId(int paramInt);
  
  public abstract List<Especialidad> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Especialidad> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Especialidad cargarDetalle(int paramInt);
  
  public abstract List<Especialidad> autocompletarEspecialidad(String paramString, int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.ServicioEspecialidad
 * JD-Core Version:    0.7.0.1
 */