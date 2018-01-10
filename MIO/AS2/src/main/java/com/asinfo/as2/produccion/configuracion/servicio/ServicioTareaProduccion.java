package com.asinfo.as2.produccion.configuracion.servicio;

import com.asinfo.as2.entities.produccion.TareaProduccion;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioTareaProduccion
{
  public abstract void guardar(TareaProduccion paramTareaProduccion);
  
  public abstract void eliminar(TareaProduccion paramTareaProduccion);
  
  public abstract TareaProduccion buscarPorId(int paramInt);
  
  public abstract List<TareaProduccion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<TareaProduccion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract TareaProduccion cargarDetalle(int paramInt);
  
  public abstract List<TareaProduccion> autocompletarTareaProduccion(String paramString);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.configuracion.servicio.ServicioTareaProduccion
 * JD-Core Version:    0.7.0.1
 */