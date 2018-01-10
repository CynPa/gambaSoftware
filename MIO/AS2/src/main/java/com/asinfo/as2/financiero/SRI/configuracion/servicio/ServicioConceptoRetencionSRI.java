package com.asinfo.as2.financiero.SRI.configuracion.servicio;

import com.asinfo.as2.entities.sri.ConceptoRetencionSRI;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioConceptoRetencionSRI
{
  public abstract void guardar(ConceptoRetencionSRI paramConceptoRetencionSRI);
  
  public abstract void eliminar(ConceptoRetencionSRI paramConceptoRetencionSRI);
  
  public abstract ConceptoRetencionSRI buscarPorId(Integer paramInteger);
  
  public abstract List<ConceptoRetencionSRI> getConceptoListaRetencionPorFecha(Date paramDate)
    throws ExcepcionAS2Financiero;
  
  public abstract List<ConceptoRetencionSRI> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<ConceptoRetencionSRI> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract ConceptoRetencionSRI getConceptoRetencionPorIdYFecha(int paramInt, Date paramDate)
    throws ExcepcionAS2Financiero;
  
  public abstract List<ConceptoRetencionSRI> conceptoRetencionIVAPorcentaje(int paramInt);
  
  public abstract ConceptoRetencionSRI conceptoRetencionPorcentajeYCodigo(BigDecimal paramBigDecimal, String paramString, int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioConceptoRetencionSRI
 * JD-Core Version:    0.7.0.1
 */