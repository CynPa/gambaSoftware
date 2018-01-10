package com.asinfo.as2.produccion.configuracion.servicio;

import com.asinfo.as2.entities.LecturaBalanza;
import com.asinfo.as2.excepciones.AS2Exception;
import java.math.BigDecimal;
import javax.ejb.Local;

@Local
public abstract interface ServicioMarcacionDispositivo
{
  public abstract void nuevaMarcacion(String paramString1, String paramString2, BigDecimal paramBigDecimal);
  
  public abstract BigDecimal getMarcacion(int paramInt, String paramString);
  
  public abstract void calcularCantidad(LecturaBalanza paramLecturaBalanza);
  
  public abstract void validarCantidad(LecturaBalanza paramLecturaBalanza)
    throws AS2Exception;
  
  public abstract void calcularPesoNeto(int paramInt, LecturaBalanza paramLecturaBalanza, boolean paramBoolean)
    throws AS2Exception;
  
  public abstract BigDecimal[] getCantidades(LecturaBalanza paramLecturaBalanza)
    throws AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.configuracion.servicio.ServicioMarcacionDispositivo
 * JD-Core Version:    0.7.0.1
 */