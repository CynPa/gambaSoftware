package com.asinfo.as2.ventas.procesos.servicio;

import com.asinfo.as2.entities.CuentaPorCobrar;
import com.asinfo.as2.entities.DetalleFacturaCliente;
import com.asinfo.as2.entities.DetallePedidoCliente;
import com.asinfo.as2.entities.FacturaCliente;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.PuntoDeVenta;
import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

@Remote
public abstract interface ServicioFacturaClienteRemote
{
  public abstract FacturaCliente guardar(FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2Ventas, ExcepcionAS2Financiero, ExcepcionAS2Financiero, Exception;
  
  public abstract void eliminar(FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2;
  
  public abstract FacturaCliente buscarPorId(Integer paramInteger)
    throws ExcepcionAS2;
  
  public abstract FacturaCliente cargarDetalle(int paramInt)
    throws ExcepcionAS2Ventas;
  
  public abstract FacturaCliente totalizar(FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2Ventas;
  
  public abstract List<CuentaPorCobrar> obtenerFacturasPendientes(int paramInt);
  
  public abstract List<CuentaPorCobrar> obtenerFacturasPendientes(int paramInt1, int paramInt2);
  
  public abstract FacturaCliente totalizarImpuesto(FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2Ventas;
  
  public abstract FacturaCliente generarCuentaPorCobrar(FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2;
  
  public abstract DetalleFacturaCliente getDetalleFacturaPedidoCliente(DetallePedidoCliente paramDetallePedidoCliente)
    throws ExcepcionAS2Ventas;
  
  public abstract List<FacturaCliente> listaFacturasPorDespachar(Estado paramEstado, int paramInt);
  
  public abstract List<FacturaCliente> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<FacturaCliente> obtenerListaComboConEqual(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract BigDecimal getSumaImpuestoPorIdFacturaCliente(int paramInt);
  
  public abstract List getReporteFacturaCliente(int paramInt)
    throws ExcepcionAS2;
  
  public abstract void anulaFacturaCliente(FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2, ExcepcionAS2Ventas, ExcepcionAS2Financiero;
  
  public abstract void actualizarEstado(int paramInt, Estado paramEstado);
  
  public abstract void esEditable(FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2Ventas, ExcepcionAS2Financiero;
  
  public abstract FacturaCliente cargarSecuencia(FacturaCliente paramFacturaCliente, PuntoDeVenta paramPuntoDeVenta)
    throws ExcepcionAS2;
  
  public abstract void obtenerImpuestosProductos(Producto paramProducto, DetalleFacturaCliente paramDetalleFacturaCliente)
    throws ExcepcionAS2Inventario;
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract long verificaExistenciaNumero(FacturaCliente paramFacturaCliente);
  
  public abstract FacturaCliente buscarPorDespachoCliente(Integer paramInteger);
  
  public abstract FacturaCliente cargarCabecera(Integer paramInteger);
  
  public abstract FacturaCliente buscarFacturaCliente(Map<String, String> paramMap)
    throws ExcepcionAS2;
  
  public abstract List<FacturaCliente> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void verificaNumeroFacturaRangoSecuencia(FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2Financiero;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaClienteRemote
 * JD-Core Version:    0.7.0.1
 */