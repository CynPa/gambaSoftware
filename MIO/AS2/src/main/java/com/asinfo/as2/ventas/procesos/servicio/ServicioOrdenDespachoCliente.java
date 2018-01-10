package com.asinfo.as2.ventas.procesos.servicio;

import com.asinfo.as2.entities.DespachoCliente;
import com.asinfo.as2.entities.DetalleOrdenDespachoCliente;
import com.asinfo.as2.entities.DetallePedidoCliente;
import com.asinfo.as2.entities.FacturaCliente;
import com.asinfo.as2.entities.MovimientoInventario;
import com.asinfo.as2.entities.OrdenDespachoCliente;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioOrdenDespachoCliente
{
  public abstract void guardar(OrdenDespachoCliente paramOrdenDespachoCliente)
    throws AS2Exception;
  
  public abstract void eliminar(OrdenDespachoCliente paramOrdenDespachoCliente)
    throws AS2Exception;
  
  public abstract OrdenDespachoCliente buscarPorId(Integer paramInteger);
  
  public abstract OrdenDespachoCliente cargarDetalle(Integer paramInteger);
  
  public abstract List<OrdenDespachoCliente> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<OrdenDespachoCliente> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<DetallePedidoCliente> actualizarDetallesPedidoCliente(OrdenDespachoCliente paramOrdenDespachoCliente);
  
  public abstract void actualizarReparticionPedidos(OrdenDespachoCliente paramOrdenDespachoCliente)
    throws AS2Exception;
  
  public abstract List<DespachoCliente> generarDespachos(OrdenDespachoCliente paramOrdenDespachoCliente)
    throws AS2Exception, ExcepcionAS2Inventario, ExcepcionAS2;
  
  public abstract MovimientoInventario guardarDespachosCliente(List<DespachoCliente> paramList, OrdenDespachoCliente paramOrdenDespachoCliente)
    throws ExcepcionAS2Inventario, AS2Exception, ExcepcionAS2;
  
  public abstract void validarOrdenDespachoAntesgenerarDespacho(OrdenDespachoCliente paramOrdenDespachoCliente)
    throws AS2Exception;
  
  public abstract void actualizarPedidosClientePorDetalle(OrdenDespachoCliente paramOrdenDespachoCliente)
    throws AS2Exception;
  
  public abstract MovimientoInventario corteProduccionRapida(OrdenDespachoCliente paramOrdenDespachoCliente)
    throws AS2Exception, ExcepcionAS2Inventario, ExcepcionAS2;
  
  public abstract void validarAgregarPeso(DetalleOrdenDespachoCliente paramDetalleOrdenDespachoCliente)
    throws AS2Exception;
  
  public abstract void cerrar(OrdenDespachoCliente paramOrdenDespachoCliente)
    throws ExcepcionAS2Inventario;
  
  public abstract List<Object[]> getReporteOrdenDespachoGavetas(OrdenDespachoCliente paramOrdenDespachoCliente, boolean paramBoolean);
  
  public abstract MovimientoInventario generarExplosionRapida(FacturaCliente paramFacturaCliente)
    throws AS2Exception, ExcepcionAS2Inventario, ExcepcionAS2;
  
  public abstract void actualizarReparticionPedidos(DetalleOrdenDespachoCliente paramDetalleOrdenDespachoCliente)
    throws AS2Exception;
  
  public abstract DetalleOrdenDespachoCliente buscarPorId(DetalleOrdenDespachoCliente paramDetalleOrdenDespachoCliente);
  
  public abstract void validarProducotListaMaterial(OrdenDespachoCliente paramOrdenDespachoCliente)
    throws AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.ServicioOrdenDespachoCliente
 * JD-Core Version:    0.7.0.1
 */