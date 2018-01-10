package com.asinfo.as2.ventas.configuracion.servicio;

import com.asinfo.as2.entities.Canal;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioCanal
{
  public abstract void guardar(Canal paramCanal);
  
  public abstract void eliminar(Canal paramCanal);
  
  public abstract Canal buscarPorId(Integer paramInteger);
  
  public abstract List<Canal> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Canal> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Canal cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.servicio.ServicioCanal
 * JD-Core Version:    0.7.0.1
 */