package com.asinfo.as2.financiero.contabilidad.reportes.servicio;

import com.asinfo.as2.entities.DetalleReporteadorVariable;
import com.asinfo.as2.entities.Reporteador;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract interface ServicioReporteador
{
  public abstract List<Reporteador> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void guardar(Reporteador paramReporteador)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract void eliminar(Reporteador paramReporteador)
    throws AS2Exception;
  
  public abstract Reporteador buscarPorId(Integer paramInteger);
  
  public abstract Reporteador cargarDetalle(int paramInt);
  
  public abstract List<Reporteador> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Object[]> getReporte(Reporteador paramReporteador, Date paramDate1, Date paramDate2, int paramInt)
    throws AS2Exception, IllegalArgumentException, ArithmeticException;
  
  public abstract List<DetalleReporteadorVariable> listaVariablesSinCuentaContable(Reporteador paramReporteador)
    throws AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioReporteador
 * JD-Core Version:    0.7.0.1
 */