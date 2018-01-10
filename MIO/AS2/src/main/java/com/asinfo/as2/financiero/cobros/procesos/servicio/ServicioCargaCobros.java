package com.asinfo.as2.financiero.cobros.procesos.servicio;

import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.io.InputStream;
import javax.ejb.Local;

@Local
public abstract interface ServicioCargaCobros
{
  public abstract void cargarCobros(Integer paramInteger, String paramString, InputStream paramInputStream, int paramInt, boolean paramBoolean)
    throws ExcepcionAS2, ExcepcionAS2Financiero;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioCargaCobros
 * JD-Core Version:    0.7.0.1
 */