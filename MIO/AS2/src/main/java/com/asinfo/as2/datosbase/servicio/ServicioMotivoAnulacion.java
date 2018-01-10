package com.asinfo.as2.datosbase.servicio;

import com.asinfo.as2.entities.MotivoAnulacion;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioMotivoAnulacion
{
  public abstract void guardar(MotivoAnulacion paramMotivoAnulacion);
  
  public abstract void eliminar(MotivoAnulacion paramMotivoAnulacion);
  
  public abstract MotivoAnulacion buscarPorId(int paramInt);
  
  public abstract List<MotivoAnulacion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<MotivoAnulacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.ServicioMotivoAnulacion
 * JD-Core Version:    0.7.0.1
 */