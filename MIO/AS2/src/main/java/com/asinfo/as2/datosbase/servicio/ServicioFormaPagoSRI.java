package com.asinfo.as2.datosbase.servicio;

import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.FormaPagoSRI;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioFormaPagoSRI
{
  public abstract List<FormaPagoSRI> getListaFormaPagoSRI(Empresa paramEmpresa);
  
  public abstract void guardar(FormaPagoSRI paramFormaPagoSRI);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.ServicioFormaPagoSRI
 * JD-Core Version:    0.7.0.1
 */