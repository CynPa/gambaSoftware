package com.asinfo.as2.inventario.procesos.servicio;

import com.asinfo.as2.clases.ReporteInventarioProducto;
import com.asinfo.as2.entities.Bodega;
import com.asinfo.as2.entities.CategoriaProducto;
import com.asinfo.as2.entities.DespachoCliente;
import com.asinfo.as2.entities.DetalleMovimientoInventario;
import com.asinfo.as2.entities.DimensionContable;
import com.asinfo.as2.entities.FacturaCliente;
import com.asinfo.as2.entities.FacturaProveedor;
import com.asinfo.as2.entities.InventarioProducto;
import com.asinfo.as2.entities.Lote;
import com.asinfo.as2.entities.MovimientoInventario;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.RecepcionProveedor;
import com.asinfo.as2.entities.SubcategoriaProducto;
import com.asinfo.as2.entities.produccion.DetalleCostoFabricacion;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.Local;

@Local
public abstract interface ServicioInventarioProducto
{
  public abstract void guardar(InventarioProducto paramInventarioProducto);
  
  public abstract List<InventarioProducto> obtenerMovimientos(int paramInt, Producto paramProducto, Bodega paramBodega, Date paramDate1, Date paramDate2);
  
  public abstract void eliminaInventarioProductoPorIdDespachoCliente(Integer paramInteger);
  
  public abstract void generarMovimientoInventarioInverso(DespachoCliente paramDespachoCliente, Date paramDate);
  
  public abstract void generarMovimientoInventarioInverso(MovimientoInventario paramMovimientoInventario, Date paramDate);
  
  public abstract void eliminaInventarioProductoPorIdRecepcionProveedor(Integer paramInteger);
  
  public abstract void eliminaInventarioProductoPorIdMovimientoInventario(Integer paramInteger);
  
  public abstract void eliminaInventarioProductoConIdAjusteTransferencia(Integer paramInteger);
  
  public abstract void eliminaInventarioProductoPorIdDevolucionProveedor(Integer paramInteger);
  
  public abstract void eliminaInventarioProductoPorIdDevolucionCliente(Integer paramInteger);
  
  public abstract void actualizarCostoInicial(int paramInt, Date paramDate1, Date paramDate2);
  
  public abstract InventarioProducto cargarDetalle(int paramInt);
  
  public abstract void generarMovimientoInventarioInverso(FacturaCliente paramFacturaCliente, Date paramDate);
  
  public abstract void generarMovimientoInventarioInverso(FacturaProveedor paramFacturaProveedor, Date paramDate);
  
  public abstract void generarMovimientoInventarioInverso(RecepcionProveedor paramRecepcionProveedor, Date paramDate);
  
  public abstract List<Producto> obtenerProductosConMovimiento(int paramInt, Date paramDate1, Date paramDate2, Bodega paramBodega);
  
  public abstract List<InventarioProducto> obtenerMovimientos(int paramInt, Producto paramProducto, Bodega paramBodega, Date paramDate1, Date paramDate2, Boolean paramBoolean);
  
  public abstract List<ReporteInventarioProducto> obtenerMovimientosInventarioProducto(int paramInt1, Producto paramProducto, Bodega paramBodega, Date paramDate1, Date paramDate2, Lote paramLote, DimensionContable paramDimensionContable, int paramInt2);
  
  public abstract List obtenerSaldosPorBodegaProyecto(Bodega paramBodega, DimensionContable paramDimensionContable, Date paramDate);
  
  public abstract void generarMovimientoInventarioInverso(MovimientoInventario paramMovimientoInventario, DetalleMovimientoInventario paramDetalleMovimientoInventario, Date paramDate);
  
  public abstract void eliminarInventarioProductoDetalleMovimientoInventario(DetalleMovimientoInventario paramDetalleMovimientoInventario);
  
  public abstract void actualizarSaldoPorProducto(int paramInt, Producto paramProducto, boolean paramBoolean, Date paramDate);
  
  public abstract List<Producto> obtenerProductos(int paramInt, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto);
  
  public abstract InventarioProducto buscarPorId(Integer paramInteger);
  
  public abstract BigDecimal obtenerCostoTransformacion(InventarioProducto paramInventarioProducto, Date paramDate1, Date paramDate2, Bodega paramBodega, Integer paramInteger, int paramInt, Set<Integer> paramSet);
  
  public abstract void flush();
  
  public abstract void actualizarComponentesCostoFabricacion(DetalleCostoFabricacion paramDetalleCostoFabricacion);
  
  public abstract void eliminaInventarioProductoTransformacionPorIdDevolucionProveedor(Integer paramInteger);
  
  public abstract void generarMovimientoInventarioTransformacionInverso(FacturaProveedor paramFacturaProveedor, Date paramDate);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.servicio.ServicioInventarioProducto
 * JD-Core Version:    0.7.0.1
 */