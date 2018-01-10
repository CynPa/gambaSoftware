package com.asinfo.as2.nomina.procesos.servicio;

import com.asinfo.as2.clases.AprobacionPagoRol;
import com.asinfo.as2.clases.RelacionDependencia;
import com.asinfo.as2.entities.Departamento;
import com.asinfo.as2.entities.DetalleFiniquitoEmpleado;
import com.asinfo.as2.entities.Empleado;
import com.asinfo.as2.entities.HistoricoEmpleado;
import com.asinfo.as2.entities.PagoCash;
import com.asinfo.as2.entities.PagoRol;
import com.asinfo.as2.entities.PagoRolEmpleado;
import com.asinfo.as2.entities.PagoRolEmpleadoRubro;
import com.asinfo.as2.entities.Quincena;
import com.asinfo.as2.entities.Rubro;
import com.asinfo.as2.entities.RubroEmpleado;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.entities.nomina.asistencia.EmpleadoAsistencia;
import com.asinfo.as2.enumeraciones.TipoRubroEnum;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPagoRol
{
  public abstract void guardar(PagoRol paramPagoRol)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void eliminar(PagoRol paramPagoRol);
  
  public abstract PagoRol buscarPorId(int paramInt);
  
  public abstract List<PagoRol> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<PagoRol> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract PagoRol cargarDetalle(int paramInt);
  
  public abstract List<PagoRolEmpleadoRubro> cargarRubrosVariablesPorEmpleado(int paramInt);
  
  public abstract List<PagoRolEmpleadoRubro> cargarRubrosVariablesPorRubro(int paramInt1, int paramInt2);
  
  public abstract void actualizarPagoRolEmpleado(PagoRol paramPagoRol, int paramInt1, int paramInt2, Integer paramInteger)
    throws ExcepcionAS2Nomina;
  
  public abstract void actualizarPagoRolEmpleadoRubro(PagoRol paramPagoRol, Integer paramInteger);
  
  public abstract void esEditable(PagoRol paramPagoRol)
    throws ExcepcionAS2Financiero;
  
  public abstract List<RelacionDependencia> obtenerDatosRDEP(int paramInt1, int paramInt2, List<Integer> paramList);
  
  public abstract void actualizarPagoCash(int paramInt, PagoCash paramPagoCash, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract boolean validarPagoRolAnterior(Date paramDate, int paramInt);
  
  public abstract void actualizarDiasTrabajados(PagoRol paramPagoRol, Integer paramInteger);
  
  public abstract List<PagoRol> obtenerPagoRol(Date paramDate1, Date paramDate2, int paramInt);
  
  public abstract PagoRol obtenerPagoRolPorDiaMes(int paramInt1, int paramInt2);
  
  public abstract PagoRol procesarPagoRol(PagoRol paramPagoRol, int paramInt1, int paramInt2, Integer paramInteger, boolean paramBoolean)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void cargarPagosRol(String paramString, InputStream paramInputStream, int paramInt)
    throws ExcepcionAS2, ExcepcionAS2Financiero, IOException;
  
  public abstract List<PagoRolEmpleadoRubro> getListaPagoRolEmpleadoRubroFiniquito(HistoricoEmpleado paramHistoricoEmpleado);
  
  public abstract void anular(PagoRol paramPagoRol)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract List<AprobacionPagoRol> getDatosAprobacionPagoRol(int paramInt, Sucursal paramSucursal, PagoRol paramPagoRol, Departamento paramDepartamento);
  
  public abstract void guardar(PagoRol paramPagoRol, List<AprobacionPagoRol> paramList)
    throws ExcepcionAS2;
  
  public abstract List<Object[]> getDatosArchivoVariacionesIESS(PagoRol paramPagoRol, Sucursal paramSucursal);
  
  public abstract List<Object[]> getArchivoDecimocuarto(PagoRol paramPagoRol, Rubro paramRubro, int paramInt);
  
  public abstract List<Object[]> getArchivoUtilidades(int paramInt1, int paramInt2);
  
  public abstract List<Object[]> getValorPagoRubros(int paramInt1, int paramInt2);
  
  public abstract int getRubroID(String paramString);
  
  public abstract List<Rubro> getRubroDecimoTercero(int paramInt);
  
  public abstract List<Rubro> getRubroTipo(TipoRubroEnum paramTipoRubroEnum, int paramInt);
  
  public abstract void actualizarPagoRolDias(PagoRol paramPagoRol, int paramInt1, int paramInt2, Integer paramInteger)
    throws ExcepcionAS2Nomina;
  
  public abstract PagoRol obtenerPagoRolPorQuincenaMesAnio(int paramInt1, Quincena paramQuincena, int paramInt2, int paramInt3);
  
  public abstract void procesarPropina(PagoRol paramPagoRol1, PagoRol paramPagoRol2, BigDecimal paramBigDecimal)
    throws AS2Exception;
  
  public abstract List<RubroEmpleado> listaEmpleadosAsignarRubro(PagoRol paramPagoRol, EmpleadoAsistencia paramEmpleadoAsistencia, PagoRolEmpleado paramPagoRolEmpleado);
  
  public abstract void procesarHorasExtra(PagoRol paramPagoRol, EmpleadoAsistencia paramEmpleadoAsistencia)
    throws AS2Exception;
  
  public abstract List<DetalleFiniquitoEmpleado> obtenerValoresPagadosPorRubrosYFechas(int paramInt, Empleado paramEmpleado, List<Rubro> paramList, Date paramDate1, Date paramDate2);
  
  public abstract List<Object[]> getArchivoDecimotercero(int paramInt, Date paramDate1, Date paramDate2, PagoRol paramPagoRol);
  
  public abstract List<RubroEmpleado> listaEmpleadosRubroFaltas(PagoRol paramPagoRol, EmpleadoAsistencia paramEmpleadoAsistencia, PagoRolEmpleado paramPagoRolEmpleado);
  
  public abstract void generarRubrosNoProcesados(PagoRol paramPagoRol);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRol
 * JD-Core Version:    0.7.0.1
 */