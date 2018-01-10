package com.asinfo.as2.ventas.procesos.servicio;

import com.asinfo.as2.entities.DetallePedidoCliente;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.Organizacion;
import com.asinfo.as2.entities.PedidoCliente;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.RegistroPeso;
import com.asinfo.as2.entities.Ruta;
import com.asinfo.as2.entities.Subempresa;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.entities.Transportista;
import com.asinfo.as2.enumeraciones.DocumentoBase;
import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
import com.asinfo.as2.rs.ventas.dto.DetalleCalculoImpuestoResponseDto;
import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPedidoCliente
{
  public abstract void guardar(PedidoCliente paramPedidoCliente)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void eliminar(PedidoCliente paramPedidoCliente)
    throws ExcepcionAS2;
  
  public abstract PedidoCliente buscarPorId(Integer paramInteger)
    throws ExcepcionAS2;
  
  public abstract PedidoCliente cargarDetalle(int paramInt);
  
  public abstract void totalizar(PedidoCliente paramPedidoCliente)
    throws ExcepcionAS2Ventas;
  
  public abstract void totalizarImpuesto(PedidoCliente paramPedidoCliente)
    throws ExcepcionAS2Ventas;
  
  public abstract List<PedidoCliente> listaPedidosPorDespachar(int paramInt);
  
  public abstract List<PedidoCliente> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap)
    throws ExcepcionAS2;
  
  public abstract List<PedidoCliente> listaPedidosPorFacturar(Estado paramEstado, int paramInt)
    throws ExcepcionAS2;
  
  public abstract List getReportePedidoCliente(int paramInt)
    throws ExcepcionAS2;
  
  public abstract List<DetallePedidoCliente> getPedidosVSInventario(Date paramDate);
  
  public abstract List<DetallePedidoCliente> obtenerListaDetallePedidoPorDespachar(int paramInt);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract void actualizarEstado(PedidoCliente paramPedidoCliente, Estado paramEstado, String paramString);
  
  public abstract void esEditable(PedidoCliente paramPedidoCliente)
    throws ExcepcionAS2Ventas, ExcepcionAS2Financiero;
  
  public abstract void anular(PedidoCliente paramPedidoCliente)
    throws ExcepcionAS2Ventas, ExcepcionAS2Financiero;
  
  public abstract void actualizarCantidadPorDespachar(List<Integer> paramList);
  
  public abstract List<PedidoCliente> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void cerrarPedidoCliente(PedidoCliente paramPedidoCliente)
    throws ExcepcionAS2Inventario;
  
  public abstract void abrirPedidoCliente(PedidoCliente paramPedidoCliente)
    throws ExcepcionAS2Inventario;
  
  public abstract boolean esPedidoPorDespachar(int paramInt);
  
  public abstract void cierreAutomatico(PedidoCliente paramPedidoCliente)
    throws ExcepcionAS2Inventario;
  
  public abstract List<DetallePedidoCliente> obtenerListaDetallePedidoPorFacturar(int paramInt);
  
  public abstract void cargarDetallePedidoClienteExcel(PedidoCliente paramPedidoCliente, String paramString, InputStream paramInputStream, int paramInt)
    throws ExcepcionAS2;
  
  public abstract PedidoCliente copiarPedidoCliente(PedidoCliente paramPedidoCliente)
    throws ExcepcionAS2Financiero;
  
  public abstract List<PedidoCliente> listaPedidosPorDespachar(Organizacion paramOrganizacion);
  
  public abstract Object[] cargarPedidosClienteExcelSupermaxi(PedidoCliente paramPedidoCliente, String paramString, InputStream paramInputStream, int paramInt)
    throws AS2Exception;
  
  public abstract Object[] cargarPedidosClienteCSVSantaMaria(PedidoCliente paramPedidoCliente, InputStream paramInputStream)
    throws AS2Exception;
  
  public abstract void obtenerImpuestosProductos(Producto paramProducto, DetallePedidoCliente paramDetallePedidoCliente)
    throws ExcepcionAS2;
  
  public abstract PedidoCliente guardarLogistica(PedidoCliente paramPedidoCliente1, PedidoCliente paramPedidoCliente2)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract void procesarPedidoCliente(PedidoCliente paramPedidoCliente, Boolean paramBoolean, boolean paramBoolean1, String paramString, Date paramDate)
    throws ExcepcionAS2Inventario;
  
  public abstract List<Object[]> getReporteLogisticaTransportista(Date paramDate1, Date paramDate2, int paramInt, Transportista paramTransportista, Ruta paramRuta, Empresa paramEmpresa);
  
  public abstract PedidoCliente cargarDetalle(int paramInt, boolean paramBoolean);
  
  public abstract List<Object[]> getReporteProductoPedidoVsFactura(Date paramDate1, Date paramDate2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, DocumentoBase paramDocumentoBase);
  
  public abstract DetalleCalculoImpuestoResponseDto calcularImpuestosDetalleProducto(Producto paramProducto, boolean paramBoolean, Date paramDate, Sucursal paramSucursal, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2)
    throws ExcepcionAS2;
  
  public abstract void actualizarAtributoEntidad(PedidoCliente paramPedidoCliente, HashMap<String, Object> paramHashMap);
  
  public abstract List<Producto> cargarPedidoSugerido(int paramInt, Empresa paramEmpresa, Subempresa paramSubempresa)
    throws AS2Exception;
  
  public abstract List<PedidoCliente> getPedidosClienteNoExistentesRegistroPeso(Estado paramEstado, Empresa paramEmpresa, RegistroPeso paramRegistroPeso);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.ServicioPedidoCliente
 * JD-Core Version:    0.7.0.1
 */