package com.asinfo.as2.inventario.configuracion.servicio;

import com.asinfo.as2.entities.TipoVehiculo;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioTipoVehiculo
{
  public abstract void guardar(TipoVehiculo paramTipoVehiculo)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void eliminar(TipoVehiculo paramTipoVehiculo)
    throws ExcepcionAS2;
  
  public abstract TipoVehiculo buscarPorId(Integer paramInteger)
    throws ExcepcionAS2;
  
  public abstract TipoVehiculo cargarDetalle(int paramInt)
    throws ExcepcionAS2;
  
  public abstract List<TipoVehiculo> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<TipoVehiculo> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract void validarWarning(TipoVehiculo paramTipoVehiculo)
    throws AS2Exception;
  
  public abstract void validar(TipoVehiculo paramTipoVehiculo)
    throws AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.ServicioTipoVehiculo
 * JD-Core Version:    0.7.0.1
 */