package com.asinfo.as2.nomina.procesos.servicio;

import com.asinfo.as2.entities.Empleado;
import com.asinfo.as2.entities.PagoRol;
import com.asinfo.as2.entities.PagoRolEmpleado;
import com.asinfo.as2.entities.PagoRolEmpleadoRubro;
import com.asinfo.as2.entities.Rubro;
import com.asinfo.as2.entities.RubroEmpleado;
import com.asinfo.as2.enumeraciones.TipoRubroEnum;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioRubroEmpleado
{
  public abstract void guardar(RubroEmpleado paramRubroEmpleado);
  
  public abstract void eliminar(RubroEmpleado paramRubroEmpleado);
  
  public abstract RubroEmpleado buscarPorId(int paramInt);
  
  public abstract List<RubroEmpleado> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<RubroEmpleado> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract RubroEmpleado cargarDetalle(int paramInt);
  
  public abstract List<RubroEmpleado> obtenerRubrosPorEmpleado(int paramInt);
  
  public abstract List<RubroEmpleado> getListaRubroEmpleado(int paramInt1, int paramInt2);
  
  public abstract BigDecimal obtenerSueldoPorEmpleado(PagoRolEmpleadoRubro paramPagoRolEmpleadoRubro);
  
  public abstract Rubro rubroEmpleadoRetencionJudicial(Empleado paramEmpleado, TipoRubroEnum paramTipoRubroEnum);
  
  public abstract List<RubroEmpleado> getListaRubroEmpleadoFiniquito(PagoRolEmpleado paramPagoRolEmpleado, int paramInt1, int paramInt2);
  
  public abstract void guardarEmpleadoAsignacionRubro(List<Empleado> paramList, PagoRol paramPagoRol);
  
  public abstract void guardarListaRubroEmpleadoAsignarPagoRol(List<RubroEmpleado> paramList, PagoRol paramPagoRol);
  
  public abstract List<RubroEmpleado> getGenerarRubroEmpleado(List<Rubro> paramList, int paramInt)
    throws ExcepcionAS2Financiero;
  
  public abstract List<Empleado> getEmpleadoSinRubro(List<Rubro> paramList, int paramInt)
    throws ExcepcionAS2Financiero;
  
  public abstract List<RubroEmpleado> obtenerRubroPorFormula(int paramInt, Empleado paramEmpleado, String paramString);
  
  public abstract BigDecimal obtenerSueldoPorEmpleado(Empleado paramEmpleado);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.ServicioRubroEmpleado
 * JD-Core Version:    0.7.0.1
 */