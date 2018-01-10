package com.asinfo.as2.ventas.reportes.servicio;

import com.asinfo.as2.entities.HojaRuta;
import com.asinfo.as2.enumeraciones.TipoOrganizacion;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteGuiaRemision
{
  public abstract List getReporteGuiaRemision(int paramInt1, int paramInt2, int paramInt3, TipoOrganizacion paramTipoOrganizacion, HojaRuta paramHojaRuta);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.servicio.ServicioReporteGuiaRemision
 * JD-Core Version:    0.7.0.1
 */