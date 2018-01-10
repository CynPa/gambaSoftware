package ec.gob.sri.comprobantes.ws.recepcion;

import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface RecepcionComprobantesOffline
  extends Remote
{
  public abstract RespuestaSolicitud validarComprobante(byte[] paramArrayOfByte)
    throws RemoteException;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     ec.gob.sri.comprobantes.ws.recepcion.RecepcionComprobantesOffline
 * JD-Core Version:    0.7.0.1
 */