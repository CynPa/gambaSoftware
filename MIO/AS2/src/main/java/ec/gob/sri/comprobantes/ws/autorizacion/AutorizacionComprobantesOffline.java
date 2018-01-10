package ec.gob.sri.comprobantes.ws.autorizacion;

import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface AutorizacionComprobantesOffline
  extends Remote
{
  public abstract RespuestaComprobante autorizacionComprobante(String paramString)
    throws RemoteException;
  
  public abstract RespuestaLote autorizacionComprobanteLote(String paramString)
    throws RemoteException;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     ec.gob.sri.comprobantes.ws.autorizacion.AutorizacionComprobantesOffline
 * JD-Core Version:    0.7.0.1
 */