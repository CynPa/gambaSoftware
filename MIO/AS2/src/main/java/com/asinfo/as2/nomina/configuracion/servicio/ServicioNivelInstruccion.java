package com.asinfo.as2.nomina.configuracion.servicio;

import com.asinfo.as2.entities.NivelInstruccion;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioNivelInstruccion
{
  public abstract void guardar(NivelInstruccion paramNivelInstruccion);
  
  public abstract void eliminar(NivelInstruccion paramNivelInstruccion);
  
  public abstract NivelInstruccion buscarPorId(int paramInt);
  
  public abstract List<NivelInstruccion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<NivelInstruccion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract NivelInstruccion cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.ServicioNivelInstruccion
 * JD-Core Version:    0.7.0.1
 */