package com.asinfo.as2.nomina.configuracion.servicio;

import com.asinfo.as2.entities.Titulo;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioTitulo
{
  public abstract void guardar(Titulo paramTitulo);
  
  public abstract void eliminar(Titulo paramTitulo);
  
  public abstract Titulo buscarPorId(int paramInt);
  
  public abstract List<Titulo> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Titulo> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Titulo cargarDetalle(int paramInt);
  
  public abstract Titulo buscarPorNombre(String paramString);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.ServicioTitulo
 * JD-Core Version:    0.7.0.1
 */