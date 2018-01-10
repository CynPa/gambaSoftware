package com.asinfo.as2.inventario.procesos.servicio;

import com.asinfo.as2.entities.PreDevolucionCliente;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPreDevolucionCliente
{
  public abstract void guardar(PreDevolucionCliente paramPreDevolucionCliente);
  
  public abstract void eliminar(PreDevolucionCliente paramPreDevolucionCliente);
  
  public abstract PreDevolucionCliente buscarPorId(int paramInt);
  
  public abstract List<PreDevolucionCliente> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<PreDevolucionCliente> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract PreDevolucionCliente cargarDetalle(int paramInt);
  
  public abstract List<PreDevolucionCliente> buscarPreDevolucionesPorTransportista(int paramInt1, boolean paramBoolean, int paramInt2, Date paramDate);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.servicio.ServicioPreDevolucionCliente
 * JD-Core Version:    0.7.0.1
 */