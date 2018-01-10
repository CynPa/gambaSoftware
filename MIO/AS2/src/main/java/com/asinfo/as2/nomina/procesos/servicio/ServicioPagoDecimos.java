package com.asinfo.as2.nomina.procesos.servicio;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioPagoDecimos
{
  public abstract List<Object[]> datosArchivoDecimoTercero(Date paramDate1, Date paramDate2);
  
  public abstract List<Object[]> datosArchivoDecimoCuarto(Date paramDate1, Date paramDate2);
  
  public abstract List<Object[]> datosArchivoUtilidades(int paramInt, String paramString1, String paramString2, String paramString3);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.ServicioPagoDecimos
 * JD-Core Version:    0.7.0.1
 */