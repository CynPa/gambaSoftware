package com.asinfo.as2.nomina.configuracion.servicio;

import com.asinfo.as2.entities.LlamadoAtencion;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioLlamadoAtencion
{
  public abstract List<LlamadoAtencion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract void guardar(LlamadoAtencion paramLlamadoAtencion);
  
  public abstract void eliminar(LlamadoAtencion paramLlamadoAtencion);
  
  public abstract LlamadoAtencion buscarPorId(int paramInt);
  
  public abstract LlamadoAtencion cargarDetalles(int paramInt);
  
  public abstract List<LlamadoAtencion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List reportellamadosAtencion(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Date paramDate1, Date paramDate2);
  
  public abstract void actualizarAtributoEntidad(LlamadoAtencion paramLlamadoAtencion, HashMap<String, Object> paramHashMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.ServicioLlamadoAtencion
 * JD-Core Version:    0.7.0.1
 */