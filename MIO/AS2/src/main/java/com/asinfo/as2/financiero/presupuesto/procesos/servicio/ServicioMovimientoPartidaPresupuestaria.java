package com.asinfo.as2.financiero.presupuesto.procesos.servicio;

import com.asinfo.as2.entities.Documento;
import com.asinfo.as2.entities.presupuesto.MovimientoPartidaPresupuestaria;
import com.asinfo.as2.entities.presupuesto.Presupuesto;
import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioMovimientoPartidaPresupuestaria
{
  public abstract void guardar(MovimientoPartidaPresupuestaria paramMovimientoPartidaPresupuestaria, Presupuesto paramPresupuesto)
    throws AS2Exception, ExcepcionAS2;
  
  public abstract void eliminar(MovimientoPartidaPresupuestaria paramMovimientoPartidaPresupuestaria)
    throws AS2Exception;
  
  public abstract MovimientoPartidaPresupuestaria cargarDetalle(int paramInt);
  
  public abstract MovimientoPartidaPresupuestaria buscarPorId(Integer paramInteger);
  
  public abstract List<MovimientoPartidaPresupuestaria> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract void flush();
  
  public abstract List<MovimientoPartidaPresupuestaria> cargarMovimientosPorAprobar(int paramInt, Documento paramDocumento, Date paramDate1, Date paramDate2);
  
  public abstract void procesarMovimientos(List<MovimientoPartidaPresupuestaria> paramList, Estado paramEstado)
    throws AS2Exception, ExcepcionAS2;
  
  public abstract void actualizarSaldos(MovimientoPartidaPresupuestaria paramMovimientoPartidaPresupuestaria, Presupuesto paramPresupuesto);
  
  public abstract List<Object[]> getReporteMovimientoPartidaPresupuestaria(int paramInt1, int paramInt2);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.presupuesto.procesos.servicio.ServicioMovimientoPartidaPresupuestaria
 * JD-Core Version:    0.7.0.1
 */