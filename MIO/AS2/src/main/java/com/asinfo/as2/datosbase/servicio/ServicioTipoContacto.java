package com.asinfo.as2.datosbase.servicio;

import com.asinfo.as2.entities.TipoContacto;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioTipoContacto
{
  public abstract void guardar(TipoContacto paramTipoContacto);
  
  public abstract void eliminar(TipoContacto paramTipoContacto);
  
  public abstract TipoContacto buscarPorId(int paramInt);
  
  public abstract List<TipoContacto> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<TipoContacto> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.ServicioTipoContacto
 * JD-Core Version:    0.7.0.1
 */