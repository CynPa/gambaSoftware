package com.asinfo.as2.inventario.configuracion.servicio;

import com.asinfo.as2.entities.FiltroProducto;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioFiltroProducto
{
  public abstract void guardar(FiltroProducto paramFiltroProducto)
    throws ExcepcionAS2;
  
  public abstract void eliminar(FiltroProducto paramFiltroProducto);
  
  public abstract FiltroProducto buscarPorId(Integer paramInteger);
  
  public abstract List<FiltroProducto> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<FiltroProducto> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.ServicioFiltroProducto
 * JD-Core Version:    0.7.0.1
 */