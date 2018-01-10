package com.asinfo.as2.produccion.procesos.servicio;

import com.asinfo.as2.entities.DetalleOrdenSalidaMaterialOrdenFabricacion;
import com.asinfo.as2.entities.LecturaBalanza;
import com.asinfo.as2.entities.produccion.OrdenFabricacion;
import com.asinfo.as2.excepciones.AS2Exception;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioDesechoMaterial
{
  public abstract void agregarPesoDesecho(LecturaBalanza paramLecturaBalanza)
    throws AS2Exception;
  
  public abstract void eliminarPesoDesecho(LecturaBalanza paramLecturaBalanza)
    throws AS2Exception;
  
  public abstract void guardarRegistroDesecho(List<DetalleOrdenSalidaMaterialOrdenFabricacion> paramList)
    throws AS2Exception;
  
  public abstract List<Object[]> getReporteRegistroDesecho(OrdenFabricacion paramOrdenFabricacion);
  
  public abstract void actualizarCantidadDesecho(DetalleOrdenSalidaMaterialOrdenFabricacion paramDetalleOrdenSalidaMaterialOrdenFabricacion, BigDecimal paramBigDecimal1, BigDecimal paramBigDecimal2, boolean paramBoolean)
    throws AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.servicio.ServicioDesechoMaterial
 * JD-Core Version:    0.7.0.1
 */