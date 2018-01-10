package com.asinfo.as2.compras.reportes.servicio;

import com.asinfo.as2.clases.ReporteAnticipoProveedor;
import com.asinfo.as2.clases.ReporteFacturaProveedor;
import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
import com.asinfo.as2.entities.Atributo;
import com.asinfo.as2.entities.Bodega;
import com.asinfo.as2.entities.CategoriaEmpresa;
import com.asinfo.as2.entities.CategoriaProducto;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.FacturaProveedor;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.SubcategoriaProducto;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.entities.TipoOperacion;
import com.asinfo.as2.entities.ValorAtributo;
import com.asinfo.as2.entities.vista.VEstadoCuentaProveedor;
import com.asinfo.as2.enumeraciones.DocumentoBase;
import com.asinfo.as2.enumeraciones.OrdenamientoEnum;
import com.asinfo.as2.enumeraciones.TipoOrganizacion;
import com.asinfo.as2.enumeraciones.TipoProducto;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteCompra
{
  public abstract List<VEstadoCuentaProveedor> getAnalisisVencimientoProveedor(Date paramDate1, Date paramDate2, int paramInt1, int paramInt2, int paramInt3, TipoOrganizacion paramTipoOrganizacion, Sucursal paramSucursal, CategoriaEmpresa paramCategoriaEmpresa, boolean paramBoolean)
    throws ExcepcionAS2Compras;
  
  public abstract List<Object[]> getReportePedidoSaldoPorRecibir(Date paramDate1, Date paramDate2, int paramInt1, boolean paramBoolean, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, int paramInt2);
  
  public abstract List getListaReporteFacturacionResumidoCompra(Date paramDate1, Date paramDate2, String paramString1, String paramString2, int paramInt1, int paramInt2, boolean paramBoolean1, int paramInt3, int paramInt4, boolean paramBoolean2, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, List<Producto> paramList, Sucursal paramSucursal, DocumentoBase paramDocumentoBase)
    throws ExcepcionAS2Ventas;
  
  public abstract List getListaReporteCorteFechaResumido(Date paramDate, int paramInt1, int paramInt2)
    throws ExcepcionAS2Ventas;
  
  public abstract List getListaReporteCorteFecha(Date paramDate, int paramInt1, int paramInt2, int paramInt3, Sucursal paramSucursal, CategoriaEmpresa paramCategoriaEmpresa)
    throws ExcepcionAS2;
  
  public abstract BigDecimal obtenerSaldoEstadoCuenta(int paramInt1, Date paramDate, int paramInt2);
  
  public abstract List getListaReporteEstadoCuenta(Date paramDate1, Date paramDate2, int paramInt1, int paramInt2, OrdenamientoEnum paramOrdenamientoEnum, boolean paramBoolean, int paramInt3, Sucursal paramSucursal, CategoriaEmpresa paramCategoriaEmpresa)
    throws ExcepcionAS2Ventas;
  
  public abstract List getReporteFacturaProveedor(int paramInt);
  
  public abstract List getVencimientos(Empresa paramEmpresa, Date paramDate, int paramInt, TipoOperacion paramTipoOperacion, CategoriaEmpresa paramCategoriaEmpresa, boolean paramBoolean)
    throws ExcepcionAS2;
  
  public abstract List<ReporteAnticipoProveedor> getReporteAnticipoProveedoresResumido(Date paramDate1, Date paramDate2, int paramInt1, int paramInt2, boolean paramBoolean)
    throws ExcepcionAS2;
  
  public abstract List<ReporteAnticipoProveedor> getReporteAnticipoProveedores(Date paramDate1, Date paramDate2, int paramInt1, int paramInt2, boolean paramBoolean)
    throws ExcepcionAS2;
  
  public abstract List<Object[]> getListaReporteEstadoCuenta(int paramInt, FacturaProveedor paramFacturaProveedor);
  
  public abstract List getReporteComparativoPrecioCompra(int paramInt, TipoProducto paramTipoProducto);
  
  public abstract List getReporteAnalisisComprasProveedor(Date paramDate1, Date paramDate2, boolean paramBoolean1, int paramInt1, int paramInt2, boolean paramBoolean2);
  
  public abstract List getReporteAnalisisComprasProducto(Date paramDate1, Date paramDate2, boolean paramBoolean1, int paramInt1, int paramInt2, boolean paramBoolean2);
  
  public abstract List<ReporteAnticipoProveedor> getReporteLiquidacionAnticipoProveedor(Date paramDate1, Date paramDate2, List<String> paramList, int paramInt1, int paramInt2, boolean paramBoolean)
    throws ExcepcionAS2;
  
  public abstract List<ReporteAnticipoProveedor> getReporteSaldoAnticipoProveedor(Date paramDate1, Date paramDate2, int paramInt1, int paramInt2)
    throws ExcepcionAS2;
  
  public abstract List<Object[]> getReporteFacturaRecepcion(Date paramDate1, Date paramDate2, int paramInt1, boolean paramBoolean, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, int paramInt2);
  
  public abstract List getReporteRecepcionFactura(Date paramDate1, Date paramDate2, int paramInt1, boolean paramBoolean, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, int paramInt2);
  
  public abstract List<Object[]> getReportePlanificacionCompras(int paramInt, Date paramDate, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, Producto paramProducto, Bodega paramBodega, Atributo paramAtributo, ValorAtributo paramValorAtributo, String paramString, Integer paramInteger);
  
  public abstract ReporteFacturaProveedor getReporteFacturaProveedorAsientos(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.reportes.servicio.ServicioReporteCompra
 * JD-Core Version:    0.7.0.1
 */