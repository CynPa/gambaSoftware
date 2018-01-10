package com.asinfo.as2.financiero.contabilidad.procesos.servicio;

import com.asinfo.as2.entities.ConfiguracionExtractoBancario;
import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
import com.asinfo.as2.entities.InterfazContableProceso;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.as2.servicio.ServicioGenerico;
import java.io.InputStream;
import javax.ejb.Local;

@Local
public abstract interface ServicioExtractoBancario
  extends ServicioGenerico<InterfazContableProceso>
{
  public abstract InterfazContableProceso leerExtractoBancario(InterfazContableProceso paramInterfazContableProceso, InputStream paramInputStream, int paramInt, CuentaBancariaOrganizacion paramCuentaBancariaOrganizacion)
    throws ExcepcionAS2;
  
  public abstract InterfazContableProceso cargarDetalle(InterfazContableProceso paramInterfazContableProceso);
  
  public abstract void contabilizar(InterfazContableProceso paramInterfazContableProceso)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract ConfiguracionExtractoBancario cargarDetalle(ConfiguracionExtractoBancario paramConfiguracionExtractoBancario);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioExtractoBancario
 * JD-Core Version:    0.7.0.1
 */