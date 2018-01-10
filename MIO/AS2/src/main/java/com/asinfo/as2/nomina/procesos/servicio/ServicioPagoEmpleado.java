package com.asinfo.as2.nomina.procesos.servicio;

import com.asinfo.as2.entities.PagoEmpleado;
import com.asinfo.as2.entities.PagoRolEmpleado;
import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPagoEmpleado
{
  public abstract void guardar(PagoEmpleado paramPagoEmpleado)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void eliminar(PagoEmpleado paramPagoEmpleado);
  
  public abstract PagoEmpleado buscarPorId(int paramInt);
  
  public abstract List<PagoEmpleado> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract PagoEmpleado cargarDetalle(int paramInt);
  
  public abstract List<PagoRolEmpleado> cargarEmpleadosPendientes(int paramInt, boolean paramBoolean);
  
  public abstract void anular(PagoEmpleado paramPagoEmpleado)
    throws ExcepcionAS2Financiero;
  
  public abstract void contabilizar(PagoEmpleado paramPagoEmpleado, Date paramDate)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract void actualizarEstado(int paramInt, Estado paramEstado);
  
  public abstract void actualizarAtributoEntidad(PagoEmpleado paramPagoEmpleado, HashMap<String, Object> paramHashMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.ServicioPagoEmpleado
 * JD-Core Version:    0.7.0.1
 */