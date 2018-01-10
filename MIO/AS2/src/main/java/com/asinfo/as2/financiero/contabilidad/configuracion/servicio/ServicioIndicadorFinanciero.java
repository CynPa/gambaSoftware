package com.asinfo.as2.financiero.contabilidad.configuracion.servicio;

import com.asinfo.as2.entities.IndicadorFinanciero;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import net.sf.jasperreports.engine.JRDataSource;

@Local
public abstract interface ServicioIndicadorFinanciero
{
  public abstract void guardar(IndicadorFinanciero paramIndicadorFinanciero)
    throws ExcepcionAS2Financiero;
  
  public abstract void eliminar(IndicadorFinanciero paramIndicadorFinanciero);
  
  public abstract IndicadorFinanciero buscarPorId(Integer paramInteger);
  
  public abstract IndicadorFinanciero cargarDetalle(int paramInt);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<IndicadorFinanciero> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<IndicadorFinanciero> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract IndicadorFinanciero calcularValorIndicadorFinanciero(IndicadorFinanciero paramIndicadorFinanciero, Date paramDate1, Date paramDate2)
    throws ArithmeticException;
  
  public abstract JRDataSource getReporteIndicadorFinanciero(List<IndicadorFinanciero> paramList, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioIndicadorFinanciero
 * JD-Core Version:    0.7.0.1
 */