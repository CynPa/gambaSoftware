package com.asinfo.as2.financiero.contabilidad.configuracion.servicio;

import com.asinfo.as2.entities.ConceptoContable;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioConceptoContable
{
  public abstract void guardar(ConceptoContable paramConceptoContable)
    throws ExcepcionAS2;
  
  public abstract void eliminar(ConceptoContable paramConceptoContable);
  
  public abstract ConceptoContable buscarPorId(Integer paramInteger);
  
  public abstract List<ConceptoContable> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<ConceptoContable> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioConceptoContable
 * JD-Core Version:    0.7.0.1
 */