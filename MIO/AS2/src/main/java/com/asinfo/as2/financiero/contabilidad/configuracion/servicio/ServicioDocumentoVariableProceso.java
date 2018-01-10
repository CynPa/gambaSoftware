package com.asinfo.as2.financiero.contabilidad.configuracion.servicio;

import com.asinfo.as2.entities.DocumentoVariableProceso;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioDocumentoVariableProceso
{
  public abstract void guardar(DocumentoVariableProceso paramDocumentoVariableProceso)
    throws ExcepcionAS2Financiero;
  
  public abstract void eliminar(DocumentoVariableProceso paramDocumentoVariableProceso);
  
  public abstract DocumentoVariableProceso buscarPorId(Integer paramInteger);
  
  public abstract DocumentoVariableProceso cargarDetalle(int paramInt);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<DocumentoVariableProceso> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<DocumentoVariableProceso> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoVariableProceso
 * JD-Core Version:    0.7.0.1
 */