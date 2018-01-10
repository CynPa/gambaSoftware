package com.asinfo.as2.financiero.contabilidad.reportes.servicio;

import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteChequeAsiento
{
  public abstract List<Object[]> getReporteChequeAsiento(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioReporteChequeAsiento
 * JD-Core Version:    0.7.0.1
 */