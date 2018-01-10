package com.asinfo.as2.compronteselectronicos;

import com.asinfo.as2.compronteselectronicos.base.DocumentoElectronico;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.excepciones.AS2Exception;
import javax.ejb.Local;

@Local
public abstract interface ServicioDocumentoElectronico
{
  public abstract String autorizarDocumento(DocumentoElectronico paramDocumentoElectronico)
    throws AS2Exception;
  
  public abstract String comprobarAutorizacionDocumento(DocumentoElectronico paramDocumentoElectronico)
    throws AS2Exception;
  
  public abstract void enviarDocumentoPorEmail(DocumentoElectronico paramDocumentoElectronico, Empresa paramEmpresa)
    throws AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compronteselectronicos.ServicioDocumentoElectronico
 * JD-Core Version:    0.7.0.1
 */