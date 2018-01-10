package com.asinfo.as2.compronteselectronicos;

import com.asinfo.as2.entities.sri.ComprobanteElectronicoPendienteSRI;
import com.asinfo.as2.enumeraciones.DocumentoBase;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioComprobanteElectronicoPeriodico
{
  public abstract void sincronizarComprobantesSRI(int paramInt, List<DocumentoBase> paramList);
  
  public abstract void publicarClaveAccesoPendiente(int paramInt);
  
  public abstract void enviarComprobanteSRI(ComprobanteElectronicoPendienteSRI paramComprobanteElectronicoPendienteSRI);
  
  public abstract boolean comprobarAutorizacionComprobanteSRI(ComprobanteElectronicoPendienteSRI paramComprobanteElectronicoPendienteSRI, boolean paramBoolean);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compronteselectronicos.ServicioComprobanteElectronicoPeriodico
 * JD-Core Version:    0.7.0.1
 */