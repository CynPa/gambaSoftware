package com.asinfo.as2.financiero.SRI.configuracion.servicio;

import com.asinfo.as2.entities.sri.PorcentajeImpuestoRentaAnual;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPorcentajeImpuestoRentaAnual
{
  public abstract void guardar(PorcentajeImpuestoRentaAnual paramPorcentajeImpuestoRentaAnual);
  
  public abstract void eliminar(PorcentajeImpuestoRentaAnual paramPorcentajeImpuestoRentaAnual);
  
  public abstract PorcentajeImpuestoRentaAnual buscarPorId(int paramInt);
  
  public abstract List<PorcentajeImpuestoRentaAnual> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<PorcentajeImpuestoRentaAnual> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract BigDecimal obtenerPorcentajePorAnio(int paramInt)
    throws ExcepcionAS2Financiero;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioPorcentajeImpuestoRentaAnual
 * JD-Core Version:    0.7.0.1
 */