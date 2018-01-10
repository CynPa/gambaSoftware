package com.asinfo.as2.inventario.configuracion.servicio;

import com.asinfo.as2.entities.SubcategoriaProducto;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioSubcategoriaProducto
{
  public abstract void guardar(SubcategoriaProducto paramSubcategoriaProducto);
  
  public abstract void eliminar(SubcategoriaProducto paramSubcategoriaProducto);
  
  public abstract SubcategoriaProducto buscarPorId(int paramInt);
  
  public abstract SubcategoriaProducto cargarDetalle(int paramInt);
  
  public abstract List<SubcategoriaProducto> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<SubcategoriaProducto> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto
 * JD-Core Version:    0.7.0.1
 */