package ec.gob.sri.comprobantes.ws.recepcion;

import java.net.URL;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public abstract interface RecepcionComprobantesOfflineService
  extends Service
{
  public abstract String getRecepcionComprobantesOfflinePortAddress();
  
  public abstract RecepcionComprobantesOffline getRecepcionComprobantesOfflinePort()
    throws ServiceException;
  
  public abstract RecepcionComprobantesOffline getRecepcionComprobantesOfflinePort(URL paramURL)
    throws ServiceException;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     ec.gob.sri.comprobantes.ws.recepcion.RecepcionComprobantesOfflineService
 * JD-Core Version:    0.7.0.1
 */