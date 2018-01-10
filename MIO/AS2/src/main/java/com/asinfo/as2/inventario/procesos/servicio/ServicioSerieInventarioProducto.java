package com.asinfo.as2.inventario.procesos.servicio;

import com.asinfo.as2.entities.DespachoCliente;
import com.asinfo.as2.entities.InventarioProducto;
import com.asinfo.as2.entities.MovimientoInventario;
import com.asinfo.as2.entities.RecepcionProveedor;
import com.asinfo.as2.entities.SerieInventarioProducto;
import com.asinfo.as2.excepciones.AS2Exception;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioSerieInventarioProducto
{
  public abstract void guardar(SerieInventarioProducto paramSerieInventarioProducto);
  
  public abstract void validar(MovimientoInventario paramMovimientoInventario)
    throws AS2Exception;
  
  public abstract void validar(RecepcionProveedor paramRecepcionProveedor)
    throws AS2Exception;
  
  public abstract void validar(DespachoCliente paramDespachoCliente)
    throws AS2Exception;
  
  public abstract List<SerieInventarioProducto> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract InventarioProducto cargarDetalle(InventarioProducto paramInventarioProducto);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.servicio.ServicioSerieInventarioProducto
 * JD-Core Version:    0.7.0.1
 */