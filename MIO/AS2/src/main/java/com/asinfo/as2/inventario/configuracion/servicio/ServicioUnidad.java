package com.asinfo.as2.inventario.configuracion.servicio;

import com.asinfo.as2.entities.Unidad;
import com.asinfo.as2.enumeraciones.TipoUnidadMedida;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioUnidad
{
  public abstract void guardar(Unidad paramUnidad);
  
  public abstract void eliminar(Unidad paramUnidad);
  
  public abstract Unidad buscarPorId(int paramInt);
  
  public abstract List<Unidad> listaUnidadCombo(String paramString, TipoUnidadMedida paramTipoUnidadMedida, int paramInt);
  
  public abstract Unidad cargarDetalle(int paramInt);
  
  public abstract List<Unidad> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<Unidad> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Unidad> obtenerListaUnidadPorUnidadOrigen(int paramInt);
  
  public abstract Unidad buscarUnidad(Map<String, String> paramMap)
    throws ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidad
 * JD-Core Version:    0.7.0.1
 */