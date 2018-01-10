package com.asinfo.as2.financiero.contabilidad.configuracion.servicio;

import com.asinfo.as2.entities.Impuesto;
import com.asinfo.as2.entities.RangoImpuesto;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioImpuesto
{
  public abstract void guardar(Impuesto paramImpuesto)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void eliminar(Impuesto paramImpuesto)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract Impuesto buscarPorId(Integer paramInteger)
    throws ExcepcionAS2;
  
  public abstract Impuesto cargarDetalle(int paramInt)
    throws ExcepcionAS2Financiero;
  
  public abstract List<Impuesto> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Impuesto> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract boolean getIndicadorTributario();
  
  public abstract RangoImpuesto getRangoRangoImpuestoTributario(Date paramDate, int paramInt);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Impuesto buscarPorId(int paramInt);
  
  public abstract Impuesto buscarPorCodigo(String paramString, int paramInt);
  
  public abstract BigDecimal getPorcentajeIVA(int paramInt, Date paramDate);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioImpuesto
 * JD-Core Version:    0.7.0.1
 */