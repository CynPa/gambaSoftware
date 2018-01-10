package com.asinfo.as2.mantenimiento.configuracion.servicio;

import com.asinfo.as2.entities.mantenimiento.Equipo;
import java.util.HashMap;
import javax.ejb.Local;

@Local
public abstract interface ServicioDocumentoEquipo
{
  public abstract void actualizarAtributoEntidad(Equipo paramEquipo, HashMap<String, Object> paramHashMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioDocumentoEquipo
 * JD-Core Version:    0.7.0.1
 */