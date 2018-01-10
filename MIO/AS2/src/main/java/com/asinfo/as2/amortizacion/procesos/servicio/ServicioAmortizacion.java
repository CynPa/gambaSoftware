package com.asinfo.as2.amortizacion.procesos.servicio;

import com.asinfo.as2.entities.Asiento;
import com.asinfo.as2.entities.InterfazContableProceso;
import com.asinfo.as2.entities.TipoAsiento;
import com.asinfo.as2.entities.amortizacion.Amortizacion;
import com.asinfo.as2.entities.amortizacion.DetalleAmortizacion;
import com.asinfo.as2.entities.amortizacion.TipoAmortizacion;
import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioAmortizacion
{
  public abstract void guardar(Amortizacion paramAmortizacion)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract void eliminar(Amortizacion paramAmortizacion)
    throws ExcepcionAS2;
  
  public abstract Amortizacion buscarPorId(Integer paramInteger)
    throws ExcepcionAS2;
  
  public abstract List<Amortizacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract Amortizacion cargarSecuencia(Amortizacion paramAmortizacion)
    throws ExcepcionAS2;
  
  public abstract Amortizacion cargarDetalle(Amortizacion paramAmortizacion);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract void esEditable(Amortizacion paramAmortizacion)
    throws ExcepcionAS2;
  
  public abstract void esAnulable(Amortizacion paramAmortizacion)
    throws ExcepcionAS2;
  
  public abstract void calcularDetalleAmortizacion(Amortizacion paramAmortizacion);
  
  public abstract Asiento contabilizarMes(int paramInt1, int paramInt2, InterfazContableProceso paramInterfazContableProceso, TipoAsiento paramTipoAsiento)
    throws AS2Exception, ExcepcionAS2;
  
  public abstract void contabilizar(int paramInt1, int paramInt2, Date paramDate)
    throws AS2Exception, ExcepcionAS2;
  
  public abstract List getReporteAmortizacion(Amortizacion paramAmortizacion)
    throws ExcepcionAS2;
  
  public abstract List getReporteAmortizacionResumido(int paramInt1, int paramInt2, TipoAmortizacion paramTipoAmortizacion, int paramInt3);
  
  public abstract List<DetalleAmortizacion> getDetalleAmortizacion(int paramInt1, Date paramDate1, Date paramDate2, Estado paramEstado, int paramInt2);
  
  public abstract void contabilizarInterfazContable(int paramInt1, int paramInt2, InterfazContableProceso paramInterfazContableProceso)
    throws AS2Exception, ExcepcionAS2;
  
  public abstract void anularInterfazContable(InterfazContableProceso paramInterfazContableProceso);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.amortizacion.procesos.servicio.ServicioAmortizacion
 * JD-Core Version:    0.7.0.1
 */