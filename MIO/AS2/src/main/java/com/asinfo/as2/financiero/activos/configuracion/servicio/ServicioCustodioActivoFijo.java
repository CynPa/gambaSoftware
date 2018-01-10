package com.asinfo.as2.financiero.activos.configuracion.servicio;

import com.asinfo.as2.entities.CustodioActivoFijo;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioCustodioActivoFijo
{
  public abstract void guardar(CustodioActivoFijo paramCustodioActivoFijo)
    throws ExcepcionAS2Financiero;
  
  public abstract void eliminar(CustodioActivoFijo paramCustodioActivoFijo);
  
  public abstract CustodioActivoFijo buscarPorId(int paramInt);
  
  public abstract List<CustodioActivoFijo> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<CustodioActivoFijo> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract CustodioActivoFijo cargarDetalle(int paramInt);
  
  public abstract CustodioActivoFijo buscarPorIdActivoFijoFechaFinNull(int paramInt);
  
  public abstract void actualizaFechaCustodioFijo(CustodioActivoFijo paramCustodioActivoFijo);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioCustodioActivoFijo
 * JD-Core Version:    0.7.0.1
 */