package com.asinfo.as2.ventas.configuracion.servicio;

import com.asinfo.as2.entities.RangoComision;
import com.asinfo.as2.entities.RangoComisionCategoriaProducto;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioRangoComision
{
  public abstract void guardar(RangoComision paramRangoComision);
  
  public abstract void eliminar(RangoComision paramRangoComision);
  
  public abstract RangoComision buscarPorId(int paramInt);
  
  public abstract List<RangoComision> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<RangoComision> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract RangoComision cargarDetalle(int paramInt);
  
  public abstract List<RangoComision> getListaOrderByValorHasta();
  
  public abstract List<RangoComisionCategoriaProducto> getListaRangoComisionCategoriaProducto();
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.servicio.ServicioRangoComision
 * JD-Core Version:    0.7.0.1
 */