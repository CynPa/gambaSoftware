package com.asinfo.as2.nomina.asistencia.procesos;

import com.asinfo.as2.entities.nomina.asistencia.PlanPersonalizadoHorarioEmpleado;
import com.asinfo.as2.excepciones.AS2Exception;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioAsistenciaProcesos
{
  public abstract PlanPersonalizadoHorarioEmpleado cargarDetallePlanPersonalizadoHorarioEmpleado(PlanPersonalizadoHorarioEmpleado paramPlanPersonalizadoHorarioEmpleado);
  
  public abstract List<PlanPersonalizadoHorarioEmpleado> obtenerListaPorPaginaPlanPersonalizadoHorarioEmpleado(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterioPlanPersonalizadoHorarioEmpleado(Map<String, String> paramMap);
  
  public abstract void guardarPlanPersonalizadoHorarioEmpleado(PlanPersonalizadoHorarioEmpleado paramPlanPersonalizadoHorarioEmpleado)
    throws AS2Exception;
  
  public abstract void copiarPlanPersonalizadoHorarioEmpleado(PlanPersonalizadoHorarioEmpleado paramPlanPersonalizadoHorarioEmpleado)
    throws AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.asistencia.procesos.ServicioAsistenciaProcesos
 * JD-Core Version:    0.7.0.1
 */