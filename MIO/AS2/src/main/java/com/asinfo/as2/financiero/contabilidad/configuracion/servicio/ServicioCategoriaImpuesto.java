package com.asinfo.as2.financiero.contabilidad.configuracion.servicio;

import com.asinfo.as2.entities.CategoriaImpuesto;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioCategoriaImpuesto
{
  public abstract void guardar(CategoriaImpuesto paramCategoriaImpuesto)
    throws ExcepcionAS2;
  
  public abstract void eliminar(CategoriaImpuesto paramCategoriaImpuesto)
    throws ExcepcionAS2;
  
  public abstract CategoriaImpuesto buscarPorId(Integer paramInteger);
  
  public abstract CategoriaImpuesto cargarDetalle(int paramInt);
  
  public abstract List<CategoriaImpuesto> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<CategoriaImpuesto> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCategoriaImpuesto
 * JD-Core Version:    0.7.0.1
 */