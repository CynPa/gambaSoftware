package com.asinfo.as2.produccion.procesos.servicio;

import com.asinfo.as2.entities.Bodega;
import com.asinfo.as2.entities.DetalleOrdenSalidaMaterial;
import com.asinfo.as2.entities.produccion.DetallePlanProduccion;
import com.asinfo.as2.entities.produccion.PlanProduccion;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPlanProduccion
{
  public abstract void guardar(PlanProduccion paramPlanProduccion)
    throws AS2Exception;
  
  public abstract void eliminar(PlanProduccion paramPlanProduccion);
  
  public abstract PlanProduccion buscarPorId(int paramInt);
  
  public abstract List<PlanProduccion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<PlanProduccion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract PlanProduccion cargarDetalle(int paramInt);
  
  public abstract void cargarSugerenciasVentas(PlanProduccion paramPlanProduccion, int paramInt, List<Bodega> paramList)
    throws AS2Exception;
  
  public abstract void calcularSaldosSemana(DetallePlanProduccion paramDetallePlanProduccion)
    throws AS2Exception;
  
  public abstract void generarOrdenProduccion(PlanProduccion paramPlanProduccion, Date paramDate)
    throws AS2Exception;
  
  public abstract void proyectarVentas(DetallePlanProduccion paramDetallePlanProduccion)
    throws AS2Exception;
  
  public abstract void actualizarPlanMaestroProduccion(PlanProduccion paramPlanProduccion)
    throws AS2Exception;
  
  public abstract List<DetalleOrdenSalidaMaterial> generarDetallesOrdenSalidaMaterial(PlanProduccion paramPlanProduccion)
    throws AS2Exception;
  
  public abstract boolean generarOrdenSolicitudMaterialConsumibles(List<DetalleOrdenSalidaMaterial> paramList, String paramString)
    throws AS2Exception, ExcepcionAS2Financiero, ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.servicio.ServicioPlanProduccion
 * JD-Core Version:    0.7.0.1
 */