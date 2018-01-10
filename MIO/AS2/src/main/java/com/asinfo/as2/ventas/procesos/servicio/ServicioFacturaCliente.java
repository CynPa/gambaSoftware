package com.asinfo.as2.ventas.procesos.servicio;

import com.asinfo.as2.compronteselectronicos.base.DocumentoElectronico;
import com.asinfo.as2.compronteselectronicos.base.EstadoDocumentoElectronico;
import com.asinfo.as2.entities.Canal;
import com.asinfo.as2.entities.CuentaPorCobrar;
import com.asinfo.as2.entities.DespachoCliente;
import com.asinfo.as2.entities.DetalleFacturaCliente;
import com.asinfo.as2.entities.DetallePedidoCliente;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.HojaRuta;
import com.asinfo.as2.entities.MotivoAnulacion;
import com.asinfo.as2.entities.NotaFacturaCliente;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.PuntoDeVenta;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.entities.Transportista;
import com.asinfo.as2.entities.Zona;
import com.asinfo.as2.entities.sri.FacturaClienteSRI;
import com.asinfo.as2.enumeraciones.DocumentoBase;
import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.enumeraciones.TipoOrganizacion;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioFacturaCliente
{
  public abstract com.asinfo.as2.entities.FacturaCliente guardar(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2Ventas, ExcepcionAS2Inventario, ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract void eliminar(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2;
  
  public abstract com.asinfo.as2.entities.FacturaCliente buscarPorId(Integer paramInteger)
    throws ExcepcionAS2;
  
  public abstract com.asinfo.as2.entities.FacturaCliente cargarDetalle(int paramInt)
    throws ExcepcionAS2Ventas;
  
  public abstract com.asinfo.as2.entities.FacturaCliente totalizar(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2Ventas;
  
  public abstract List<CuentaPorCobrar> obtenerFacturasPendientes(int paramInt);
  
  public abstract List<CuentaPorCobrar> obtenerFacturasPendientes(int paramInt1, int paramInt2);
  
  public abstract List<CuentaPorCobrar> obtenerFacturasPendientes(int paramInt1, int paramInt2, String paramString);
  
  public abstract com.asinfo.as2.entities.FacturaCliente totalizarImpuesto(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2Ventas;
  
  public abstract com.asinfo.as2.entities.FacturaCliente generarCuentaPorCobrar(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2;
  
  public abstract DetalleFacturaCliente getDetalleFacturaPedidoCliente(DetallePedidoCliente paramDetallePedidoCliente)
    throws ExcepcionAS2Ventas;
  
  public abstract List<com.asinfo.as2.entities.FacturaCliente> listaFacturasPorDespachar(Estado paramEstado, int paramInt);
  
  public abstract List<com.asinfo.as2.entities.FacturaCliente> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<com.asinfo.as2.entities.FacturaCliente> obtenerListaComboConEqual(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract BigDecimal getSumaImpuestoPorIdFacturaCliente(int paramInt);
  
  public abstract List getReporteFacturaCliente(int paramInt1, TipoOrganizacion paramTipoOrganizacion, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, int paramInt3)
    throws ExcepcionAS2;
  
  public abstract void anulaFacturaCliente(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2, ExcepcionAS2Ventas, ExcepcionAS2Financiero;
  
  public abstract void actualizarEstado(int paramInt, Estado paramEstado, MotivoAnulacion paramMotivoAnulacion);
  
  public abstract void esEditable(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2Ventas, ExcepcionAS2Financiero;
  
  public abstract com.asinfo.as2.entities.FacturaCliente cargarSecuencia(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente, PuntoDeVenta paramPuntoDeVenta)
    throws ExcepcionAS2;
  
  public abstract void obtenerImpuestosProductos(Producto paramProducto, DetalleFacturaCliente paramDetalleFacturaCliente)
    throws ExcepcionAS2Inventario;
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract long verificaExistenciaNumero(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente);
  
  public abstract com.asinfo.as2.entities.FacturaCliente buscarPorDespachoCliente(Integer paramInteger);
  
  public abstract com.asinfo.as2.entities.FacturaCliente cargarCabecera(Integer paramInteger);
  
  public abstract com.asinfo.as2.entities.FacturaCliente buscarFacturaCliente(Map<String, String> paramMap)
    throws ExcepcionAS2;
  
  public abstract void validarCambioNumeroFactura(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2Ventas;
  
  public abstract void actualizarNumeroFactura(FacturaClienteSRI paramFacturaClienteSRI)
    throws ExcepcionAS2Ventas, ExcepcionAS2Financiero;
  
  public abstract void validarBodega(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2;
  
  public abstract List<com.asinfo.as2.entities.FacturaCliente> migracionFacturaCliente(int paramInt1, String paramString, InputStream paramInputStream, int paramInt2)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract com.asinfo.as2.entities.FacturaCliente copiarFacturaCliente(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente1, com.asinfo.as2.entities.FacturaCliente paramFacturaCliente2)
    throws ExcepcionAS2;
  
  public abstract void liberarDespachos(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente)
    throws AS2Exception;
  
  public abstract void actualizarCabeceraFacturaCliente(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2Financiero;
  
  public abstract List<com.asinfo.as2.entities.FacturaCliente> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void verificaNumeroFacturaRangoSecuencia(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2Financiero;
  
  public abstract List<CuentaPorCobrar> obtenerFacturasPendientesLiquidacionCuentaPorCobrar(int paramInt1, int paramInt2, Date paramDate, BigDecimal paramBigDecimal);
  
  public abstract void anularFacturaClientePos(com.asinfo.pos.model.FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2Ventas, ExcepcionAS2Inventario, ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract List<Object[]> getListaDevolucionCliente(int paramInt1, int paramInt2)
    throws ExcepcionAS2;
  
  public abstract List<com.asinfo.as2.entities.FacturaCliente> obtenerFacturasMes(Date paramDate1, Date paramDate2, Sucursal paramSucursal, PuntoDeVenta paramPuntoDeVenta, int paramInt, DocumentoBase paramDocumentoBase, boolean paramBoolean);
  
  public abstract List<com.asinfo.as2.entities.FacturaCliente> obtenerFacturasMes(Date paramDate1, Date paramDate2, Sucursal paramSucursal, PuntoDeVenta paramPuntoDeVenta, int paramInt, DocumentoBase paramDocumentoBase);
  
  public abstract List<com.asinfo.as2.entities.FacturaCliente> getListaNotaCreditoCliente(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente1, com.asinfo.as2.entities.FacturaCliente paramFacturaCliente2);
  
  public abstract void cargarAutorizacionAutoImpresor(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2Financiero;
  
  public abstract void anulaFacturaCliente(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente, Sucursal paramSucursal)
    throws ExcepcionAS2, ExcepcionAS2Ventas, ExcepcionAS2Financiero;
  
  public abstract com.asinfo.as2.entities.FacturaCliente buscarFacturaClientePorNumero(int paramInt, String paramString, DocumentoBase paramDocumentoBase);
  
  public abstract void guardarFacturaClienteRevisadas(List<com.asinfo.as2.entities.FacturaCliente> paramList)
    throws ExcepcionAS2Ventas, ExcepcionAS2Inventario, ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract void enviarMail(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente, String paramString)
    throws ExcepcionAS2;
  
  public abstract PuntoDeVenta cargarPuntoVenta(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2;
  
  public abstract List<DetalleFacturaCliente> buscarDetallesNoDespachados(Integer paramInteger);
  
  public abstract List<com.asinfo.as2.entities.FacturaCliente> cargaNotaCreditoCliente(int paramInt1, String paramString, InputStream paramInputStream, int paramInt2, PuntoDeVenta paramPuntoDeVenta)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void guardarCuotas(List<CuentaPorCobrar> paramList, BigDecimal paramBigDecimal)
    throws AS2Exception;
  
  public abstract com.asinfo.as2.entities.FacturaCliente obtenerUltimaFacturaAutorizadaPorCliente(int paramInt, Empresa paramEmpresa, boolean paramBoolean, BigDecimal paramBigDecimal);
  
  public abstract List<Object[]> getReporteFacturaCliente(int paramInt1, int paramInt2)
    throws ExcepcionAS2;
  
  public abstract List<com.asinfo.as2.entities.FacturaCliente> listaFacturas(int paramInt, Date paramDate1, Date paramDate2, Transportista paramTransportista, Zona paramZona, Canal paramCanal, HojaRuta paramHojaRuta);
  
  public abstract void facturaEnLote(int paramInt, Sucursal paramSucursal, HojaRuta paramHojaRuta, Transportista paramTransportista, Date paramDate1, Date paramDate2, PuntoDeVenta paramPuntoDeVenta)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void actualizarValorDevuelto(int paramInt, BigDecimal paramBigDecimal);
  
  public abstract void actualizarDocumento(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente, boolean paramBoolean, PuntoDeVenta paramPuntoDeVenta)
    throws ExcepcionAS2;
  
  public abstract ExcepcionAS2 agregarDetalleDespachoAFactura(DespachoCliente paramDespachoCliente, com.asinfo.as2.entities.FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2;
  
  public abstract ExcepcionAS2 agregarDetalleDespachoAFactura(DespachoCliente paramDespachoCliente, com.asinfo.as2.entities.FacturaCliente paramFacturaCliente, boolean paramBoolean)
    throws ExcepcionAS2;
  
  public abstract void actualizarProducto(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente, DetalleFacturaCliente paramDetalleFacturaCliente, Producto paramProducto, boolean paramBoolean1, TipoOrganizacion paramTipoOrganizacion, boolean paramBoolean2)
    throws ExcepcionAS2Inventario, ExcepcionAS2;
  
  public abstract com.asinfo.as2.entities.FacturaCliente crearFacturaDesdeDespacho(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente, int paramInt, PuntoDeVenta paramPuntoDeVenta)
    throws AS2Exception, ExcepcionAS2;
  
  public abstract void guardarNotaFacturaCliente(NotaFacturaCliente paramNotaFacturaCliente);
  
  public abstract List<NotaFacturaCliente> cargarListaNotaFacturaCliente(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente);
  
  public abstract com.asinfo.as2.entities.FacturaCliente cargarDetalle(int paramInt, boolean paramBoolean);
  
  public abstract com.asinfo.as2.entities.FacturaCliente crearFacturaDesdeDespacho(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente, int paramInt, PuntoDeVenta paramPuntoDeVenta, Sucursal paramSucursal)
    throws AS2Exception, ExcepcionAS2;
  
  public abstract com.asinfo.as2.entities.FacturaCliente crearFacturaDesdeDespacho(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente, int paramInt, PuntoDeVenta paramPuntoDeVenta, Sucursal paramSucursal, boolean paramBoolean)
    throws AS2Exception, ExcepcionAS2;
  
  public abstract void actualizaFacturaClienteSRI(int paramInt, Estado paramEstado, EstadoDocumentoElectronico paramEstadoDocumentoElectronico, Date paramDate, String paramString1, String paramString2);
  
  public abstract void enviarMail(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente, DocumentoElectronico paramDocumentoElectronico, String paramString)
    throws ExcepcionAS2;
  
  public abstract List<com.asinfo.as2.entities.FacturaCliente> obtenerFacturasNotasCredito(int paramInt1, Date paramDate1, Date paramDate2, DocumentoBase paramDocumentoBase, int paramInt2);
  
  public abstract void anulaFacturaCliente(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente, boolean paramBoolean)
    throws ExcepcionAS2, ExcepcionAS2Ventas, ExcepcionAS2Financiero;
  
  public abstract void actualizarAtributoEntidad(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente, HashMap<String, Object> paramHashMap);
  
  public abstract void actualizarProducto(com.asinfo.as2.entities.FacturaCliente paramFacturaCliente, DetalleFacturaCliente paramDetalleFacturaCliente, Producto paramProducto, boolean paramBoolean, TipoOrganizacion paramTipoOrganizacion)
    throws ExcepcionAS2Inventario, ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente
 * JD-Core Version:    0.7.0.1
 */