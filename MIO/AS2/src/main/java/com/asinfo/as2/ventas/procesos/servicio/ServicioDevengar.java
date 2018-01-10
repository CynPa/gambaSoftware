package com.asinfo.as2.ventas.procesos.servicio;

import com.asinfo.as2.entities.InterfazContableProceso;
import com.asinfo.as2.entities.Organizacion;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.Date;
import javax.ejb.Local;

@Local
public abstract interface ServicioDevengar
{
  public abstract void devengar(Date paramDate, InterfazContableProceso paramInterfazContableProceso, Organizacion paramOrganizacion)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.ServicioDevengar
 * JD-Core Version:    0.7.0.1
 */