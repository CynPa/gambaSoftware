package com.asinfo.as2.inventario.configuracion.servicio;

import com.asinfo.as2.entities.CategoriaProducto;
import com.asinfo.as2.entities.SubcategoriaProducto;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioCategoriaProducto
{
  public abstract void guardar(CategoriaProducto paramCategoriaProducto);
  
  public abstract void eliminar(CategoriaProducto paramCategoriaProducto);
  
  public abstract CategoriaProducto buscarPorId(int paramInt);
  
  public abstract List<CategoriaProducto> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<CategoriaProducto> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<SubcategoriaProducto> obtenerListaSubcategoriaProducto(int paramInt, CategoriaProducto paramCategoriaProducto, String paramString);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto
 * JD-Core Version:    0.7.0.1
 */