package com.asinfo.as2.produccion.procesos.servicio;

import com.asinfo.as2.entities.produccion.DetallePlanMaestroProduccion;
import com.asinfo.as2.entities.produccion.PlanMaestroProduccion;
import com.asinfo.as2.enumeraciones.Mes;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPlanMaestroProduccion
{
  public abstract void guardar(PlanMaestroProduccion paramPlanMaestroProduccion)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void eliminar(PlanMaestroProduccion paramPlanMaestroProduccion);
  
  public abstract PlanMaestroProduccion buscarPorId(int paramInt);
  
  public abstract List<PlanMaestroProduccion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<PlanMaestroProduccion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract PlanMaestroProduccion cargarDetalle(int paramInt);
  
  public abstract List<PlanMaestroProduccion> buscarPlanesMaestrosProduccionPorRangoFecha(int paramInt, Date paramDate1, Date paramDate2)
    throws ExcepcionAS2;
  
  public abstract void actualizarProductos(PlanMaestroProduccion paramPlanMaestroProduccion);
  
  public abstract Map<Integer, DetallePlanMaestroProduccion> obtenerDetallesPlanMaestroPorFecha(PlanMaestroProduccion paramPlanMaestroProduccion, Mes paramMes);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.servicio.ServicioPlanMaestroProduccion
 * JD-Core Version:    0.7.0.1
 */