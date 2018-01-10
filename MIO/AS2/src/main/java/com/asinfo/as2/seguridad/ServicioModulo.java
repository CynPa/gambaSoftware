package com.asinfo.as2.seguridad;

import com.asinfo.as2.entities.Modulo;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioModulo
{
  public abstract void guardar(Modulo paramModulo);
  
  public abstract void eliminar(Modulo paramModulo);
  
  public abstract Modulo buscarPorId(Integer paramInteger);
  
  public abstract List<Modulo> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.ServicioModulo
 * JD-Core Version:    0.7.0.1
 */