package com.asinfo.as2.financiero.contabilidad.configuracion.servicio;

import com.asinfo.as2.entities.CentroCosto;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioCentroCosto
{
  public abstract void guardar(CentroCosto paramCentroCosto)
    throws ExcepcionAS2Financiero;
  
  public abstract void eliminar(CentroCosto paramCentroCosto)
    throws ExcepcionAS2Financiero;
  
  public abstract CentroCosto buscarPorId(int paramInt);
  
  public abstract CentroCosto buscarPorCodigo(String paramString)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract List<CentroCosto> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap)
    throws ExcepcionAS2Financiero;
  
  public abstract List<CentroCosto> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<CentroCosto> buscarPorNivel(int paramInt);
  
  public abstract List<CentroCosto> ObtenerListaComboPorBodega(int paramInt);
  
  public abstract CentroCosto cargarDetalle(int paramInt);
  
  public abstract List<CentroCosto> buscarPorCodigoNivelCentroCosto(int paramInt)
    throws ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCentroCosto
 * JD-Core Version:    0.7.0.1
 */