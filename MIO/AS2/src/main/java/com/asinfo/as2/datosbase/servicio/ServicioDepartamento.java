package com.asinfo.as2.datosbase.servicio;

import com.asinfo.as2.entities.Departamento;
import com.asinfo.as2.entities.Rubro;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioDepartamento
{
  public abstract void guardar(Departamento paramDepartamento);
  
  public abstract void eliminar(Departamento paramDepartamento);
  
  public abstract Departamento buscarPorId(int paramInt);
  
  public abstract List<Departamento> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Departamento> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Departamento cargarDetalle(int paramInt);
  
  public abstract Departamento buscarPorNombre(String paramString);
  
  public abstract List<Rubro> getListaRubros(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.ServicioDepartamento
 * JD-Core Version:    0.7.0.1
 */