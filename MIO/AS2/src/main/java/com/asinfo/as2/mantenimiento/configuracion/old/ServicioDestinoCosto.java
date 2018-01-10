package com.asinfo.as2.mantenimiento.configuracion.old;

import com.asinfo.as2.entities.DestinoCosto;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioDestinoCosto
{
  public abstract void guardar(DestinoCosto paramDestinoCosto);
  
  public abstract void eliminar(DestinoCosto paramDestinoCosto);
  
  public abstract DestinoCosto buscarPorId(int paramInt);
  
  public abstract List<DestinoCosto> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<DestinoCosto> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<DestinoCosto> autocompletarDestinoCosto(String paramString);
  
  public abstract DestinoCosto buscarPorCodigo(String paramString)
    throws ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.old.ServicioDestinoCosto
 * JD-Core Version:    0.7.0.1
 */