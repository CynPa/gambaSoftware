package com.asinfo.as2.financiero.SRI.procesos.servicio;

import com.asinfo.as2.clases.ReporteComprasVentasRetenciones;
import com.asinfo.as2.clases.ReporteRetencionesResumido;
import com.asinfo.as2.compronteselectronicos.base.DocumentoElectronico;
import com.asinfo.as2.compronteselectronicos.base.EstadoDocumentoElectronico;
import com.asinfo.as2.entities.CajaChica;
import com.asinfo.as2.entities.CompraCajaChica;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.FacturaProveedor;
import com.asinfo.as2.entities.PuntoDeVenta;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.entities.sri.ConceptoRetencionSRI;
import com.asinfo.as2.entities.sri.CreditoTributarioSRI;
import com.asinfo.as2.entities.sri.DetalleFacturaProveedorSRI;
import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
import com.asinfo.as2.entities.sri.ReembolsoGastos;
import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
import com.asinfo.as2.enumeraciones.DocumentoBase;
import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioFacturaProveedorSRI
{
  public abstract void guardar(FacturaProveedorSRI paramFacturaProveedorSRI)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void eliminar(FacturaProveedorSRI paramFacturaProveedorSRI);
  
  public abstract FacturaProveedorSRI buscarPorId(Integer paramInteger);
  
  public abstract List<FacturaProveedorSRI> obtenerFacturasMes(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract FacturaProveedorSRI cargarDetalle(int paramInt);
  
  public abstract FacturaProveedorSRI obtenerFacturaProveedorSRI(int paramInt);
  
  public abstract List<Object[]> getRetencionSRI(int paramInt1, int paramInt2, int paramInt3, Sucursal paramSucursal1, Sucursal paramSucursal2, PuntoDeVenta paramPuntoDeVenta, String paramString, boolean paramBoolean)
    throws ExcepcionAS2;
  
  public abstract List getReporteFacturaProveedorSRI(int paramInt)
    throws ExcepcionAS2;
  
  public abstract void actualizarEstado(Integer paramInteger, Estado paramEstado);
  
  public abstract void actualizarFacturaProveedorSRI(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2;
  
  public abstract boolean existeFactura(FacturaProveedorSRI paramFacturaProveedorSRI);
  
  public abstract List<FacturaProveedorSRI> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void actualizarFechaDeRegistroPorCajaChica(CajaChica paramCajaChica);
  
  public abstract List<FacturaProveedorSRI> obtenerFacturasPorEgresoPago(int paramInt);
  
  public abstract List<ReporteComprasVentasRetenciones> getReporteCompras(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract List<ReporteComprasVentasRetenciones> getReporteVentas(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean);
  
  public abstract List<ReporteComprasVentasRetenciones> getReporteExportaciones(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract List<ReporteComprasVentasRetenciones> getNumeroComprobantesAnulados(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract String actualizarValorRetencion(DetalleFacturaProveedorSRI paramDetalleFacturaProveedorSRI);
  
  public abstract void cargarDetalleRetencion(FacturaProveedorSRI paramFacturaProveedorSRI, ConceptoRetencionSRI paramConceptoRetencionSRI, BigDecimal paramBigDecimal);
  
  public abstract void cargarConceptosRetencion(FacturaProveedorSRI paramFacturaProveedorSRI, Empresa paramEmpresa)
    throws ExcepcionAS2;
  
  public abstract List<ReporteRetencionesResumido> getRetencionSRIResumido(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void actualizarRetencion(FacturaProveedorSRI paramFacturaProveedorSRI)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract void enviarMail(FacturaProveedorSRI paramFacturaProveedorSRI, String paramString)
    throws ExcepcionAS2;
  
  public abstract List<FacturaProveedorSRI> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract TipoComprobanteSRI cargarTipoComprobanteSRI(String paramString, int paramInt);
  
  public abstract List<FacturaProveedorSRI> migracionFacturaProveedorSRI(int paramInt1, String paramString, InputStream paramInputStream, int paramInt2)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void cargarDetalleIVARetencion(FacturaProveedorSRI paramFacturaProveedorSRI, ConceptoRetencionSRI paramConceptoRetencionSRI);
  
  public abstract List<ReembolsoGastos> listaReembolsoGastos(FacturaProveedorSRI paramFacturaProveedorSRI);
  
  public abstract void actualizarTipoComprobanteSRI(FacturaProveedorSRI paramFacturaProveedorSRI, TipoComprobanteSRI paramTipoComprobanteSRI, Date paramDate)
    throws ExcepcionAS2;
  
  public abstract List<ReporteComprasVentasRetenciones> getReporteRetencionClientes(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract CreditoTributarioSRI getCreditoTributarioSRI(FacturaProveedor paramFacturaProveedor);
  
  public abstract List<Object[]> getMontoBasePorTipoProducto(FacturaProveedor paramFacturaProveedor);
  
  public abstract void enviarMail(FacturaProveedorSRI paramFacturaProveedorSRI, DocumentoElectronico paramDocumentoElectronico, String paramString)
    throws ExcepcionAS2;
  
  public abstract void actualizaFacturaProveedorSRI(int paramInt, Estado paramEstado, EstadoDocumentoElectronico paramEstadoDocumentoElectronico, Date paramDate, String paramString1, String paramString2);
  
  public abstract BigDecimal valorAcumuladoCajaChica(CompraCajaChica paramCompraCajaChica, CajaChica paramCajaChica);
  
  public abstract List<FacturaProveedorSRI> obtenerRetencionesProveedor(int paramInt1, Date paramDate1, Date paramDate2, DocumentoBase paramDocumentoBase, int paramInt2);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI
 * JD-Core Version:    0.7.0.1
 */