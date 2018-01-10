package com.asinfo.as2.compras.procesos.servicio;

import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
import com.asinfo.as2.entities.DetalleFacturaProveedor;
import com.asinfo.as2.entities.DetalleRecepcionProveedor;
import com.asinfo.as2.entities.FacturaProveedor;
import com.asinfo.as2.entities.LiquidacionAnticipoProveedor;
import com.asinfo.as2.entities.Pago;
import com.asinfo.as2.entities.RecepcionProveedor;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioVerificadorCompras
{
  public abstract void actualizarCantidadPorFacturar(DetalleFacturaProveedor paramDetalleFacturaProveedor, boolean paramBoolean, List<Integer> paramList);
  
  public abstract void actualizarCantidadPorFacturar(FacturaProveedor paramFacturaProveedor, boolean paramBoolean);
  
  public abstract void actualizarCantidadPorRecibir(DetalleRecepcionProveedor paramDetalleRecepcionProveedor, boolean paramBoolean, List<Integer> paramList);
  
  public abstract void actualizarCantidadPorRecibir(RecepcionProveedor paramRecepcionProveedor, boolean paramBoolean);
  
  public abstract void actualizarCuentaPorPagar(Pago paramPago, boolean paramBoolean)
    throws ExcepcionAS2Compras;
  
  public abstract void actualizarCuentaPorPagar(LiquidacionAnticipoProveedor paramLiquidacionAnticipoProveedor, boolean paramBoolean)
    throws ExcepcionAS2Compras;
  
  public abstract void actualizarPrecioFechaUltimaCompra(FacturaProveedor paramFacturaProveedor);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.servicio.ServicioVerificadorCompras
 * JD-Core Version:    0.7.0.1
 */