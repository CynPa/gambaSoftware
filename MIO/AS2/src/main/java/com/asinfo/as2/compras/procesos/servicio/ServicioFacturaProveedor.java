package com.asinfo.as2.compras.procesos.servicio;

import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
import com.asinfo.as2.entities.CategoriaEmpresa;
import com.asinfo.as2.entities.CentroCosto;
import com.asinfo.as2.entities.CuentaContable;
import com.asinfo.as2.entities.CuentaPorPagar;
import com.asinfo.as2.entities.DetalleFacturaProveedor;
import com.asinfo.as2.entities.DimensionContable;
import com.asinfo.as2.entities.Documento;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.FacturaProveedor;
import com.asinfo.as2.entities.Pago;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.PuntoDeVenta;
import com.asinfo.as2.entities.RecepcionProveedor;
import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.enumeraciones.TipoServicioCuentaBancariaEnum;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioFacturaProveedor
{
  public abstract void guardar(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void eliminar(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2;
  
  public abstract FacturaProveedor buscarPorId(Integer paramInteger)
    throws ExcepcionAS2;
  
  public abstract FacturaProveedor cargarDetalle(Integer paramInteger)
    throws ExcepcionAS2Compras;
  
  public abstract void totalizar(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2Compras;
  
  public abstract List<CuentaPorPagar> obtenerFacturasPendientes(int paramInt1, int paramInt2);
  
  public abstract List<CuentaPorPagar> obtenerFacturasPendientes(int paramInt1, int paramInt2, int paramInt3, Date paramDate);
  
  public abstract List<CuentaPorPagar> obtenerFacturasPendientes(int paramInt1, int paramInt2, int paramInt3, Date paramDate, int paramInt4);
  
  public abstract List<CuentaPorPagar> obtenerFacturasPendientes(int paramInt, Date paramDate, TipoServicioCuentaBancariaEnum paramTipoServicioCuentaBancariaEnum, CategoriaEmpresa paramCategoriaEmpresa);
  
  public abstract List<CuentaPorPagar> obtenerFacturasPendientes(int paramInt, Date paramDate, TipoServicioCuentaBancariaEnum paramTipoServicioCuentaBancariaEnum, CategoriaEmpresa paramCategoriaEmpresa, Documento paramDocumento);
  
  public abstract void totalizarImpuesto(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2Compras;
  
  public abstract void generarCuentaPorPagar(FacturaProveedor paramFacturaProveedor);
  
  public abstract List<FacturaProveedor> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<FacturaProveedor> listaFacturasPorRecibir(Estado paramEstado, int paramInt);
  
  public abstract FacturaProveedor cargarInformacionSRI(Integer paramInteger)
    throws ExcepcionAS2Compras;
  
  public abstract List<FacturaProveedor> listaFacturaPorProveedor(Empresa paramEmpresa);
  
  public abstract BigDecimal getSumaImpuestoPorIdFacturaProveedor(int paramInt);
  
  public abstract void esEditable(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2Compras, ExcepcionAS2Financiero;
  
  public abstract void anular(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2Compras, ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void actualizarEstado(Integer paramInteger, Estado paramEstado);
  
  public abstract void contabilizar(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception;
  
  public abstract List<FacturaProveedor> obtenerListaComboConEqual(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract FacturaProveedor copiarFacturaProveedor(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2;
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract void obtenerImpuestosProductos(Producto paramProducto, DetalleFacturaProveedor paramDetalleFacturaProveedor)
    throws ExcepcionAS2Inventario;
  
  public abstract void cargarSecuencia(FacturaProveedor paramFacturaProveedor, PuntoDeVenta paramPuntoDeVenta)
    throws ExcepcionAS2;
  
  public abstract long verificaExistenciaNumero(FacturaProveedor paramFacturaProveedor);
  
  public abstract void agregarGastoProductoFacturaProveedor(DetalleFacturaProveedor paramDetalleFacturaProveedor, CentroCosto paramCentroCosto)
    throws ExcepcionAS2;
  
  public abstract void agregarGastoProductoFacturaProveedor(DetalleFacturaProveedor paramDetalleFacturaProveedor, DimensionContable paramDimensionContable1, DimensionContable paramDimensionContable2, DimensionContable paramDimensionContable3, DimensionContable paramDimensionContable4, DimensionContable paramDimensionContable5, CuentaContable paramCuentaContable1, CuentaContable paramCuentaContable2)
    throws ExcepcionAS2;
  
  public abstract void actualizarAtributoEntidad(FacturaProveedor paramFacturaProveedor, HashMap<String, Object> paramHashMap);
  
  public abstract FacturaProveedor buscarPorRecepcionProveedor(int paramInt);
  
  public abstract RecepcionProveedor cargarDetalleARecibir(RecepcionProveedor paramRecepcionProveedor, Integer paramInteger, List<DetalleFacturaProveedor> paramList);
  
  public abstract FacturaProveedor copiarFacturaProveedor(FacturaProveedor paramFacturaProveedor1, FacturaProveedor paramFacturaProveedor2)
    throws ExcepcionAS2;
  
  public abstract void validarBodega(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2;
  
  public abstract Object[] getDatosFacturaImpresionAsiento(int paramInt);
  
  public abstract void liberarRecepciones(FacturaProveedor paramFacturaProveedor);
  
  public abstract List<FacturaProveedor> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<FacturaProveedor> obtenerListaComboAutocompletar(int paramInt, String paramString);
  
  public abstract void guardarAux(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2;
  
  public abstract List<CuentaPorPagar> obtenerFacturasPendientesLiquidacionCuentaPorPagar(Pago paramPago, int paramInt, BigDecimal paramBigDecimal, boolean paramBoolean);
  
  public abstract void bloquearFactura(int paramInt, boolean paramBoolean);
  
  public abstract List<FacturaProveedor> autocompletarFacturaProveedorDevolucion(int paramInt, Map<String, String> paramMap);
  
  public abstract void anularFacturaProveedorImportacion(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2, ExcepcionAS2Compras, ExcepcionAS2Financiero;
  
  public abstract List<DetalleFacturaProveedor> buscarDetallesNoDespachados(Integer paramInteger);
  
  public abstract void validaReembolsoGastos(FacturaProveedorSRI paramFacturaProveedorSRI)
    throws AS2Exception;
  
  public abstract void agregarDetalleFacturaSRI332(FacturaProveedorSRI paramFacturaProveedorSRI);
  
  public abstract void anularRetencion(FacturaProveedorSRI paramFacturaProveedorSRI)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void guardar(FacturaProveedor paramFacturaProveedor, boolean paramBoolean)
    throws ExcepcionAS2, ExcepcionAS2Compras, ExcepcionAS2Financiero, AS2Exception;
  
  public abstract List<CuentaPorPagar> obtenerFacturasPendientes(int paramInt1, int paramInt2, Date paramDate1, TipoServicioCuentaBancariaEnum paramTipoServicioCuentaBancariaEnum, CategoriaEmpresa paramCategoriaEmpresa, int paramInt3, Documento paramDocumento, Boolean paramBoolean, Integer paramInteger, Date paramDate2);
  
  public abstract void liberarRecepcionFacturaProveedor(RecepcionProveedor paramRecepcionProveedor);
  
  public abstract List<CuentaPorPagar> obtenerFacturasPendientes(int paramInt1, int paramInt2, Date paramDate1, TipoServicioCuentaBancariaEnum paramTipoServicioCuentaBancariaEnum, CategoriaEmpresa paramCategoriaEmpresa, int paramInt3, Documento paramDocumento, Boolean paramBoolean1, Integer paramInteger, Date paramDate2, Boolean paramBoolean2);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor
 * JD-Core Version:    0.7.0.1
 */