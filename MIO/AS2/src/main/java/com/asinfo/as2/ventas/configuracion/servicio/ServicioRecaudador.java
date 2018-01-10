package com.asinfo.as2.ventas.configuracion.servicio;

import com.asinfo.as2.entities.Recaudador;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioRecaudador
{
  public abstract void guardar(Recaudador paramRecaudador);
  
  public abstract void eliminar(Recaudador paramRecaudador);
  
  public abstract Recaudador buscarPorId(int paramInt);
  
  public abstract List<Recaudador> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Recaudador> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Recaudador cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.servicio.ServicioRecaudador
 * JD-Core Version:    0.7.0.1
 */