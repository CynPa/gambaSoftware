package com.asinfo.as2.compronteselectronicos;

import com.asinfo.as2.compronteselectronicos.base.DocumentoElectronico;
import com.asinfo.as2.entities.GuiaRemision;
import com.asinfo.as2.entities.sri.ComprobanteElectronicoPendienteSRI;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2DocumentoElectronico;
import javax.ejb.Local;

@Local
public abstract interface ServicioGuiaRemisionSRIXML
{
  public abstract GuiaRemision generarClaveAcceso(DocumentoElectronico paramDocumentoElectronico, GuiaRemision paramGuiaRemision, boolean paramBoolean)
    throws AS2Exception;
  
  public abstract DocumentoElectronico autorizarGuiaRemision(ComprobanteElectronicoPendienteSRI paramComprobanteElectronicoPendienteSRI)
    throws AS2Exception;
  
  public abstract DocumentoElectronico comprobarAutorizacion(ComprobanteElectronicoPendienteSRI paramComprobanteElectronicoPendienteSRI)
    throws ExcepcionAS2DocumentoElectronico;
  
  public abstract void generarXML(DocumentoElectronico paramDocumentoElectronico, GuiaRemision paramGuiaRemision)
    throws AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compronteselectronicos.ServicioGuiaRemisionSRIXML
 * JD-Core Version:    0.7.0.1
 */