package com.asinfo.as2.configuracionbase.servicio;

import com.asinfo.as2.entities.Configuracion;
import com.asinfo.as2.enumeraciones.Parametro;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

@Remote
public abstract interface ServicioConfiguracionRemoto
{
  public abstract void guardar(Configuracion paramConfiguracion);
  
  public abstract void eliminar(Configuracion paramConfiguracion);
  
  public abstract Configuracion buscarPorId(Integer paramInteger);
  
  public abstract List<Configuracion> obtenerTodos();
  
  public abstract List<Configuracion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract Object obtenerParametro(Parametro paramParametro);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.ServicioConfiguracionRemoto
 * JD-Core Version:    0.7.0.1
 */