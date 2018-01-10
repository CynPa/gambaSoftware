package com.asinfo.as2.financiero.contabilidad.configuracion.servicio;

import com.asinfo.as2.entities.CriterioDistribucion;
import com.asinfo.as2.enumeraciones.DocumentoBase;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioCriterioDistribucion
{
  public abstract void guardar(CriterioDistribucion paramCriterioDistribucion)
    throws ExcepcionAS2Financiero;
  
  public abstract CriterioDistribucion buscarPorId(Integer paramInteger);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<CriterioDistribucion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<CriterioDistribucion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void guardarListaCriterioDistribucion(List<CriterioDistribucion> paramList, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws ExcepcionAS2Financiero;
  
  public abstract List<CriterioDistribucion> obtenerListaPorDocumentoBase(int paramInt, DocumentoBase paramDocumentoBase);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCriterioDistribucion
 * JD-Core Version:    0.7.0.1
 */