package com.asinfo.as2.nomina.procesos.servicio;

import com.asinfo.as2.entities.HistoricoEmpleado;
import com.asinfo.as2.entities.PagoRolEmpleado;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
import javax.ejb.Local;

@Local
public abstract interface ServicioFiniquitoEmpleado
{
  public abstract HistoricoEmpleado generarFiniquito(HistoricoEmpleado paramHistoricoEmpleado)
    throws ExcepcionAS2Nomina, ExcepcionAS2;
  
  public abstract PagoRolEmpleado procesarFiniquito(PagoRolEmpleado paramPagoRolEmpleado, boolean paramBoolean)
    throws ExcepcionAS2Nomina, ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void guardarFiniquito(PagoRolEmpleado paramPagoRolEmpleado)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void refrescarDatosFiniquito(PagoRolEmpleado paramPagoRolEmpleado);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.ServicioFiniquitoEmpleado
 * JD-Core Version:    0.7.0.1
 */