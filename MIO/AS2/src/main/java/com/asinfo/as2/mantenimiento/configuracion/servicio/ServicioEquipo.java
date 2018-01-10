package com.asinfo.as2.mantenimiento.configuracion.servicio;

import com.asinfo.as2.entities.mantenimiento.Equipo;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioEquipo
{
  public abstract void guardar(Equipo paramEquipo)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract void eliminar(Equipo paramEquipo)
    throws ExcepcionAS2;
  
  public abstract Equipo buscarPorId(Integer paramInteger)
    throws ExcepcionAS2;
  
  public abstract List<Equipo> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract Equipo cargarDetalle(Equipo paramEquipo);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<Equipo> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void actualizarAtributoEntidad(Equipo paramEquipo, HashMap<String, Object> paramHashMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioEquipo
 * JD-Core Version:    0.7.0.1
 */