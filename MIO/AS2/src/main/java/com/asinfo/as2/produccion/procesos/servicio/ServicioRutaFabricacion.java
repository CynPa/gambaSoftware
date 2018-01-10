package com.asinfo.as2.produccion.procesos.servicio;

import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.produccion.RutaFabricacion;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioRutaFabricacion
{
  public abstract void guardar(RutaFabricacion paramRutaFabricacion);
  
  public abstract void eliminar(RutaFabricacion paramRutaFabricacion);
  
  public abstract RutaFabricacion buscarPorId(int paramInt);
  
  public abstract List<RutaFabricacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<RutaFabricacion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract RutaFabricacion cargarDetalle(int paramInt);
  
  public abstract List<RutaFabricacion> autocompletarRutaFabricacion(Producto paramProducto, String paramString);
  
  public abstract RutaFabricacion copiarRutaFabricacion(RutaFabricacion paramRutaFabricacion);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.servicio.ServicioRutaFabricacion
 * JD-Core Version:    0.7.0.1
 */