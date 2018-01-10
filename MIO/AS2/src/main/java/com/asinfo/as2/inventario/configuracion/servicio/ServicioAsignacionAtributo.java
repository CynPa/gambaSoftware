package com.asinfo.as2.inventario.configuracion.servicio;

import com.asinfo.as2.entities.AsignacionAtributo;
import com.asinfo.as2.entities.Atributo;
import com.asinfo.as2.entities.ConjuntoAtributo;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioAsignacionAtributo
{
  public abstract void guardar(AsignacionAtributo paramAsignacionAtributo);
  
  public abstract void eliminar(AsignacionAtributo paramAsignacionAtributo);
  
  public abstract AsignacionAtributo buscarPorId(int paramInt);
  
  public abstract List<Atributo> obtenerAtributosPorConjunto(ConjuntoAtributo paramConjuntoAtributo);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.ServicioAsignacionAtributo
 * JD-Core Version:    0.7.0.1
 */