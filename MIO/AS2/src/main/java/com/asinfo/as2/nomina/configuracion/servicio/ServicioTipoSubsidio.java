package com.asinfo.as2.nomina.configuracion.servicio;

import com.asinfo.as2.entities.TipoSubsidio;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioTipoSubsidio
{
  public abstract void guardar(TipoSubsidio paramTipoSubsidio)
    throws ExcepcionAS2Nomina, AS2Exception;
  
  public abstract void eliminar(TipoSubsidio paramTipoSubsidio);
  
  public abstract TipoSubsidio buscarPorId(int paramInt);
  
  public abstract List<TipoSubsidio> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<TipoSubsidio> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract TipoSubsidio cargarDetalle(int paramInt);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.ServicioTipoSubsidio
 * JD-Core Version:    0.7.0.1
 */