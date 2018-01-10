package com.asinfo.as2.inventario.procesos.servicio;

import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.SerieProducto;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioSerieProducto
{
  public abstract void guardar(SerieProducto paramSerieProducto);
  
  public abstract List<SerieProducto> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<SerieProducto> getListaSerieProducto(int paramInt, Producto paramProducto, List<String> paramList);
  
  public abstract SerieProducto buscarPorId(Object paramObject);
  
  public abstract SerieProducto buscarPorCodigo(int paramInt, Producto paramProducto, String paramString);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.servicio.ServicioSerieProducto
 * JD-Core Version:    0.7.0.1
 */