package com.asinfo.as2.produccion.configuracion.servicio;

import com.asinfo.as2.entities.MarcaProducto;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioMarcaProducto
{
  public abstract void guardar(MarcaProducto paramMarcaProducto);
  
  public abstract void eliminar(MarcaProducto paramMarcaProducto);
  
  public abstract MarcaProducto buscarPorId(int paramInt);
  
  public abstract MarcaProducto cargarDetalle(int paramInt);
  
  public abstract List<MarcaProducto> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<MarcaProducto> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.configuracion.servicio.ServicioMarcaProducto
 * JD-Core Version:    0.7.0.1
 */