package com.asinfo.as2.servicio;

import com.asinfo.as2.entities.Tema;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioTema
{
  public abstract List<Tema> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.servicio.ServicioTema
 * JD-Core Version:    0.7.0.1
 */