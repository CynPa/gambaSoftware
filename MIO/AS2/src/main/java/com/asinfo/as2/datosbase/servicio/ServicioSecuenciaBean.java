package com.asinfo.as2.datosbase.servicio;

import com.asinfo.as2.entities.Secuencia;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioSecuenciaBean
{
  public abstract void guardarSecuencia(Secuencia paramSecuencia);
  
  public abstract void eliminarSecuancia(Secuencia paramSecuencia);
  
  public abstract List<Secuencia> getSecuencias();
  
  public abstract Secuencia buscarPorId(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.ServicioSecuenciaBean
 * JD-Core Version:    0.7.0.1
 */