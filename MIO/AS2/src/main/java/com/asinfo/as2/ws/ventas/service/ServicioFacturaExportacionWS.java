package com.asinfo.as2.ws.ventas.service;

import com.asinfo.as2.clases.ReporteCobros;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
import com.asinfo.as2.ws.ventas.model.EstadoCuentaWSEntity;
import com.asinfo.as2.ws.ventas.model.FacturaClienteFlorWSEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public abstract interface ServicioFacturaExportacionWS
{
  @WebMethod
  public abstract FacturaClienteFlorWSEntity guardarFactura(FacturaClienteFlorWSEntity paramFacturaClienteFlorWSEntity)
    throws AS2Exception;
  
  @WebMethod
  public abstract boolean anularFactura(Long paramLong)
    throws AS2Exception;
  
  @WebMethod
  public abstract void esEditable(Long paramLong)
    throws ExcepcionAS2Ventas, ExcepcionAS2Financiero, AS2Exception, ExcepcionAS2;
  
  @WebMethod
  public abstract String cambiarNumeroFactura(Long paramLong, String paramString)
    throws AS2Exception;
  
  @WebMethod
  public abstract boolean liberarFacturaAutomatica(Long paramLong)
    throws AS2Exception;
  
  public abstract void actualizarInformacionFactura(FacturaClienteFlorWSEntity paramFacturaClienteFlorWSEntity)
    throws AS2Exception;
  
  @WebMethod
  public abstract BigDecimal getPorcentajeIVA(int paramInt, Date paramDate);
  
  @WebMethod
  public abstract ArrayList<Object[]> getListaReporteEstadoCuenta(EstadoCuentaWSEntity paramEstadoCuentaWSEntity);
  
  @WebMethod
  public abstract String[] getFieldsReporteEstadoCuenta();
  
  @WebMethod
  public abstract BigDecimal obtenerSaldoAnticipo(int paramInt, Date paramDate);
  
  @WebMethod
  public abstract BigDecimal obtenerSaldoChequePosfechado(int paramInt, Date paramDate);
  
  @WebMethod
  public abstract BigDecimal obtenerSaldoEstadoCuenta(int paramInt, Date paramDate);
  
  @WebMethod
  public abstract String[] getFieldsReporteFacturaCliente();
  
  @WebMethod
  public abstract ArrayList<Object[]> getDatosReporteFactura(Long paramLong, boolean paramBoolean)
    throws ExcepcionAS2;
  
  @WebMethod
  public abstract ArrayList<ReporteCobros> getDatosReporteCobros(Long paramLong1, Date paramDate1, Date paramDate2, Long paramLong2);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.ventas.service.ServicioFacturaExportacionWS
 * JD-Core Version:    0.7.0.1
 */