package com.asinfo.as2.financiero.pagos.procesos.servicio;

import com.asinfo.as2.clases.PagoAnticipoCheque;
import com.asinfo.as2.entities.Pago;
import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPago
{
  public abstract void guardar(Pago paramPago)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void eliminar(Pago paramPago);
  
  public abstract Pago buscarPorId(Integer paramInteger);
  
  public abstract void detach(Pago paramPago);
  
  public abstract Pago cargarDetalle(int paramInt);
  
  public abstract boolean validaAsientoPago(Pago paramPago)
    throws ExcepcionAS2, ExcepcionAS2Financiero;
  
  public abstract void anularPago(Pago paramPago)
    throws ExcepcionAS2, ExcepcionAS2Financiero;
  
  public abstract List<Pago> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void contabilizar(Pago paramPago)
    throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception;
  
  public abstract void cargarFacturasPendientes(Pago paramPago, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract void cargarFacturasPendientes(Pago paramPago, int paramInt, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract void actualizarEstado(int paramInt, Estado paramEstado);
  
  public abstract void esEditable(Pago paramPago)
    throws ExcepcionAS2Financiero;
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract void actualizaIndicadorBloqueadoPago(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
    throws ExcepcionAS2Financiero;
  
  public abstract void esEditable(Pago paramPago, boolean paramBoolean)
    throws ExcepcionAS2Financiero;
  
  public abstract void actualizarChequeEntregadoPago(int paramInt, boolean paramBoolean, String paramString, Date paramDate);
  
  public abstract List<PagoAnticipoCheque> getPagoConCheques();
  
  public abstract void cargarPagos(String paramString, InputStream paramInputStream, int paramInt1, int paramInt2)
    throws ExcepcionAS2, ExcepcionAS2Financiero;
  
  public abstract void actualizarDescripcionPago(Pago paramPago);
  
  public abstract void actualizarIndicadorBloqueadoPagoPorFactura(int paramInt, boolean paramBoolean);
  
  public abstract List<Pago> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void anularPago(Pago paramPago, boolean paramBoolean)
    throws ExcepcionAS2, ExcepcionAS2Financiero;
  
  public abstract void guardar(Pago paramPago, String paramString)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void actualizarAtributoEntidad(Pago paramPago, HashMap<String, Object> paramHashMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioPago
 * JD-Core Version:    0.7.0.1
 */