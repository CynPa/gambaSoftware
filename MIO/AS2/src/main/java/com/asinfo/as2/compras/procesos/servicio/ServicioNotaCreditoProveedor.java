package com.asinfo.as2.compras.procesos.servicio;

import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
import com.asinfo.as2.entities.AnticipoProveedor;
import com.asinfo.as2.entities.DetalleFacturaProveedor;
import com.asinfo.as2.entities.FacturaProveedor;
import com.asinfo.as2.entities.PuntoDeVenta;
import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioNotaCreditoProveedor
{
  public abstract void guardar(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2;
  
  public abstract FacturaProveedor buscarPorId(Integer paramInteger)
    throws ExcepcionAS2;
  
  public abstract void totalizar(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2Compras;
  
  public abstract void totalizarImpuesto(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2Compras;
  
  public abstract List<FacturaProveedor> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void anular(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2Compras, ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract AnticipoProveedor generarAnticipo(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2;
  
  public abstract void actualizarAtributoEntidad(FacturaProveedor paramFacturaProveedor, HashMap<String, Object> paramHashMap);
  
  public abstract void esEditable(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2Compras, ExcepcionAS2Financiero;
  
  public abstract void actualizarEstado(Integer paramInteger, Estado paramEstado);
  
  public abstract void cargarSecuencia(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2;
  
  public abstract void actulizarDetalleDevolucion(int paramInt, FacturaProveedor paramFacturaProveedor, boolean paramBoolean);
  
  public abstract FacturaProveedor cargarDetalleFactura(FacturaProveedor paramFacturaProveedor1, FacturaProveedor paramFacturaProveedor2)
    throws ExcepcionAS2Compras, ExcepcionAS2;
  
  public abstract FacturaProveedor cargarSecuencia(FacturaProveedor paramFacturaProveedor, PuntoDeVenta paramPuntoDeVenta)
    throws ExcepcionAS2;
  
  public abstract FacturaProveedor actualizarAutorizacionSRI(FacturaProveedor paramFacturaProveedor, PuntoDeVenta paramPuntoDeVenta)
    throws ExcepcionAS2;
  
  public abstract List<Object[]> getReporteNotaCreditoProveedor(int paramInt);
  
  public abstract List<DetalleFacturaProveedor> getDetallesDevolucionesProveedor(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.servicio.ServicioNotaCreditoProveedor
 * JD-Core Version:    0.7.0.1
 */