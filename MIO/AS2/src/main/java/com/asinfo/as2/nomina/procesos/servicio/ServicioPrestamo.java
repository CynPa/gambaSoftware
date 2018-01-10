package com.asinfo.as2.nomina.procesos.servicio;

import com.asinfo.as2.entities.DetallePrestamo;
import com.asinfo.as2.entities.Prestamo;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPrestamo
{
  public abstract void guardar(Prestamo paramPrestamo)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract void eliminar(Prestamo paramPrestamo);
  
  public abstract void eliminarDetallesPrestamo(Prestamo paramPrestamo);
  
  public abstract Prestamo buscarPorId(int paramInt);
  
  public abstract List<Prestamo> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Prestamo> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Prestamo cargarDetalle(int paramInt);
  
  public abstract List<DetallePrestamo> generarTablaAmortizacion(Prestamo paramPrestamo);
  
  public abstract void contabilizar(Prestamo paramPrestamo)
    throws ExcepcionAS2, ExcepcionAS2Financiero;
  
  public abstract void tablaAmortizacion(Prestamo paramPrestamo);
  
  public abstract List reporteTablaAmortizacion(Prestamo paramPrestamo);
  
  public abstract void anularAsiento(Prestamo paramPrestamo)
    throws ExcepcionAS2Financiero, AS2Exception;
  
  public abstract void anularPrestamo(Prestamo paramPrestamo)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract void validar(Prestamo paramPrestamo)
    throws ExcepcionAS2Financiero, AS2Exception;
  
  public abstract void validarCuotaCobrada(Prestamo paramPrestamo)
    throws AS2Exception;
  
  public abstract void guardar(Prestamo paramPrestamo, boolean paramBoolean1, boolean paramBoolean2)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.ServicioPrestamo
 * JD-Core Version:    0.7.0.1
 */