package com.asinfo.as2.datosbase.servicio;

import com.asinfo.as2.entities.EstadoProceso;
import com.asinfo.as2.enumeraciones.DocumentoBase;
import com.asinfo.as2.enumeraciones.Estado;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioEstadoProceso
{
  public abstract void guardar(EstadoProceso paramEstadoProceso);
  
  public abstract void eliminar(EstadoProceso paramEstadoProceso);
  
  public abstract EstadoProceso buscarPorId(int paramInt);
  
  public abstract List<Estado> buscarPorDocumentoBase(DocumentoBase paramDocumentoBase);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.ServicioEstadoProceso
 * JD-Core Version:    0.7.0.1
 */