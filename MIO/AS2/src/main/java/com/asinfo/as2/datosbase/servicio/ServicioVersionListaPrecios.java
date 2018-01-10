package com.asinfo.as2.datosbase.servicio;

import com.asinfo.as2.entities.ListaPrecios;
import com.asinfo.as2.entities.VersionListaPrecios;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioVersionListaPrecios
{
  public abstract List<VersionListaPrecios> autocompletarListaVersionListaPrecios(String paramString, ListaPrecios paramListaPrecios);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.ServicioVersionListaPrecios
 * JD-Core Version:    0.7.0.1
 */