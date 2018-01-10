package com.asinfo.as2.inventario.configuracion.servicio;

import com.asinfo.as2.entities.RangoCostoEstandarProducto;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioRangoCostoEstandarProducto
{
  public abstract RangoCostoEstandarProducto guardar(RangoCostoEstandarProducto paramRangoCostoEstandarProducto);
  
  public abstract void eliminar(RangoCostoEstandarProducto paramRangoCostoEstandarProducto);
  
  public abstract List<RangoCostoEstandarProducto> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<RangoCostoEstandarProducto> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract RangoCostoEstandarProducto buscarPorId(Integer paramInteger);
  
  public abstract RangoCostoEstandarProducto cargarDetalle(int paramInt);
  
  public abstract void cargaCostoEstandarProducto(RangoCostoEstandarProducto paramRangoCostoEstandarProducto, InputStream paramInputStream, int paramInt)
    throws ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.ServicioRangoCostoEstandarProducto
 * JD-Core Version:    0.7.0.1
 */