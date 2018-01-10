package com.asinfo.as2.financiero.contabilidad.reportes.servicio;

import com.asinfo.as2.entities.EstadoFinanciero;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.enumeraciones.TipoEstadoFinanciero;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.List;

public abstract interface ServicioEstadoFinanciero
{
  public abstract void guardar(EstadoFinanciero paramEstadoFinanciero)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract void eliminar(EstadoFinanciero paramEstadoFinanciero);
  
  public abstract EstadoFinanciero buscarPorId(Integer paramInteger);
  
  public abstract EstadoFinanciero cargarDetalle(int paramInt)
    throws ExcepcionAS2Financiero;
  
  public abstract EstadoFinanciero cargarDatos(EstadoFinanciero paramEstadoFinanciero, String paramString1, String paramString2, int paramInt1, int paramInt2, boolean paramBoolean)
    throws ExcepcionAS2Financiero;
  
  public abstract EstadoFinanciero cargarDatos(int paramInt1, int paramInt2, int paramInt3, int paramInt4, TipoEstadoFinanciero paramTipoEstadoFinanciero, String paramString1, String paramString2, boolean paramBoolean1, int paramInt5, int paramInt6, boolean paramBoolean2, int paramInt7)
    throws ExcepcionAS2Financiero;
  
  public abstract List<Object[]> obtenerReporteEstadoFinancieroComparativoMensual(int paramInt1, int paramInt2, int paramInt3, int paramInt4, TipoEstadoFinanciero paramTipoEstadoFinanciero, String paramString1, String paramString2, boolean paramBoolean1, Sucursal paramSucursal, int paramInt5, boolean paramBoolean2, int paramInt6)
    throws ExcepcionAS2Financiero;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.reportes.servicio.ServicioEstadoFinanciero
 * JD-Core Version:    0.7.0.1
 */