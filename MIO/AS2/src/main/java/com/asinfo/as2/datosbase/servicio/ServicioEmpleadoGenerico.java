package com.asinfo.as2.datosbase.servicio;

import com.asinfo.as2.entities.EmpleadoGenerico;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioEmpleadoGenerico
{
  public abstract void guardar(EmpleadoGenerico paramEmpleadoGenerico);
  
  public abstract void eliminar(EmpleadoGenerico paramEmpleadoGenerico);
  
  public abstract EmpleadoGenerico buscarPorId(int paramInt);
  
  public abstract List<EmpleadoGenerico> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<EmpleadoGenerico> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract EmpleadoGenerico cargarDetalle(int paramInt);
  
  public abstract List<EmpleadoGenerico> autocompletarEmpeladoGenerico(String paramString);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.ServicioEmpleadoGenerico
 * JD-Core Version:    0.7.0.1
 */