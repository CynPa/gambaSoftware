package com.asinfo.as2.configuracionbase.servicio;

import java.sql.SQLException;
import javax.ejb.Local;

@Local
public abstract interface ServicioUpdate
{
  public abstract void cargarUpdate()
    throws SQLException;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.ServicioUpdate
 * JD-Core Version:    0.7.0.1
 */