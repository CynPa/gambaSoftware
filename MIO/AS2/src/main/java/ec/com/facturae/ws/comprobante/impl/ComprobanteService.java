package ec.com.facturae.ws.comprobante.impl;

import ec.com.facturae.ws.comprobante.ServicioWebComprobante;
import java.net.URL;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public abstract interface ComprobanteService
  extends Service
{
  public abstract String getComprobantePortAddress();
  
  public abstract ServicioWebComprobante getComprobantePort()
    throws ServiceException;
  
  public abstract ServicioWebComprobante getComprobantePort(URL paramURL)
    throws ServiceException;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     ec.com.facturae.ws.comprobante.impl.ComprobanteService
 * JD-Core Version:    0.7.0.1
 */