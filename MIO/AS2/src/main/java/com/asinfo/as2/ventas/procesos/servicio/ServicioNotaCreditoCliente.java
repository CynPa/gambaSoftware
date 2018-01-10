package com.asinfo.as2.ventas.procesos.servicio;

import com.asinfo.as2.entities.AnticipoCliente;
import com.asinfo.as2.entities.DetalleFacturaCliente;
import com.asinfo.as2.entities.FacturaCliente;
import com.asinfo.as2.entities.MotivoAnulacion;
import com.asinfo.as2.entities.MovimientoInventario;
import com.asinfo.as2.entities.NotaFacturaCliente;
import com.asinfo.as2.entities.PreDevolucionCliente;
import com.asinfo.as2.entities.PuntoDeVenta;
import com.asinfo.as2.entities.RecepcionDevolucionTransportista;
import com.asinfo.as2.enumeraciones.DocumentoBase;
import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.enumeraciones.TipoOrganizacion;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.as2.seguridad.modelo.Usuario;
import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioNotaCreditoCliente
{
  public abstract void guardar(FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2Ventas, ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract List<FacturaCliente> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void actualizarEstado(int paramInt, Estado paramEstado, MotivoAnulacion paramMotivoAnulacion);
  
  public abstract void esEditable(FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2Ventas, ExcepcionAS2Financiero;
  
  public abstract void anular(FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2Ventas, ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract AnticipoCliente generarAnticipo(FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract FacturaCliente cargarSecuencia(FacturaCliente paramFacturaCliente, PuntoDeVenta paramPuntoDeVenta)
    throws ExcepcionAS2;
  
  public abstract FacturaCliente totalizar(FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2Ventas;
  
  public abstract void actualizarDetalleDevolucion(int paramInt, FacturaCliente paramFacturaCliente);
  
  public abstract FacturaCliente cargarDetalleFactura(FacturaCliente paramFacturaCliente1, FacturaCliente paramFacturaCliente2)
    throws ExcepcionAS2Ventas, ExcepcionAS2;
  
  public abstract List getReporteNotaCreditoCliente(int paramInt, TipoOrganizacion paramTipoOrganizacion, DocumentoBase paramDocumentoBase, boolean paramBoolean);
  
  public abstract List getReporteNotaCreditoCliente(int paramInt1, TipoOrganizacion paramTipoOrganizacion, DocumentoBase paramDocumentoBase, boolean paramBoolean1, int paramInt2, boolean paramBoolean2);
  
  public abstract void procesarPreDevolucion(PreDevolucionCliente paramPreDevolucionCliente, boolean paramBoolean, PuntoDeVenta paramPuntoDeVenta)
    throws ExcepcionAS2Financiero, ExcepcionAS2Ventas, AS2Exception, ExcepcionAS2;
  
  public abstract List<MovimientoInventario> costearTransformacionDevolucionCliente(FacturaCliente paramFacturaCliente);
  
  public abstract List<MovimientoInventario> contabilizarTransformacionDevolucionCliente(FacturaCliente paramFacturaCliente)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract void actualizarAtributoEntidad(FacturaCliente paramFacturaCliente, HashMap<String, Object> paramHashMap);
  
  public abstract void guardar(FacturaCliente paramFacturaCliente, boolean paramBoolean)
    throws ExcepcionAS2Ventas, ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract void rechazar(FacturaCliente paramFacturaCliente, Usuario paramUsuario, NotaFacturaCliente paramNotaFacturaCliente)
    throws AS2Exception, ExcepcionAS2;
  
  public abstract void aprobar(FacturaCliente paramFacturaCliente, Usuario paramUsuario, NotaFacturaCliente paramNotaFacturaCliente)
    throws ExcepcionAS2Ventas, ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract void procesarPreDevoluciones(RecepcionDevolucionTransportista paramRecepcionDevolucionTransportista, boolean paramBoolean, PuntoDeVenta paramPuntoDeVenta)
    throws ExcepcionAS2Financiero, ExcepcionAS2Ventas, ExcepcionAS2, AS2Exception;
  
  public abstract void actualizarDetalleDevolucionDesdeDespacho(int paramInt, FacturaCliente paramFacturaCliente);
  
  public abstract List<DetalleFacturaCliente> obtenerDetalleFacturaClientePorDevolver(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.ServicioNotaCreditoCliente
 * JD-Core Version:    0.7.0.1
 */