package com.asinfo.as2.inventario.procesos.servicio;

import com.asinfo.as2.entities.Bodega;
import com.asinfo.as2.entities.DespachoCliente;
import com.asinfo.as2.entities.DetalleMovimientoInventario;
import com.asinfo.as2.entities.DetallePedidoCliente;
import com.asinfo.as2.entities.DetalleRegistroPesoLote;
import com.asinfo.as2.entities.EntidadBase;
import com.asinfo.as2.entities.FacturaCliente;
import com.asinfo.as2.entities.FacturaProveedor;
import com.asinfo.as2.entities.MovimientoInventario;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.RecepcionProveedor;
import com.asinfo.as2.entities.produccion.DetalleOrdenFabricacionMaterial;
import com.asinfo.as2.entities.produccion.OrdenFabricacion;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioVerificadorInventario
{
  public abstract void actualizarSaldoProducto(MovimientoInventario paramMovimientoInventario, boolean paramBoolean)
    throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception;
  
  public abstract void actualizarSaldoProducto(DespachoCliente paramDespachoCliente, boolean paramBoolean, Date paramDate)
    throws ExcepcionAS2Inventario, AS2Exception;
  
  public abstract void actualizarSaldoProducto(RecepcionProveedor paramRecepcionProveedor, boolean paramBoolean)
    throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception;
  
  public abstract void actualizarSaldoProducto(FacturaCliente paramFacturaCliente, boolean paramBoolean)
    throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception;
  
  public abstract void actualizarSaldoProducto(FacturaProveedor paramFacturaProveedor, boolean paramBoolean)
    throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception;
  
  public abstract BigDecimal validarSaldoProducto(Date paramDate, BigDecimal paramBigDecimal, Producto paramProducto, Bodega paramBodega, int paramInt)
    throws ExcepcionAS2Inventario;
  
  public abstract void actualizarSaldoProducto(RecepcionProveedor paramRecepcionProveedor, boolean paramBoolean, Date paramDate)
    throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception;
  
  public abstract <T extends EntidadBase> void cantidadDetalle(List<? extends EntidadBase> paramList)
    throws ExcepcionAS2Inventario;
  
  public abstract <T extends EntidadBase> void verificarTotalDetalle(List<? extends EntidadBase> paramList)
    throws ExcepcionAS2Inventario;
  
  public abstract void abrirCerrarOrdenSalidaMaterial(MovimientoInventario paramMovimientoInventario);
  
  public abstract void actualizarSaldoDetalle(DetalleMovimientoInventario paramDetalleMovimientoInventario, boolean paramBoolean, MovimientoInventario paramMovimientoInventario)
    throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception;
  
  public abstract BigDecimal actualizarCantidadFabricadaOrdenFabricacion(OrdenFabricacion paramOrdenFabricacion, BigDecimal paramBigDecimal);
  
  public abstract void actualizarInventarioComprometido(DetallePedidoCliente paramDetallePedidoCliente, Date paramDate, BigDecimal paramBigDecimal);
  
  public abstract void actualizarInventarioComprometidoDespacho(DetalleRegistroPesoLote paramDetalleRegistroPesoLote);
  
  public abstract void actualizarInventarioComprometidoProduccion(DetalleOrdenFabricacionMaterial paramDetalleOrdenFabricacionMaterial, Date paramDate, boolean paramBoolean)
    throws AS2Exception;
  
  public abstract void actualizarSaldoProducto(MovimientoInventario paramMovimientoInventario, boolean paramBoolean, Producto paramProducto)
    throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.servicio.ServicioVerificadorInventario
 * JD-Core Version:    0.7.0.1
 */