package com.asinfo.as2.financiero.pagos.procesos.servicio;

import com.asinfo.as2.clases.PagoAnticipoCheque;
import com.asinfo.as2.entities.AnticipoProveedor;
import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioAnticipoProveedor
{
  public abstract void guardar(AnticipoProveedor paramAnticipoProveedor)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void eliminar(AnticipoProveedor paramAnticipoProveedor);
  
  public abstract AnticipoProveedor buscarPorId(Integer paramInteger);
  
  public abstract AnticipoProveedor cargarDetalle(int paramInt);
  
  public abstract boolean validaAsientoAnticipoProveedor(AnticipoProveedor paramAnticipoProveedor)
    throws ExcepcionAS2, ExcepcionAS2Financiero;
  
  public abstract void anularAnticipoProveedor(AnticipoProveedor paramAnticipoProveedor)
    throws ExcepcionAS2, ExcepcionAS2Financiero;
  
  public abstract List<AnticipoProveedor> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void contabilizar(AnticipoProveedor paramAnticipoProveedor)
    throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception;
  
  public abstract BigDecimal obtenerSaldoAnticipo(int paramInt, Date paramDate);
  
  public abstract void esEditable(AnticipoProveedor paramAnticipoProveedor)
    throws ExcepcionAS2Financiero;
  
  public abstract void actualizarEstado(Integer paramInteger, Estado paramEstado);
  
  public abstract void cargarSecuencia(AnticipoProveedor paramAnticipoProveedor)
    throws ExcepcionAS2;
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract void guardarDevolucion(AnticipoProveedor paramAnticipoProveedor)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract BigDecimal obtenerSaldoProveedor(int paramInt);
  
  public abstract void esEditable(AnticipoProveedor paramAnticipoProveedor, boolean paramBoolean)
    throws ExcepcionAS2Financiero;
  
  public abstract void validaFechaDevolucionAnticipo(Date paramDate1, Date paramDate2)
    throws ExcepcionAS2Financiero;
  
  public abstract void actualizarChequeEntregadoPago(int paramInt, boolean paramBoolean, String paramString, Date paramDate);
  
  public abstract List<PagoAnticipoCheque> getPagoConCheques();
  
  public abstract void detach(AnticipoProveedor paramAnticipoProveedor);
  
  public abstract BigDecimal getSaldoAnticipoPreveedor(AnticipoProveedor paramAnticipoProveedor);
  
  public abstract void contabilizarDevolucion(AnticipoProveedor paramAnticipoProveedor)
    throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception;
  
  public abstract List<AnticipoProveedor> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void anularAnticipoProveedor(AnticipoProveedor paramAnticipoProveedor, boolean paramBoolean)
    throws ExcepcionAS2, ExcepcionAS2Financiero;
  
  public abstract void anularDevolverAnticipoProveedor(AnticipoProveedor paramAnticipoProveedor)
    throws ExcepcionAS2, ExcepcionAS2Financiero;
  
  public abstract void actualizarAtributoEntidad(AnticipoProveedor paramAnticipoProveedor, HashMap<String, Object> paramHashMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioAnticipoProveedor
 * JD-Core Version:    0.7.0.1
 */