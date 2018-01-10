package com.asinfo.as2.financiero.presupuesto.procesos.servicio;

import com.asinfo.as2.entities.presupuesto.Presupuesto;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPresupuesto
{
  public abstract void guardar(Presupuesto paramPresupuesto)
    throws ExcepcionAS2Inventario, ExcepcionAS2Financiero;
  
  public abstract void eliminar(Presupuesto paramPresupuesto);
  
  public abstract Presupuesto buscarPorId(int paramInt);
  
  public abstract List<Presupuesto> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Presupuesto> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Presupuesto cargarDetalle(int paramInt);
  
  public abstract void validaPresupuesto(Presupuesto paramPresupuesto)
    throws ExcepcionAS2Inventario, ExcepcionAS2Financiero;
  
  public abstract Presupuesto copiarPresupuesto(Presupuesto paramPresupuesto);
  
  public abstract List<Object[]> getReportePresupuesto(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract Presupuesto buscarPresupuestoPorEjercicio(int paramInt1, int paramInt2);
  
  public abstract Presupuesto buscarPorFecha(Date paramDate, int paramInt)
    throws ExcepcionAS2Financiero;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.presupuesto.procesos.servicio.ServicioPresupuesto
 * JD-Core Version:    0.7.0.1
 */