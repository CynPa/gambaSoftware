package com.asinfo.as2.mantenimiento.configuracion.servicio;

import com.asinfo.as2.entities.mantenimiento.ConsumoAgilMantenimiento;
import com.asinfo.as2.entities.mantenimiento.DetalleConsumoAgilMantenimiento;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.AS2ExceptionMantenimiento;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioConsumoAgilMantenimiento
{
  public abstract void guardar(ConsumoAgilMantenimiento paramConsumoAgilMantenimiento)
    throws AS2ExceptionMantenimiento, AS2ExceptionMantenimiento, ExcepcionAS2, AS2Exception;
  
  public abstract void eliminar(ConsumoAgilMantenimiento paramConsumoAgilMantenimiento)
    throws AS2ExceptionMantenimiento, ExcepcionAS2;
  
  public abstract ConsumoAgilMantenimiento buscarPorId(Integer paramInteger);
  
  public abstract List<ConsumoAgilMantenimiento> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract ConsumoAgilMantenimiento cargarDetalle(ConsumoAgilMantenimiento paramConsumoAgilMantenimiento);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract ConsumoAgilMantenimiento cargarDetalle();
  
  public abstract void guardar(ConsumoAgilMantenimiento paramConsumoAgilMantenimiento, DetalleConsumoAgilMantenimiento paramDetalleConsumoAgilMantenimiento)
    throws AS2ExceptionMantenimiento, AS2ExceptionMantenimiento, ExcepcionAS2, AS2Exception;
  
  public abstract void eliminar(DetalleConsumoAgilMantenimiento paramDetalleConsumoAgilMantenimiento)
    throws AS2ExceptionMantenimiento, ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioConsumoAgilMantenimiento
 * JD-Core Version:    0.7.0.1
 */