package com.asinfo.as2.inventario.configuracion.servicio;

import com.asinfo.as2.entities.Visualizacion;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioVisualizacion
{
  public abstract void guardar(Visualizacion paramVisualizacion);
  
  public abstract void eliminar(Visualizacion paramVisualizacion);
  
  public abstract Visualizacion buscarPorId(int paramInt);
  
  public abstract Visualizacion cargarDetalle(int paramInt);
  
  public abstract List<Visualizacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.ServicioVisualizacion
 * JD-Core Version:    0.7.0.1
 */