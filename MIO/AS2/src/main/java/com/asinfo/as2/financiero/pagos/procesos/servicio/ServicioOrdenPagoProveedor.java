package com.asinfo.as2.financiero.pagos.procesos.servicio;

import com.asinfo.as2.entities.AnticipoProveedor;
import com.asinfo.as2.entities.CuentaPorPagar;
import com.asinfo.as2.entities.DetalleFormaPago;
import com.asinfo.as2.entities.DetalleOrdenPagoProveedor;
import com.asinfo.as2.entities.OrdenPagoProveedor;
import com.asinfo.as2.entities.Pago;
import com.asinfo.as2.entities.PagoCash;
import com.asinfo.as2.entities.Proveedor;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioOrdenPagoProveedor
{
  public abstract void guardar(OrdenPagoProveedor paramOrdenPagoProveedor)
    throws AS2Exception;
  
  public abstract void eliminar(OrdenPagoProveedor paramOrdenPagoProveedor)
    throws AS2Exception;
  
  public abstract OrdenPagoProveedor buscarPorId(Integer paramInteger);
  
  public abstract void detach(OrdenPagoProveedor paramOrdenPagoProveedor);
  
  public abstract OrdenPagoProveedor cargarDetalle(int paramInt);
  
  public abstract List<OrdenPagoProveedor> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract void cargarFacturasPendientes(OrdenPagoProveedor paramOrdenPagoProveedor);
  
  public abstract List<Object[]> getReporteOrdenPagoProveedor(OrdenPagoProveedor paramOrdenPagoProveedor);
  
  public abstract List<DetalleOrdenPagoProveedor> buscarDetallesPendientesPago(int paramInt, Boolean paramBoolean, Date paramDate);
  
  public abstract void generarPagos(Pago paramPago, DetalleFormaPago paramDetalleFormaPago, List<Proveedor> paramList, String paramString)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void reversarOrdenAlAnularPago(Pago paramPago);
  
  public abstract void reversarOrdenAlAnularPagoCash(PagoCash paramPagoCash);
  
  public abstract void reversarOrdenAlAnularAnticipo(AnticipoProveedor paramAnticipoProveedor);
  
  public abstract void isEditable(OrdenPagoProveedor paramOrdenPagoProveedor)
    throws AS2Exception;
  
  public abstract void cerrar(OrdenPagoProveedor paramOrdenPagoProveedor, String paramString);
  
  public abstract void anular(OrdenPagoProveedor paramOrdenPagoProveedor)
    throws AS2Exception;
  
  public abstract void revisarOrdenPagoProveedor(OrdenPagoProveedor paramOrdenPagoProveedor, String paramString);
  
  public abstract List<Integer> buscarOrdenPagoProveedorPorNumeroDeFactura(int paramInt, String paramString);
  
  public abstract void aprobarOrdenPagoProveedor(OrdenPagoProveedor paramOrdenPagoProveedor);
  
  public abstract void liberarCuentasPorPagar(Map<Integer, CuentaPorPagar> paramMap);
  
  public abstract void actualizarAtributoEntidad(OrdenPagoProveedor paramOrdenPagoProveedor, HashMap<String, Object> paramHashMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioOrdenPagoProveedor
 * JD-Core Version:    0.7.0.1
 */