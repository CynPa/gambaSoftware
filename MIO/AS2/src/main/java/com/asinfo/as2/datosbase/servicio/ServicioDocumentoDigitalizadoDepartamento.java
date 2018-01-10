package com.asinfo.as2.datosbase.servicio;

import com.asinfo.as2.entities.DocumentoDigitalizado;
import com.asinfo.as2.entities.DocumentoDigitalizadoDepartamento;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioDocumentoDigitalizadoDepartamento
{
  public abstract List<DocumentoDigitalizado> obtenerDocumentosDigitalizados(int paramInt);
  
  public abstract void eliminar(DocumentoDigitalizadoDepartamento paramDocumentoDigitalizadoDepartamento);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.ServicioDocumentoDigitalizadoDepartamento
 * JD-Core Version:    0.7.0.1
 */