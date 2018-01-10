package com.asinfo.as2.nomina.reportes;

import com.asinfo.as2.entities.Banco;
import com.asinfo.as2.entities.CategoriaEmpresa;
import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
import com.asinfo.as2.entities.Departamento;
import com.asinfo.as2.entities.DetallePagoCash;
import com.asinfo.as2.entities.DimensionContable;
import com.asinfo.as2.entities.Empleado;
import com.asinfo.as2.entities.FormaPago;
import com.asinfo.as2.entities.HistoricoEmpleado;
import com.asinfo.as2.entities.Organizacion;
import com.asinfo.as2.entities.PagoCash;
import com.asinfo.as2.entities.PagoRol;
import com.asinfo.as2.entities.PagoRolEmpleadoRubro;
import com.asinfo.as2.entities.Rubro;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.entities.TipoDiscapacidad;
import com.asinfo.as2.entities.TipoPermisoEmpleado;
import com.asinfo.as2.entities.TipoPrestamo;
import com.asinfo.as2.enumeraciones.FormaPagoEmpleado;
import com.asinfo.as2.enumeraciones.TipoServicioCuentaBancariaEnum;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import org.primefaces.model.SortOrder;

@Local
public abstract interface ServicioReporteNomina
{
  public abstract List<PagoRolEmpleadoRubro> obtenerListaPorPaginaPagoRolEmpleadoRubro(int paramInt1, int paramInt2, String paramString, SortOrder paramSortOrder, Map<String, String> paramMap);
  
  public abstract int contarPorCriterioPagoRolEmpleadoRubro(Map<String, String> paramMap);
  
  public abstract List getListaIngresosEgresos(PagoRol paramPagoRol, FormaPagoEmpleado paramFormaPagoEmpleado, Departamento paramDepartamento, Sucursal paramSucursal, int paramInt1, int paramInt2, boolean paramBoolean1, int paramInt3, boolean paramBoolean2, DimensionContable paramDimensionContable);
  
  public abstract List getListaFirmas(PagoRol paramPagoRol, FormaPagoEmpleado paramFormaPagoEmpleado, Sucursal paramSucursal, int paramInt);
  
  public abstract List getListaTransacionBancos(PagoRol paramPagoRol, FormaPagoEmpleado paramFormaPagoEmpleado, Sucursal paramSucursal, int paramInt, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract List<Object[]> getSobreEmpleado(PagoRol paramPagoRol, Empleado paramEmpleado, FormaPagoEmpleado paramFormaPagoEmpleado, Sucursal paramSucursal, int paramInt, Departamento paramDepartamento, boolean paramBoolean);
  
  public abstract List getVacacion(Departamento paramDepartamento, Empleado paramEmpleado, Sucursal paramSucursal, String paramString, FormaPagoEmpleado paramFormaPagoEmpleado, int paramInt, boolean paramBoolean1, Date paramDate1, Date paramDate2, boolean paramBoolean2);
  
  public abstract String getReporteTipoContratoPorId(int paramInt)
    throws ExcepcionAS2;
  
  public abstract List<DetallePagoCash> getCashManagementEmpleado(PagoCash paramPagoCash, CuentaBancariaOrganizacion paramCuentaBancariaOrganizacion, FormaPago paramFormaPago, Empleado paramEmpleado, boolean paramBoolean, TipoServicioCuentaBancariaEnum paramTipoServicioCuentaBancariaEnum, Banco paramBanco)
    throws ExcepcionAS2Nomina;
  
  public abstract List getReporteDiscapacidad(Sucursal paramSucursal, TipoDiscapacidad paramTipoDiscapacidad, Departamento paramDepartamento, Organizacion paramOrganizacion);
  
  public abstract List getReporteDiscapacidadCargaEmpleado(Sucursal paramSucursal, TipoDiscapacidad paramTipoDiscapacidad, Departamento paramDepartamento, Organizacion paramOrganizacion);
  
  public abstract List<Object[]> getReporteRubroEmpleado(int paramInt1, int paramInt2, int paramInt3, List<PagoRol> paramList, List<Rubro> paramList1, int paramInt4, boolean paramBoolean1, boolean paramBoolean2, int paramInt5, CategoriaEmpresa paramCategoriaEmpresa);
  
  public abstract List<Object[]> getReporteRubroMensual(int paramInt1, int paramInt2, int paramInt3, List<PagoRol> paramList, List<Rubro> paramList1, int paramInt4, boolean paramBoolean1, boolean paramBoolean2, int paramInt5, CategoriaEmpresa paramCategoriaEmpresa);
  
  public abstract List<Object[]> getReporteRubroMensualDetallado(int paramInt1, int paramInt2, int paramInt3, List<PagoRol> paramList, List<Rubro> paramList1, int paramInt4, boolean paramBoolean1, boolean paramBoolean2, int paramInt5, CategoriaEmpresa paramCategoriaEmpresa);
  
  public abstract List getReporteUtilidadEmpleado(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract List getReportePrestamoEmpleado(int paramInt, TipoPrestamo paramTipoPrestamo, Departamento paramDepartamento, Empleado paramEmpleado, Sucursal paramSucursal, boolean paramBoolean, Date paramDate1, Date paramDate2);
  
  public abstract List<Object[]> getReportePermisos(Empleado paramEmpleado, int paramInt1, Departamento paramDepartamento, Date paramDate1, Date paramDate2, int paramInt2, TipoPermisoEmpleado paramTipoPermisoEmpleado);
  
  public abstract List<Object[]> getReporteRubroAsignado(int paramInt, Empleado paramEmpleado, boolean paramBoolean);
  
  public abstract List<Object[]> getPagoRolEmpleadoRubro(HistoricoEmpleado paramHistoricoEmpleado, Empleado paramEmpleado, int paramInt1, int paramInt2);
  
  public abstract List getReporteDotacionEmpleado(Date paramDate1, Date paramDate2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
  
  public abstract void enviarEmailPagoRolEmpleados(Organizacion paramOrganizacion, String paramString, PagoRol paramPagoRol, Sucursal paramSucursal, Empleado paramEmpleado, FormaPagoEmpleado paramFormaPagoEmpleado, Departamento paramDepartamento)
    throws AS2Exception;
  
  public abstract String getReporteMotivoLlamadoAtencionPorId(int paramInt)
    throws ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.reportes.ServicioReporteNomina
 * JD-Core Version:    0.7.0.1
 */