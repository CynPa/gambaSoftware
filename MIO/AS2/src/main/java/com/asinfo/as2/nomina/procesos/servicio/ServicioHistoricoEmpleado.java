package com.asinfo.as2.nomina.procesos.servicio;

import com.asinfo.as2.entities.Empleado;
import com.asinfo.as2.entities.HistoricoEmpleado;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioHistoricoEmpleado
{
  public abstract void guardar(HistoricoEmpleado paramHistoricoEmpleado)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void eliminar(HistoricoEmpleado paramHistoricoEmpleado);
  
  public abstract HistoricoEmpleado buscarPorId(int paramInt);
  
  public abstract List<HistoricoEmpleado> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<HistoricoEmpleado> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract HistoricoEmpleado obtenerPeriodoActivo(int paramInt, Date paramDate1, Date paramDate2)
    throws ExcepcionAS2Nomina;
  
  public abstract HistoricoEmpleado cargarDetalle(int paramInt);
  
  public abstract HistoricoEmpleado cargarDetalle(int paramInt, boolean paramBoolean);
  
  public abstract void actualizarAtributoEntidad(HistoricoEmpleado paramHistoricoEmpleado, HashMap<String, Object> paramHashMap);
  
  public abstract HistoricoEmpleado obtenerPeriodoActivo(int paramInt, Date paramDate)
    throws ExcepcionAS2Nomina;
  
  public abstract List<HistoricoEmpleado> autocompletarHistoricoEmpleado(String paramString, Map<String, String> paramMap);
  
  public abstract void validar(HistoricoEmpleado paramHistoricoEmpleado)
    throws ExcepcionAS2Nomina, AS2Exception;
  
  public abstract void cargarSecuenciaFiniquito(HistoricoEmpleado paramHistoricoEmpleado)
    throws ExcepcionAS2;
  
  public abstract int obtenerAnioAntiguedad(Empleado paramEmpleado, Date paramDate);
  
  public abstract Date getMaximaFechaDetalleHistoricoEmpleado(int paramInt);
  
  public abstract List<HistoricoEmpleado> verificacion(int paramInt, Empleado paramEmpleado);
  
  public abstract Date ultimaFechaSalida(int paramInt, HistoricoEmpleado paramHistoricoEmpleado);
  
  public abstract List<HistoricoEmpleado> historicosParaUtilidad(int paramInt, Date paramDate1, Date paramDate2);
  
  public abstract void eliminarSalidaEmpleado(HistoricoEmpleado paramHistoricoEmpleado);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.ServicioHistoricoEmpleado
 * JD-Core Version:    0.7.0.1
 */