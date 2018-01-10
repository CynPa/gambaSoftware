package com.asinfo.as2.ventas.procesos.servicio;

import com.asinfo.as2.entities.DetallePedidoCliente;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioDetallePedidoCliente
{
  public abstract List<DetallePedidoCliente> buscarDetallePedidoClientePorProducto(int paramInt1, int paramInt2);
  
  public abstract void guardar(DetallePedidoCliente paramDetallePedidoCliente);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.ServicioDetallePedidoCliente
 * JD-Core Version:    0.7.0.1
 */