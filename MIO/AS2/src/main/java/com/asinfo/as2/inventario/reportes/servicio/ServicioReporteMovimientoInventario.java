package com.asinfo.as2.inventario.reportes.servicio;

import com.asinfo.as2.entities.Bodega;
import com.asinfo.as2.entities.CategoriaProducto;
import com.asinfo.as2.entities.DestinoCosto;
import com.asinfo.as2.entities.DimensionContable;
import com.asinfo.as2.entities.Documento;
import com.asinfo.as2.entities.Lote;
import com.asinfo.as2.entities.MovimientoInventario;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.SubcategoriaProducto;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.entities.TomaFisica;
import com.asinfo.as2.enumeraciones.Estado;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteMovimientoInventario
{
  public abstract List getReporteTransferenciaBodega(int paramInt);
  
  public abstract List getReporteConsumoBodega(int paramInt);
  
  public abstract List getReporteAjusteInventario(int paramInt);
  
  public abstract List getReporteConsumoBodegaDestinoCostoResumido(Date paramDate1, Date paramDate2, SubcategoriaProducto paramSubcategoriaProducto, int paramInt, Producto paramProducto, DestinoCosto paramDestinoCosto, List<CategoriaProducto> paramList, DimensionContable paramDimensionContable, Sucursal paramSucursal);
  
  public abstract List getReporteConsumoBodegaDestinoCostoMensual(Date paramDate1, Date paramDate2, int paramInt, Producto paramProducto, DestinoCosto paramDestinoCosto, List<CategoriaProducto> paramList, SubcategoriaProducto paramSubcategoriaProducto, DimensionContable paramDimensionContable, Sucursal paramSucursal, Documento paramDocumento);
  
  public abstract List getReporteConsumoBodegaDestinoCostoDetallado(Date paramDate1, Date paramDate2, SubcategoriaProducto paramSubcategoriaProducto, int paramInt, Producto paramProducto, DestinoCosto paramDestinoCosto, List<CategoriaProducto> paramList, DimensionContable paramDimensionContable, Sucursal paramSucursal, boolean paramBoolean, Documento paramDocumento);
  
  public abstract List getReporteConsumoBodegaDestinoCostoCategoria(Date paramDate1, Date paramDate2, SubcategoriaProducto paramSubcategoriaProducto, int paramInt, Producto paramProducto, DestinoCosto paramDestinoCosto, List<CategoriaProducto> paramList, DimensionContable paramDimensionContable, Sucursal paramSucursal, Documento paramDocumento);
  
  public abstract List getReporteAjusteInventario(Date paramDate1, Date paramDate2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
  
  public abstract List getReporteOrdenSalidaMaterialByOrdenFabricacion(int paramInt);
  
  public abstract List<Object[]> getReporteTransferenciaInventario(int paramInt, Documento paramDocumento, Date paramDate1, Date paramDate2, Bodega paramBodega1, Bodega paramBodega2, SubcategoriaProducto paramSubcategoriaProducto, Estado paramEstado, DimensionContable paramDimensionContable, CategoriaProducto paramCategoriaProducto);
  
  public abstract List getReporteIngresoFabricacion(MovimientoInventario paramMovimientoInventario, boolean paramBoolean);
  
  public abstract List<Object[]> reporteTomaFisica(TomaFisica paramTomaFisica);
  
  public abstract List getReporteOrdenSalidaMaterial(int paramInt, boolean paramBoolean1, Date paramDate1, Date paramDate2, Producto paramProducto, Lote paramLote, boolean paramBoolean2, boolean paramBoolean3);
  
  public abstract List<Object[]> getReporteCierreCirculo(int paramInt, Date paramDate1, Date paramDate2, SubcategoriaProducto paramSubcategoriaProducto);
  
  public abstract List<Object[]> getDatosImpresionEtiquetaLote(int paramInt1, int paramInt2, String paramString, int paramInt3, int paramInt4);
  
  public abstract List<Object[]> getReporteOrdenSalidaMaterialConOrdenFabricacion(int paramInt, boolean paramBoolean1, Date paramDate1, Date paramDate2, Producto paramProducto, Lote paramLote, boolean paramBoolean2, boolean paramBoolean3);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.servicio.ServicioReporteMovimientoInventario
 * JD-Core Version:    0.7.0.1
 */