package com.asinfo.as2.financiero.cobros.procesos.servicio;

import com.asinfo.as2.entities.Banco;
import com.asinfo.as2.entities.Cobro;
import com.asinfo.as2.entities.DetalleFormaCobro;
import com.asinfo.as2.entities.PlanTarjetaCredito;
import com.asinfo.as2.entities.TarjetaCredito;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioRegistroVoucher
{
  public abstract List<Cobro> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Cobro cargarDetalle(Cobro paramCobro, boolean paramBoolean);
  
  public abstract List<Cobro> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void conciliarVoucher(Banco paramBanco, Cobro paramCobro, DetalleFormaCobro paramDetalleFormaCobro, BigDecimal paramBigDecimal, boolean paramBoolean)
    throws AS2Exception;
  
  public abstract List<DetalleFormaCobro> getVouchersNoCobrados(int paramInt, List<TarjetaCredito> paramList, List<PlanTarjetaCredito> paramList1, List<String> paramList2);
  
  public abstract List<String> getLotesPendientesPorCobrar(int paramInt);
  
  public abstract void esEditable(Cobro paramCobro)
    throws ExcepcionAS2Financiero;
  
  public abstract void guardarRegistroRegistro(Cobro paramCobro)
    throws AS2Exception, ExcepcionAS2;
  
  public abstract void guardarCobroVoucher(Cobro paramCobro)
    throws AS2Exception, ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioRegistroVoucher
 * JD-Core Version:    0.7.0.1
 */