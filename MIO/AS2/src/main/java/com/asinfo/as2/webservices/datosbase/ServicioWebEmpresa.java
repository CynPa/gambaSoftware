package com.asinfo.as2.webservices.datosbase;

import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.excepciones.ExcepcionAS2Identification;
import com.asinfo.pos.model.Cliente;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public abstract interface ServicioWebEmpresa
{
  @WebMethod
  public abstract boolean crearClienteConDatosBasicos(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2)
    throws ExcepcionAS2Identification, ExcepcionAS2, Exception;
  
  @WebMethod
  public abstract Cliente getCliente(String paramString)
    throws ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.datosbase.ServicioWebEmpresa
 * JD-Core Version:    0.7.0.1
 */