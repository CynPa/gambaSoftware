package com.asinfo.as2.ventas.reportes.servicio;

import com.asinfo.as2.entities.Atributo;
import com.asinfo.as2.entities.Canal;
import com.asinfo.as2.entities.CategoriaEmpresa;
import com.asinfo.as2.entities.CategoriaProducto;
import com.asinfo.as2.entities.Cobro;
import com.asinfo.as2.entities.DetalleCobro;
import com.asinfo.as2.entities.DetalleFormaCobro;
import com.asinfo.as2.entities.DimensionContable;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.PuntoDeVenta;
import com.asinfo.as2.entities.Recaudador;
import com.asinfo.as2.entities.SubcategoriaProducto;
import com.asinfo.as2.entities.Subempresa;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.entities.Zona;
import com.asinfo.as2.entities.seguridad.EntidadUsuario;
import com.asinfo.as2.enumeraciones.DocumentoBase;
import com.asinfo.as2.enumeraciones.OrdenamientoEnum;
import com.asinfo.as2.enumeraciones.TipoVentaEnum;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import javax.mail.MessagingException;

@Local
public abstract interface ServicioReporteVenta
{
  public abstract List getListaReporteEstadoCuenta(Date paramDate1, Date paramDate2, Empresa paramEmpresa, Recaudador paramRecaudador, Subempresa paramSubempresa, int paramInt, OrdenamientoEnum paramOrdenamientoEnum, boolean paramBoolean, Sucursal paramSucursal, PuntoDeVenta paramPuntoDeVenta)
    throws ExcepcionAS2Ventas;
  
  public abstract List getListaReporteCorteFecha(Date paramDate, Empresa paramEmpresa, Recaudador paramRecaudador, int paramInt, Subempresa paramSubempresa, EntidadUsuario paramEntidadUsuario, Sucursal paramSucursal, PuntoDeVenta paramPuntoDeVenta, Zona paramZona, DimensionContable paramDimensionContable, CategoriaEmpresa paramCategoriaEmpresa)
    throws ExcepcionAS2;
  
  public abstract List getAnalisisVencimientoCliente(Date paramDate, Empresa paramEmpresa, Recaudador paramRecaudador, int paramInt1, Subempresa paramSubempresa, EntidadUsuario paramEntidadUsuario, Sucursal paramSucursal, PuntoDeVenta paramPuntoDeVenta, Zona paramZona, int paramInt2)
    throws ExcepcionAS2Ventas;
  
  public abstract List getAnalisisDinarpad(Date paramDate1, Date paramDate2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, Sucursal paramSucursal, int paramInt6, BigDecimal paramBigDecimal)
    throws ExcepcionAS2Ventas;
  
  public abstract List getListaReporteFacturacionResumido(Date paramDate1, Date paramDate2, String paramString1, String paramString2, int paramInt)
    throws ExcepcionAS2Ventas;
  
  public abstract List getListaReporteFacturacionResumido(Date paramDate1, Date paramDate2, String paramString1, String paramString2, int paramInt1, int paramInt2, boolean paramBoolean1, int paramInt3, int paramInt4, Sucursal paramSucursal, PuntoDeVenta paramPuntoDeVenta, TipoVentaEnum paramTipoVentaEnum, boolean paramBoolean2, boolean paramBoolean3, int paramInt5, DocumentoBase paramDocumentoBase, int paramInt6, DimensionContable paramDimensionContable);
  
  public abstract List getListaReporteVentaProductoDetallado(Date paramDate1, Date paramDate2, String paramString1, String paramString2, int paramInt1, int paramInt2, boolean paramBoolean1, int paramInt3, int paramInt4, Sucursal paramSucursal, PuntoDeVenta paramPuntoDeVenta, TipoVentaEnum paramTipoVentaEnum, boolean paramBoolean2, int paramInt5, DocumentoBase paramDocumentoBase, int paramInt6, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, Producto paramProducto, Atributo paramAtributo, String paramString3, DimensionContable paramDimensionContable);
  
  public abstract List getReportePedidoSaldoPorDespachar(Date paramDate1, Date paramDate2, int paramInt1, boolean paramBoolean, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, int paramInt2)
    throws ExcepcionAS2;
  
  public abstract List getReportePedidoOrdenFabricacion(Date paramDate1, Date paramDate2, int paramInt1, boolean paramBoolean, int paramInt2)
    throws ExcepcionAS2;
  
  public abstract List getListaReporteVentasMargenDescuento(Date paramDate1, Date paramDate2, int paramInt1, BigDecimal paramBigDecimal, int paramInt2)
    throws ExcepcionAS2;
  
  public abstract List getReportePedidoSaldoPorDescacharGenerico(Date paramDate1, Date paramDate2, int paramInt);
  
  public abstract BigDecimal obtenerSaldoEstadoCuenta(int paramInt, Date paramDate, boolean paramBoolean);
  
  public abstract List getReporteNotaDebito(int paramInt);
  
  public abstract List getVencimientos(int paramInt1, Date paramDate, int paramInt2, int paramInt3, int paramInt4, int paramInt5, Sucursal paramSucursal, PuntoDeVenta paramPuntoDeVenta, Zona paramZona, CategoriaEmpresa paramCategoriaEmpresa, boolean paramBoolean)
    throws ExcepcionAS2;
  
  public abstract List getReporteFacturaCliente(int paramInt)
    throws ExcepcionAS2;
  
  public abstract List getReporteVentasProducto(Date paramDate1, Date paramDate2, int paramInt);
  
