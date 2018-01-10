package com.asinfo.as2.internacionalizacion.servicio;

import com.asinfo.as2.entities.Idioma;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioInternacionalizacionBean
{
  public abstract List<Idioma> getIdiomas();
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.internacionalizacion.servicio.ServicioInternacionalizacionBean
 * JD-Core Version:    0.7.0.1
 */