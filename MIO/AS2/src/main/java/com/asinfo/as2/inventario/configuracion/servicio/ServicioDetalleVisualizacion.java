package com.asinfo.as2.inventario.configuracion.servicio;

import com.asinfo.as2.entities.DetalleVisualizacion;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioDetalleVisualizacion
{
  public abstract void guardar(DetalleVisualizacion paramDetalleVisualizacion);
  
  public abstract void eliminar(DetalleVisualizacion paramDetalleVisualizacion);
  
  public abstract DetalleVisualizacion buscarPorId(int paramInt);
  
  public abstract DetalleVisualizacion cargarDetalle(int paramInt);
  
  public abstract List<DetalleVisualizacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.ServicioDetalleVisualizacion
 * JD-Core Version:    0.7.0.1
 */