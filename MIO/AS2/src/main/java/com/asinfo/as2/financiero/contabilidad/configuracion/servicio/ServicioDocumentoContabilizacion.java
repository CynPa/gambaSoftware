package com.asinfo.as2.financiero.contabilidad.configuracion.servicio;

import com.asinfo.as2.entities.CriterioContabilizacion;
import com.asinfo.as2.entities.DocumentoContabilizacion;
import com.asinfo.as2.entities.DocumentoVariableProceso;
import com.asinfo.as2.enumeraciones.DocumentoBase;
import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioDocumentoContabilizacion
{
  public abstract void guardar(DocumentoContabilizacion paramDocumentoContabilizacion)
    throws ExcepcionAS2Financiero;
  
  public abstract DocumentoContabilizacion buscarPorId(Integer paramInteger);
  
  public abstract DocumentoContabilizacion cargarDetalle(int paramInt);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<DocumentoContabilizacion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<DocumentoContabilizacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<DocumentoVariableProceso> obtenerListaComboDocumentoVariableProceso(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void guardarListaDocumentoContabilizacion(List<DocumentoContabilizacion> paramList)
    throws ExcepcionAS2Financiero;
  
  public abstract List<DocumentoContabilizacion> obtenerListaPorDocumentoBase(int paramInt, DocumentoBase paramDocumentoBase);
  
  public abstract List<CriterioContabilizacion> getListaCriterioContabilizacion(int paramInt, DocumentoBase paramDocumentoBase, ProcesoContabilizacionEnum paramProcesoContabilizacionEnum);
  
  public abstract List<DocumentoContabilizacion> obtenerListaPorDocumentoBase(int paramInt, DocumentoBase paramDocumentoBase, ProcesoContabilizacionEnum paramProcesoContabilizacionEnum);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoContabilizacion
 * JD-Core Version:    0.7.0.1
 */