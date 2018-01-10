package com.asinfo.as2.financiero.cobros.reportes.servicio;

import com.asinfo.as2.clases.ReporteCarteraCobrada;
import com.asinfo.as2.clases.ReporteCobros;
import com.asinfo.as2.entities.Calificacion;
import com.asinfo.as2.entities.CategoriaEmpresa;
import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.PuntoDeVenta;
import com.asinfo.as2.entities.Recaudador;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.entities.Zona;
import com.asinfo.as2.entities.seguridad.EntidadUsuario;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteCobroCliente
{
  public abstract List getReporteCobroCliente(Date paramDate1, Date paramDate2, int paramInt1, Recaudador paramRecaudador, int paramInt2, boolean paramBoolean1, int paramInt3, boolean paramBoolean2, Sucursal paramSucursal, PuntoDeVenta paramPuntoDeVenta, EntidadUsuario paramEntidadUsuario, Zona paramZona)
    throws ExcepcionAS2;
  
  public abstract List getReporteCobro(int paramInt);
  
  public abstract List getReporteCobroClientePorFormaPago(Date paramDate1, Date paramDate2, int paramInt1, Recaudador paramRecaudador, int paramInt2, boolean paramBoolean1, int paramInt3, boolean paramBoolean2, Sucursal paramSucursal, Zona paramZona, PuntoDeVenta paramPuntoDeVenta, EntidadUsuario paramEntidadUsuario);
  
  public abstract List getReporteCobroProtestos(int paramInt1, Date paramDate1, Date paramDate2, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract List getReporteDepositosCobro(int paramInt1, Date paramDate1, Date paramDate2, int paramInt2, boolean paramBoolean, int paramInt3, int paramInt4);
  
  public abstract List getReporteChequesProtestados(Date paramDate1, Date paramDate2, Empresa paramEmpresa, CuentaBancariaOrganizacion paramCuentaBancariaOrganizacion, boolean paramBoolean);
  
  public abstract List<Object[]> facturasCanceladas(Date paramDate1, Date paramDate2, int paramInt, Sucursal paramSucursal, Zona paramZona);
  
  public abstract List<Object[]> getReporteRegistroVoucher(int paramInt1, int paramInt2);
  
  public abstract List<Object[]> getReportePagoVoucher(int paramInt1, int paramInt2);
  
  public abstract List<Object[]> listaReporteTicket(int paramInt, Date paramDate1, Date paramDate2, String paramString1, String paramString2, String paramString3, String paramString4);
  
  public abstract Long countTickets(int paramInt, Date paramDate, String paramString1, String paramString2);
  
  public abstract List<Object[]> getReporteConsultaVoucher(int paramInt, PuntoDeVenta paramPuntoDeVenta, Date paramDate1, Date paramDate2, String paramString);
  
  public abstract ArrayList<ReporteCobros> getListaCobros(Integer paramInteger, Date paramDate1, Date paramDate2, int paramInt);
  
  public abstract List<ReporteCarteraCobrada> getReporteCarteraCobrada(CategoriaEmpresa paramCategoriaEmpresa, EntidadUsuario paramEntidadUsuario, Recaudador paramRecaudador, Empresa paramEmpresa, Date paramDate1, Date paramDate2, boolean paramBoolean1, boolean paramBoolean2, int paramInt);
  
  public abstract List<ReporteCarteraCobrada> getReporteCalificacionCliente(CategoriaEmpresa paramCategoriaEmpresa, EntidadUsuario paramEntidadUsuario, Recaudador paramRecaudador, Empresa paramEmpresa, Calificacion paramCalificacion, Date paramDate1, Date paramDate2, int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.cobros.reportes.servicio.ServicioReporteCobroCliente
 * JD-Core Version:    0.7.0.1
 */