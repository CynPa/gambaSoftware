package com.asinfo.as2.combustibles;

import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.io.InputStream;
import javax.ejb.Local;

@Local
public abstract interface ServicioCargaProcesoCombustibles
{
  public abstract void cargarFacturasBancoInternacionalMasgas(String paramString, InputStream paramInputStream, Estado paramEstado, boolean paramBoolean)
    throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception;
  
  public abstract void cargarFacturasBancoGeneralRuminahuiPichinchaMasgas(String paramString, InputStream paramInputStream, boolean paramBoolean)
    throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception;
  
  public abstract void cargarFacturaUnica(String paramString, InputStream paramInputStream, int paramInt)
    throws ExcepcionAS2, ExcepcionAS2Financiero;
  
  public abstract void cargarFacturasBancoGuayaquilMasgas(String paramString, InputStream paramInputStream, boolean paramBoolean)
    throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.combustibles.ServicioCargaProcesoCombustibles
 * JD-Core Version:    0.7.0.1
 */