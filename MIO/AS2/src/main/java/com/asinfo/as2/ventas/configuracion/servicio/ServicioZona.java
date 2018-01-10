package com.asinfo.as2.ventas.configuracion.servicio;

import com.asinfo.as2.entities.Zona;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioZona
{
  public abstract void guardar(Zona paramZona);
  
  public abstract void eliminar(Zona paramZona);
  
  public abstract Zona buscarPorId(int paramInt);
  
  public abstract List<Zona> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Zona> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Zona cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.servicio.ServicioZona
 * JD-Core Version:    0.7.0.1
 */