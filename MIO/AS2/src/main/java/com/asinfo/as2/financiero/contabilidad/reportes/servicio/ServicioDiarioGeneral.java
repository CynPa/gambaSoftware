package com.asinfo.as2.financiero.contabilidad.reportes.servicio;

import com.asinfo.as2.entities.Asiento;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.Date;
import java.util.List;

public abstract interface ServicioDiarioGeneral
{
  public abstract List<Asiento> cargarDatos(Date paramDate1, Date paramDate2, int paramInt1, int paramInt2, Asiento paramAsiento)
    throws ExcepcionAS2Financiero;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioDiarioGeneral
 * JD-Core Version:    0.7.0.1
 */