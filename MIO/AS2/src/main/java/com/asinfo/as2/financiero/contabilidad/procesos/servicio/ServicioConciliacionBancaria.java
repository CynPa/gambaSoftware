package com.asinfo.as2.financiero.contabilidad.procesos.servicio;

import com.asinfo.as2.clases.DetalleInterfazContable;
import com.asinfo.as2.entities.ConciliacionBancaria;
import com.asinfo.as2.entities.MovimientoBancario;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioConciliacionBancaria
{
  public abstract void guardar(ConciliacionBancaria paramConciliacionBancaria)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void eliminar(ConciliacionBancaria paramConciliacionBancaria);
  
  public abstract ConciliacionBancaria buscarPorId(Integer paramInteger);
  
  public abstract ConciliacionBancaria cargarDetalle(int paramInt);
  
  public abstract List<ConciliacionBancaria> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<MovimientoBancario> cargarDatosConciliar(ConciliacionBancaria paramConciliacionBancaria);
  
  public abstract List getReporteConciliacionBancaria(Date paramDate, int paramInt)
    throws ExcepcionAS2;
  
  public abstract void esEditable(ConciliacionBancaria paramConciliacionBancaria)
    throws ExcepcionAS2Financiero;
  
  public abstract Date obtenerFechaUltimaConciliacion(Date paramDate);
  
  public abstract void contabilizarGastosYRetenciones(ConciliacionBancaria paramConciliacionBancaria, List<DetalleInterfazContable> paramList)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract BigDecimal totalCreditoConciliado(ConciliacionBancaria paramConciliacionBancaria);
  
  public abstract BigDecimal totalDebitoConciliado(ConciliacionBancaria paramConciliacionBancaria);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioConciliacionBancaria
 * JD-Core Version:    0.7.0.1
 */