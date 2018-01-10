package com.asinfo.as2.ventas.reportes.servicio;

import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReportePedidosVSInventario
{
  public abstract List getPedidosVSInventario(Date paramDate)
    throws ExcepcionAS2Ventas;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.servicio.ServicioReportePedidosVSInventario
 * JD-Core Version:    0.7.0.1
 */