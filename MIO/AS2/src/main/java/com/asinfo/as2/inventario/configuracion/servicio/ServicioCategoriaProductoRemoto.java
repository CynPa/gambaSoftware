package com.asinfo.as2.inventario.configuracion.servicio;

import com.asinfo.as2.entities.CategoriaProducto;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

@Remote
public abstract interface ServicioCategoriaProductoRemoto
{
  public abstract void guardar(CategoriaProducto paramCategoriaProducto);
  
  public abstract void eliminar(CategoriaProducto paramCategoriaProducto);
  
  public abstract CategoriaProducto buscarPorId(int paramInt);
  
  public abstract List<CategoriaProducto> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<CategoriaProducto> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract CategoriaProducto buscarPorCodigo(String paramString)
    throws ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProductoRemoto
 * JD-Core Version:    0.7.0.1
 */