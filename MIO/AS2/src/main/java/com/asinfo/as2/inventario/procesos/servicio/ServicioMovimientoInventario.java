package com.asinfo.as2.inventario.procesos.servicio;

import com.asinfo.as2.entities.Bodega;
import com.asinfo.as2.entities.CategoriaProducto;
import com.asinfo.as2.entities.DetalleFacturaCliente;
import com.asinfo.as2.entities.DetalleFacturaProveedor;
import com.asinfo.as2.entities.DetalleMovimientoInventario;
import com.asinfo.as2.entities.DetalleRecepcionProveedor;
import com.asinfo.as2.entities.FacturaCliente;
import com.asinfo.as2.entities.LecturaBalanza;
import com.asinfo.as2.entities.Lote;
import com.asinfo.as2.entities.MovimientoInventario;
import com.asinfo.as2.entities.OrdenDespachoCliente;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.RegistroPeso;
import com.asinfo.as2.entities.produccion.OrdenFabricacion;
import com.asinfo.as2.enumeraciones.DocumentoBase;
import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.enumeraciones.TipoOrganizacion;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
import com.asinfo.excepciones.ExcepcionAS2Identification;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioMovimientoInventario
{
  public abstract void guardar(MovimientoInventario paramMovimientoInventario, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void guardar(MovimientoInventario paramMovimientoInventario, boolean paramBoolean1, boolean paramBoolean2)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void guardar(MovimientoInventario paramMovimientoInventario)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void eliminar(MovimientoInventario paramMovimientoInventario)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract MovimientoInventario buscarPorId(Integer paramInteger);
  
  public abstract MovimientoInventario cargarDetalle(Integer paramInteger);
  
  public abstract List<MovimientoInventario> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<DetalleFacturaCliente> getDetalleFacturaDevolucionCliente(int paramInt1, int paramInt2);
  
  public abstract List<DetalleFacturaProveedor> getDetalleFacturaDevolucionProveedor(int paramInt1, int paramInt2);
  
  public abstract boolean verificaAjusteTomaFisica(int paramInt);
  
  public abstract void contabilizar(MovimientoInventario paramMovimientoInventario)
    throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception;
  
  public abstract void esEditable(MovimientoInventario paramMovimientoInventario)
    throws ExcepcionAS2Financiero, ExcepcionAS2Inventario;
  
  public abstract void anular(MovimientoInventario paramMovimientoInventario)
    throws ExcepcionAS2Financiero, ExcepcionAS2Inventario;
  
  public abstract void actualizarEstado(Integer paramInteger, Estado paramEstado);
  
  public abstract void cargarDetalleAjusteInventario(MovimientoInventario paramMovimientoInventario, String paramString, InputStream paramInputStream, int paramInt)
    throws ExcepcionAS2;
  
  public abstract int contarPorCirterio(Map<String, String> paramMap);
  
  public abstract void actualizarAsientoAjusteInventario(int paramInt, Date paramDate1, Date paramDate2, TipoOrganizacion paramTipoOrganizacion)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract void cargarDetalleConsumoBodega(MovimientoInventario paramMovimientoInventario, String paramString, InputStream paramInputStream, int paramInt)
    throws ExcepcionAS2;
  
  public abstract void cargarDetalleTransferenciaArchivoTexto(TipoOrganizacion paramTipoOrganizacion, MovimientoInventario paramMovimientoInventario, String paramString, InputStream paramInputStream, Bodega paramBodega1, Bodega paramBodega2)
    throws ExcepcionAS2;
  
  public abstract MovimientoInventario copiarMovimientoInventario(MovimientoInventario paramMovimientoInventario)
    throws ExcepcionAS2Financiero;
  
  public abstract List getReporteAprobarAjusteInventario(int paramInt);
  
  public abstract List<MovimientoInventario> getListaAjusteInvetario(int paramInt, Date paramDate1, Date paramDate2, TipoOrganizacion paramTipoOrganizacion);
  
  public abstract void guardarDetalleMovimiento(MovimientoInventario paramMovimientoInventario)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract void cargarDetalleTransferenciaBodega(MovimientoInventario paramMovimientoInventario, String paramString, InputStream paramInputStream, int paramInt)
    throws ExcepcionAS2;
  
  public abstract List<MovimientoInventario> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void eliminarDetalleIngresoFabricacion(MovimientoInventario paramMovimientoInventario, DetalleMovimientoInventario paramDetalleMovimientoInventario, boolean paramBoolean)
    throws ExcepcionAS2Inventario, ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract List<DetalleMovimientoInventario> obtenerListaDetalleRecepcionFabricacion(int paramInt, Bodega paramBodega);
  
  public abstract List<DetalleMovimientoInventario> obtenerListaDetalleRecepcionFabricacion(int paramInt, Bodega paramBodega, String paramString, List<Integer> paramList);
  
  public abstract void recepcionarDetalleIngresoFabricacion(DetalleMovimientoInventario paramDetalleMovimientoInventario)
    throws ExcepcionAS2, AS2Exception, ExcepcionAS2Financiero;
  
  public abstract void contabilizarIngresoFabricacion(MovimientoInventario paramMovimientoInventario)
    throws AS2Exception, ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void guardaTransferenciaBodegaIngreso(MovimientoInventario paramMovimientoInventario, boolean paramBoolean1, boolean paramBoolean2)
    throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception;
  
  public abstract void guardaTransferenciaBodegaIngreso(MovimientoInventario paramMovimientoInventario, boolean paramBoolean)
    throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception;
  
  public abstract Map<Integer, MovimientoInventario> crearAjustesInventarioDiferenciasRecepcionTransferencia(MovimientoInventario paramMovimientoInventario);
  
  public abstract boolean existenDiferenciasRecepcionTransferencia(MovimientoInventario paramMovimientoInventario, boolean paramBoolean)
    throws AS2Exception;
  
  public abstract void guardarRecepcionTransferenciaConAjusteInventario(List<MovimientoInventario> paramList, MovimientoInventario paramMovimientoInventario)
    throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception;
  
  public abstract void cargarSaldosBodegaOrigenTransferencia(MovimientoInventario paramMovimientoInventario)
    throws AS2Exception, ExcepcionAS2;
  
  public abstract MovimientoInventario obtenerIngresoFabricacionPorFecha(int paramInt, Date paramDate, OrdenFabricacion paramOrdenFabricacion);
  
  public abstract MovimientoInventario cargarDetallesDiariosIngresoFabricacion(MovimientoInventario paramMovimientoInventario, CategoriaProducto paramCategoriaProducto, int paramInt);
  
  public abstract List<MovimientoInventario> getListaAjusteInvetario(int paramInt, Date paramDate1, Date paramDate2, TipoOrganizacion paramTipoOrganizacion, List<DocumentoBase> paramList);
  
  public abstract MovimientoInventario obtenerUltimaTransformacion(Producto paramProducto, Lote paramLote);
  
  public abstract MovimientoInventario guardaTransformacionRapidaProducto(Map<String, Producto> paramMap, Date paramDate, OrdenDespachoCliente paramOrdenDespachoCliente, boolean paramBoolean, int paramInt, FacturaCliente paramFacturaCliente)
    throws AS2Exception, ExcepcionAS2Inventario, ExcepcionAS2;
  
  public abstract MovimientoInventario guardaTransformacionProducto(MovimientoInventario paramMovimientoInventario, boolean paramBoolean, BigDecimal paramBigDecimal, Bodega paramBodega, FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception;
  
  public abstract MovimientoInventario guardaTransformacionSubordenes(OrdenFabricacion paramOrdenFabricacion, BigDecimal paramBigDecimal, Date paramDate, Producto paramProducto, Lote paramLote)
    throws AS2Exception, ExcepcionAS2Inventario, ExcepcionAS2;
  
  public abstract void cargarDetallesConsumoBodegaADevolver(MovimientoInventario paramMovimientoInventario)
    throws ExcepcionAS2;
  
  public abstract MovimientoInventario cargarDetalle(int paramInt, Producto paramProducto);
  
  public abstract MovimientoInventario guardarIngresoFabricacion(MovimientoInventario paramMovimientoInventario, DetalleMovimientoInventario paramDetalleMovimientoInventario, LecturaBalanza paramLecturaBalanza, TipoOrganizacion paramTipoOrganizacion)
    throws AS2Exception, ExcepcionAS2Inventario, ExcepcionAS2, ExcepcionAS2Financiero;
  
  public abstract void generarTransformacionUnidadInformativa(DetalleMovimientoInventario paramDetalleMovimientoInventario, DetalleRecepcionProveedor paramDetalleRecepcionProveedor)
    throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception;
  
  public abstract void eliminarTransformacionProductoMateriales(MovimientoInventario paramMovimientoInventario)
    throws ExcepcionAS2;
  
  public abstract void agregarInventariosProducto(MovimientoInventario paramMovimientoInventario);
  
  public abstract void guardarRecepcionTransferenciaConAjusteInventario(List<MovimientoInventario> paramList, MovimientoInventario paramMovimientoInventario, RegistroPeso paramRegistroPeso)
    throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception, ExcepcionAS2Identification;
  
  public abstract void guardar(MovimientoInventario paramMovimientoInventario, Producto paramProducto)
    throws ExcepcionAS2, ExcepcionAS2Inventario, AS2Exception;
  
  public abstract void contabilizarConsumoBodega(List<MovimientoInventario> paramList)
    throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception;
  
  public abstract List<MovimientoInventario> obtenerIngresoFabricacionDelMes(Date paramDate, OrdenFabricacion paramOrdenFabricacion);
  
  public abstract void anularTransformacionProducto(MovimientoInventario paramMovimientoInventario)
    throws ExcepcionAS2Financiero, ExcepcionAS2Inventario;
  
  public abstract void generarTransformacionUnidadInformativa(DetalleMovimientoInventario paramDetalleMovimientoInventario, DetalleRecepcionProveedor paramDetalleRecepcionProveedor, DetalleFacturaProveedor paramDetalleFacturaProveedor)
    throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception;
  
  public abstract List<DetalleMovimientoInventario> autocompletarIngresosFabricacion(int paramInt, String paramString, CategoriaProducto paramCategoriaProducto);
  
  public abstract void actualizarInventarioProductoDetalle(DetalleMovimientoInventario paramDetalleMovimientoInventario)
    throws ExcepcionAS2;
  
  public abstract Map<Integer, MovimientoInventario> crearAjustesInventarioDiferenciasRecepcionTransferenciaRegistroPeso(MovimientoInventario paramMovimientoInventario);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario
 * JD-Core Version:    0.7.0.1
 */