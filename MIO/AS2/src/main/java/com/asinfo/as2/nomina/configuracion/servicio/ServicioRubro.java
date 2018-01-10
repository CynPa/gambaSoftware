package com.asinfo.as2.nomina.configuracion.servicio;

import com.asinfo.as2.entities.Rubro;
import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioRubro
{
  public abstract void guardar(Rubro paramRubro)
    throws ExcepcionAS2Nomina;
  
  public abstract void eliminar(Rubro paramRubro);
  
  public abstract Rubro buscarPorId(int paramInt);
  
  public abstract List<Rubro> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Rubro> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Rubro cargarDetalle(int paramInt);
  
  public abstract List<Rubro> getLisRubro();
  
  public abstract List<Rubro> getListaRubros(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.ServicioRubro
 * JD-Core Version:    0.7.0.1
 */