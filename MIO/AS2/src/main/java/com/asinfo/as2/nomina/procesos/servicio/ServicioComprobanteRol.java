package com.asinfo.as2.nomina.procesos.servicio;

import com.asinfo.as2.entities.PagoRol;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
import javax.ejb.Local;

@Local
public abstract interface ServicioComprobanteRol
{
  public abstract void contabilizar(PagoRol paramPagoRol)
    throws ExcepcionAS2, ExcepcionAS2Financiero, ExcepcionAS2Nomina, AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.ServicioComprobanteRol
 * JD-Core Version:    0.7.0.1
 */