  public abstract List getReporteVentasCaja(Date paramDate1, Date paramDate2, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract List getReporteCorteFechaAnticipoClientes(Date paramDate1, Date paramDate2, int paramInt1, boolean paramBoolean1, int paramInt2, Sucursal paramSucursal, boolean paramBoolean2)
    throws ExcepcionAS2;
  
  public abstract List getReporteCorteFechaAnticipoClientesSinLiquidacion(Date paramDate, int paramInt)
    throws ExcepcionAS2;
  
  public abstract List geteReporteVentasPorAtributo(Date paramDate1, Date paramDate2, int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7);
  
  public abstract List<Object[]> getListaReporteEstadoCuenta(int paramInt, String paramString);
  
  public abstract List getListaReporteFacturacionDetallado(Date paramDate1, Date paramDate2, String paramString1, String paramString2, int paramInt1, int paramInt2, boolean paramBoolean1, int paramInt3, int paramInt4, Sucursal paramSucursal, PuntoDeVenta paramPuntoDeVenta, TipoVentaEnum paramTipoVentaEnum, boolean paramBoolean2, boolean paramBoolean3, int paramInt5, DocumentoBase paramDocumentoBase, int paramInt6, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, Producto paramProducto, Atributo paramAtributo, String paramString3, DimensionContable paramDimensionContable);
  
  public abstract List<DetalleFormaCobro> getReporteListaRetencionVentas(Date paramDate1, Date paramDate2, int paramInt1, int paramInt2, Sucursal paramSucursal, boolean paramBoolean, PuntoDeVenta paramPuntoDeVenta);
  
  public abstract List<DetalleCobro> getReporteListaDetalleCobro(List<Cobro> paramList);
  
  public abstract List getListaReporteFacturacionProductoResumido(Date paramDate1, Date paramDate2, String paramString1, String paramString2, int paramInt1, int paramInt2, boolean paramBoolean1, int paramInt3, int paramInt4, Sucursal paramSucursal, PuntoDeVenta paramPuntoDeVenta, TipoVentaEnum paramTipoVentaEnum, boolean paramBoolean2, int paramInt5, DocumentoBase paramDocumentoBase, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, Producto paramProducto, Atributo paramAtributo, String paramString3, DimensionContable paramDimensionContable);
  
  public abstract List<Object[]> getReporteAnalisisVentasCliente(Date paramDate1, Date paramDate2, boolean paramBoolean1, int paramInt1, int paramInt2, boolean paramBoolean2);
  
  public abstract List getReporteAnalisisVentasProducto(Date paramDate1, Date paramDate2, boolean paramBoolean1, int paramInt1, int paramInt2, boolean paramBoolean2);
  
  public abstract List getListaReporteVentasCombustible(Date paramDate1, Date paramDate2, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
  
  public abstract List getReporteFacturaDespacho(Date paramDate1, Date paramDate2, int paramInt1, boolean paramBoolean, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, int paramInt2);
  
  public abstract List getReporteDespachoFactura(Date paramDate1, Date paramDate2, int paramInt1, boolean paramBoolean, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, int paramInt2);
  
  public abstract List getSaldosFactura(Date paramDate, int paramInt)
    throws ExcepcionAS2;
  
  public abstract List<Object[]> getReporteResultadoNetoProductosVendidos(Date paramDate1, Date paramDate2, int paramInt, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, Atributo paramAtributo, String paramString1, String paramString2, Sucursal paramSucursal, boolean paramBoolean, CategoriaEmpresa paramCategoriaEmpresa, Empresa paramEmpresa);
  
  public abstract List<Empresa> getEmpresasEnvioEmailVencimiento(int paramInt1, Date paramDate, int paramInt2, int paramInt3, int paramInt4, int paramInt5, Sucursal paramSucursal, PuntoDeVenta paramPuntoDeVenta, Zona paramZona, CategoriaEmpresa paramCategoriaEmpresa);
  
  public abstract void enviarEmail(Date paramDate, String paramString, Empresa paramEmpresa, byte[] paramArrayOfByte)
    throws IOException, MessagingException;
  
  public abstract List<Object[]> getReporteComportamientoCobroCliente(int paramInt, Date paramDate1, Date paramDate2, CategoriaEmpresa paramCategoriaEmpresa, Empresa paramEmpresa);
  
  public abstract List<Object[]> getReporteVencimientoMensual(int paramInt, Empresa paramEmpresa, Subempresa paramSubempresa, Date paramDate, boolean paramBoolean);
  
  public abstract List<Object[]> getReporteImpuestoVenta(Date paramDate1, Date paramDate2, Empresa paramEmpresa, CategoriaEmpresa paramCategoriaEmpresa, EntidadUsuario paramEntidadUsuario, boolean paramBoolean1, Canal paramCanal, Zona paramZona, int paramInt, DocumentoBase paramDocumentoBase, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, Producto paramProducto, boolean paramBoolean2, boolean paramBoolean3, Sucursal paramSucursal);
  
  public abstract Object[] getResumenVentaCliente(Date paramDate1, Date paramDate2, int paramInt);
  
  public abstract List<Object[]> getResumenProductosMasVendidosCliente(Date paramDate1, Date paramDate2, int paramInt);
  
  public abstract List<Object[]> getDetalleVentasProductoCliente(Date paramDate1, Date paramDate2, int paramInt1, int paramInt2);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta
 * JD-Core Version:    0.7.0.1
 */