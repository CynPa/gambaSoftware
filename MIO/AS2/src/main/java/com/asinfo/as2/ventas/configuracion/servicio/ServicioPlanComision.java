package com.asinfo.as2.ventas.configuracion.servicio;

import com.asinfo.as2.entities.DetalleVersionPlanComision;
import com.asinfo.as2.entities.DetalleVersionPlanComisionRangoDias;
import com.asinfo.as2.entities.PlanComision;
import com.asinfo.as2.entities.RangoDiasComision;
import com.asinfo.as2.entities.VersionPlanComision;
import com.asinfo.as2.excepciones.AS2Exception;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPlanComision
{
  public abstract void guardar(PlanComision paramPlanComision)
    throws AS2Exception;
  
  public abstract void eliminar(PlanComision paramPlanComision);
  
  public abstract PlanComision buscarPorId(int paramInt);
  
  public abstract List<PlanComision> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<PlanComision> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract PlanComision cargarDetalle(int paramInt);
  
  public abstract List<VersionPlanComision> getReportePlanComision(PlanComision paramPlanComision);
  
  public abstract void actualizarRangoDiasPorDetalleVersion(DetalleVersionPlanComision paramDetalleVersionPlanComision, List<RangoDiasComision> paramList);
  
  public abstract DetalleVersionPlanComisionRangoDias obtenerValorDetalleVersionPlanComision(DetalleVersionPlanComision paramDetalleVersionPlanComision, RangoDiasComision paramRangoDiasComision);
  
  public abstract PlanComision copiarPlanComision(PlanComision paramPlanComision);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.servicio.ServicioPlanComision
 * JD-Core Version:    0.7.0.1
 */