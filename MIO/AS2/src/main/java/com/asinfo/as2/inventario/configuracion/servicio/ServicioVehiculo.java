package com.asinfo.as2.inventario.configuracion.servicio;

import com.asinfo.as2.entities.Vehiculo;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioVehiculo
{
  public abstract void guardar(Vehiculo paramVehiculo);
  
  public abstract void eliminar(Vehiculo paramVehiculo);
  
  public abstract Vehiculo buscarPorId(Integer paramInteger);
  
  public abstract List<Vehiculo> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Vehiculo> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<Vehiculo> obtenerListaVehiculoPorTransportista(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.ServicioVehiculo
 * JD-Core Version:    0.7.0.1
 */