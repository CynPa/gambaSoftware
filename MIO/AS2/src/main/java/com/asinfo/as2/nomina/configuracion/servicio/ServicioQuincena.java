package com.asinfo.as2.nomina.configuracion.servicio;

import com.asinfo.as2.entities.Quincena;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioQuincena
{
  public abstract void guardar(Quincena paramQuincena);
  
  public abstract void eliminar(Quincena paramQuincena);
  
  public abstract Quincena buscarPorId(int paramInt);
  
  public abstract List<Quincena> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Quincena> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Quincena cargarDetalle(int paramInt);
  
  public abstract Quincena obtenerQuincenaPorCodigo(String paramString);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.ServicioQuincena
 * JD-Core Version:    0.7.0.1
 */