package com.asinfo.as2.servicio;

import com.asinfo.as2.entities.EntidadBase;
import com.asinfo.as2.excepciones.AS2Exception;
import java.util.List;
import java.util.Map;

public abstract interface ServicioGenerico<T extends EntidadBase>
{
  public abstract void guardar(T paramT)
    throws AS2Exception;
  
  public abstract void eliminar(T paramT);
  
  public abstract T buscarPorId(Class paramClass, Integer paramInteger);
  
  public abstract List<T> obtenerListaCombo(Class paramClass, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<T> obtenerListaPorPagina(Class paramClass, int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Class paramClass, Map<String, String> paramMap);
  
  public abstract T buscarPorCodigo(Class paramClass, int paramInt, String paramString)
    throws AS2Exception;
  
  public abstract T buscarPorNombre(Class paramClass, int paramInt, String paramString);
  
  public abstract void guardarValidar(T paramT)
    throws AS2Exception;
  
  public abstract List<T> obtenerListaPorPagina(Class paramClass, int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap, List<String> paramList);
  
  public abstract T cargarDetalle(Class paramClass, int paramInt, List<String> paramList);
  
  public abstract void guardarValidar(T paramT, List<? extends EntidadBase> paramList)
    throws AS2Exception;
  
  public abstract void eliminar(T paramT, List<? extends EntidadBase> paramList);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.servicio.ServicioGenerico
 * JD-Core Version:    0.7.0.1
 */