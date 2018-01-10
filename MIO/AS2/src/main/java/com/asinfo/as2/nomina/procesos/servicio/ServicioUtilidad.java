package com.asinfo.as2.nomina.procesos.servicio;

import com.asinfo.as2.entities.Utilidad;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioUtilidad
{
  public abstract void guardar(Utilidad paramUtilidad)
    throws AS2Exception;
  
  public abstract void eliminar(Utilidad paramUtilidad);
  
  public abstract Utilidad buscarPorId(int paramInt);
  
  public abstract List<Utilidad> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Utilidad> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Utilidad cargarDetalle(int paramInt);
  
  public abstract Utilidad procesarUtilidad(Utilidad paramUtilidad)
    throws ExcepcionAS2;
  
  public abstract Utilidad obtenerUtilidadPorAnio(int paramInt1, int paramInt2);
  
  public abstract void cerrar(Utilidad paramUtilidad)
    throws ExcepcionAS2, AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.ServicioUtilidad
 * JD-Core Version:    0.7.0.1
 */