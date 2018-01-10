package ec.com.facturae.ws.comprobante;

import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface ServicioWebComprobante
  extends Remote
{
  public abstract boolean insertarClaveAcceso(String paramString1, String paramString2, String paramString3)
    throws RemoteException, FacturaeException;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     ec.com.facturae.ws.comprobante.ServicioWebComprobante
 * JD-Core Version:    0.7.0.1
 */