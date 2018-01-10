package com.asinfo.as2.financiero.activos.procesos.servicio;

import com.asinfo.as2.entities.HistoricoDepreciacion;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioDepreciacionMensual
{
  public abstract void guardar(HistoricoDepreciacion paramHistoricoDepreciacion)
    throws ExcepcionAS2Financiero;
  
  public abstract void anular(HistoricoDepreciacion paramHistoricoDepreciacion)
    throws ExcepcionAS2Financiero;
  
  public abstract HistoricoDepreciacion buscarPorId(int paramInt);
  
  public abstract List<HistoricoDepreciacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract void depreciar(HistoricoDepreciacion paramHistoricoDepreciacion)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void esEditable(HistoricoDepreciacion paramHistoricoDepreciacion)
    throws ExcepcionAS2Financiero;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.activos.procesos.servicio.ServicioDepreciacionMensual
 * JD-Core Version:    0.7.0.1
 */