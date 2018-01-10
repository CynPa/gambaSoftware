package com.asinfo.as2.datosbase.servicio;

import com.asinfo.as2.entities.PersonaResponsable;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPersonaResponsable
{
  public abstract void guardar(PersonaResponsable paramPersonaResponsable);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<PersonaResponsable> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<PersonaResponsable> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void eliminar(PersonaResponsable paramPersonaResponsable);
  
  public abstract List<PersonaResponsable> autocompletarMedico(String paramString, int paramInt);
  
  public abstract PersonaResponsable cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.ServicioPersonaResponsable
 * JD-Core Version:    0.7.0.1
 */