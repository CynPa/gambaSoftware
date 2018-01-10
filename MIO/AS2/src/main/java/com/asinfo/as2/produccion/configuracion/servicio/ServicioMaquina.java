package com.asinfo.as2.produccion.configuracion.servicio;

import com.asinfo.as2.entities.produccion.Maquina;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioMaquina
{
  public abstract void guardar(Maquina paramMaquina);
  
  public abstract void eliminar(Maquina paramMaquina);
  
  public abstract Maquina buscarPorId(int paramInt);
  
  public abstract List<Maquina> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Maquina> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Maquina cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.configuracion.servicio.ServicioMaquina
 * JD-Core Version:    0.7.0.1
 */