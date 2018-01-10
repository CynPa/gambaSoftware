package com.asinfo.as2.compronteselectronicos;

import com.asinfo.as2.compronteselectronicos.base.DocumentoElectronico;
import com.asinfo.as2.entities.sri.ComprobanteElectronicoPendienteSRI;
import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2DocumentoElectronico;
import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
import javax.ejb.Local;

@Local
public abstract interface ServicioFacturaProveedorSRIXML
{
  public abstract FacturaProveedorSRI generarClaveAcceso(DocumentoElectronico paramDocumentoElectronico, FacturaProveedorSRI paramFacturaProveedorSRI, boolean paramBoolean)
    throws AS2Exception;
  
  public abstract DocumentoElectronico autorizarFactura(ComprobanteElectronicoPendienteSRI paramComprobanteElectronicoPendienteSRI)
    throws AS2Exception, ExcepcionAS2Ventas;
  
  public abstract DocumentoElectronico comprobarAutorizacion(ComprobanteElectronicoPendienteSRI paramComprobanteElectronicoPendienteSRI)
    throws ExcepcionAS2DocumentoElectronico;
  
  public abstract void generarXML(DocumentoElectronico paramDocumentoElectronico, FacturaProveedorSRI paramFacturaProveedorSRI)
    throws AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compronteselectronicos.ServicioFacturaProveedorSRIXML
 * JD-Core Version:    0.7.0.1
 */