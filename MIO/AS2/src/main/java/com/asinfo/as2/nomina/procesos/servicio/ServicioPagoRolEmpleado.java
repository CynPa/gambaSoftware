package com.asinfo.as2.nomina.procesos.servicio;

import com.asinfo.as2.entities.Empleado;
import com.asinfo.as2.entities.HistoricoEmpleado;
import com.asinfo.as2.entities.PagoEmpleado;
import com.asinfo.as2.entities.PagoRol;
import com.asinfo.as2.entities.PagoRolEmpleado;
import com.asinfo.as2.entities.PagoRolEmpleadoRubro;
import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPagoRolEmpleado
{
  public abstract void guardar(PagoRolEmpleado paramPagoRolEmpleado);
  
  public abstract void eliminar(PagoRolEmpleado paramPagoRolEmpleado);
  
  public abstract PagoRolEmpleado buscarPorId(int paramInt);
  
  public abstract List<PagoRolEmpleado> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<PagoRolEmpleado> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract PagoRolEmpleado cargarDetalle(int paramInt);
  
  public abstract List<PagoRolEmpleadoRubro> cargarRubrosVariablesPorEmpleado(int paramInt);
  
  public abstract BigDecimal obtenerSueldoPorEmpleado(PagoRolEmpleadoRubro paramPagoRolEmpleadoRubro);
  
  public abstract List<PagoRolEmpleadoRubro> cargarRubrosPorEmpleado(PagoRol paramPagoRol);
  
  public abstract BigDecimal calcularDecimoTercero(PagoRolEmpleadoRubro paramPagoRolEmpleadoRubro)
    throws ExcepcionAS2Nomina;
  
  public abstract BigDecimal obtenerAportePersonal(PagoRolEmpleadoRubro paramPagoRolEmpleadoRubro);
  
  public abstract BigDecimal obtenerAportePatronal(PagoRolEmpleadoRubro paramPagoRolEmpleadoRubro);
  
  public abstract BigDecimal calcularDecimoCuarto(PagoRolEmpleadoRubro paramPagoRolEmpleadoRubro)
    throws ExcepcionAS2Nomina;
  
  public abstract BigDecimal calcularProvisionDecimoCuarto(PagoRolEmpleadoRubro paramPagoRolEmpleadoRubro)
    throws ExcepcionAS2Nomina;
  
  public abstract BigDecimal calcularProvisionDecimoTercero(PagoRolEmpleadoRubro paramPagoRolEmpleadoRubro);
  
  public abstract BigDecimal calcularImpuestoRenta(PagoRolEmpleadoRubro paramPagoRolEmpleadoRubro)
    throws ExcepcionAS2Nomina;
  
  public abstract BigDecimal descuentoPrestamo(PagoRolEmpleadoRubro paramPagoRolEmpleadoRubro);
  
  public abstract BigDecimal calcularFondoReserva(PagoRolEmpleadoRubro paramPagoRolEmpleadoRubro)
    throws ExcepcionAS2Nomina;
  
  public abstract BigDecimal subsidio(PagoRolEmpleadoRubro paramPagoRolEmpleadoRubro);
  
  public abstract List<PagoRolEmpleadoRubro> obtenerListaPorEmpleadoRubro(Empleado paramEmpleado, int paramInt);
  
  public abstract List<PagoRolEmpleadoRubro> obtenerListaPorPagoRolRubro(int paramInt1, int paramInt2);
  
  public abstract BigDecimal obtenerValorRubroPadre(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract void actualizarPagoEmpleado(PagoRolEmpleado paramPagoRolEmpleado, PagoEmpleado paramPagoEmpleado, boolean paramBoolean);
  
  public abstract void actualizarReferenciaPagoRolEmpleado(PagoRolEmpleado paramPagoRolEmpleado);
  
  public abstract BigDecimal impuestoALaRentaCausado(int paramInt1, int paramInt2, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, BigDecimal paramBigDecimal3)
    throws ExcepcionAS2Nomina;
  
  public abstract BigDecimal obtenerRubroPorEmpleado(PagoRolEmpleadoRubro paramPagoRolEmpleadoRubro, int paramInt);
  
  public abstract BigDecimal obtenerAportePersonalProyectado(HistoricoEmpleado paramHistoricoEmpleado);
  
  public abstract BigDecimal obtenerIngresosAportables(Date paramDate1, Date paramDate2, int paramInt);
  
  public abstract Empleado obtenerEmpleado(int paramInt);
  
  public abstract BigDecimal obtenerMesesValidos(Date paramDate1, Date paramDate2, int paramInt);
  
  public abstract BigDecimal obtenerMesesValidos(Date paramDate1, Date paramDate2);
  
  public abstract BigDecimal calcularValoresAportables(PagoRolEmpleadoRubro paramPagoRolEmpleadoRubro);
  
  public abstract List getSaldoPrestamosEmpleado(int paramInt, Empleado paramEmpleado);
  
  public abstract BigDecimal getSaldoTotalPrestamosEmpleado(int paramInt, Empleado paramEmpleado);
  
  public abstract List<PagoRolEmpleado> getPagoRolEmpleado(PagoRol paramPagoRol);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRolEmpleado
 * JD-Core Version:    0.7.0.1
 */