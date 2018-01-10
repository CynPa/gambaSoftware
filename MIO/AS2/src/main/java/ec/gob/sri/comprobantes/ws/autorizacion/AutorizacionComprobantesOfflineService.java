package ec.gob.sri.comprobantes.ws.autorizacion;

import java.net.URL;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public abstract interface AutorizacionComprobantesOfflineService
  extends Service
{
  public abstract String getAutorizacionComprobantesOfflinePortAddress();
  
  public abstract AutorizacionComprobantesOffline getAutorizacionComprobantesOfflinePort()
    throws ServiceException;
  
  public abstract AutorizacionComprobantesOffline getAutorizacionComprobantesOfflinePort(URL paramURL)
    throws ServiceException;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     ec.gob.sri.comprobantes.ws.autorizacion.AutorizacionComprobantesOfflineService
 * JD-Core Version:    0.7.0.1
 */