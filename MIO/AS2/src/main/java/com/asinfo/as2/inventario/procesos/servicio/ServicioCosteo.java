package com.asinfo.as2.inventario.procesos.servicio;

import com.asinfo.as2.clases.SaldoNegativoProducto;
import com.asinfo.as2.entities.Bodega;
import com.asinfo.as2.entities.DespachoCliente;
import com.asinfo.as2.entities.FacturaCliente;
import com.asinfo.as2.entities.MovimientoInventario;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.RecepcionProveedor;
import com.asinfo.as2.entities.produccion.OrdenFabricacion;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.Local;

@Local
public abstract interface ServicioCosteo
{
  public static final int DECIMALES_CALCULO_COSTO_UNITARIO = 20;
  public static final int DECIMALES_CALCULO_COSTO_TOTAL = 4;
  
  public abstract void generarCostos(MovimientoInventario paramMovimientoInventario, boolean paramBoolean);
  
  public abstract void generarCostos(DespachoCliente paramDespachoCliente, boolean paramBoolean);
  
  public abstract void generarCostos(RecepcionProveedor paramRecepcionProveedor, boolean paramBoolean);
  
  public abstract void generarCostos(int paramInt, Producto paramProducto, Date paramDate1, Date paramDate2, Bodega paramBodega, Integer paramInteger, Set<Integer> paramSet);
  
  public abstract void generarCostosEstandar(int paramInt, Producto paramProducto, Date paramDate1, Date paramDate2);
  
  public abstract void generarCostos(FacturaCliente paramFacturaCliente, boolean paramBoolean);
  
  public abstract int eliminarCostoProductoNoInventarioProducto(int paramInt, Date paramDate1, Date paramDate2, boolean paramBoolean);
  
  public abstract void generarCostos(MovimientoInventario paramMovimientoInventario, boolean paramBoolean, Integer paramInteger);
  
  public abstract List<SaldoNegativoProducto> revisionSaldosNegativos(int paramInt, Producto paramProducto, Date paramDate1, Date paramDate2, Bodega paramBodega);
  
  public abstract BigDecimal obtenerCostoOrdenFabricacion(boolean paramBoolean, OrdenFabricacion paramOrdenFabricacion, Integer paramInteger, Date paramDate1, Date paramDate2, Bodega paramBodega, Set<Integer> paramSet);
  
  public abstract int actualizaIndicadorGeneraCostoTransferenciaIngreso(int paramInt, Date paramDate1, Date paramDate2, Producto paramProducto);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.servicio.ServicioCosteo
 * JD-Core Version:    0.7.0.1
 */