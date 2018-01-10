package com.asinfo.as2.financiero.contabilidad.configuracion.servicio;

import com.asinfo.as2.entities.CuentaContable;
import com.asinfo.as2.entities.DetalleAsiento;
import com.asinfo.as2.entities.DimensionContable;
import com.asinfo.as2.enumeraciones.GrupoCuenta;
import com.asinfo.as2.enumeraciones.TipoCalculo;
import com.asinfo.as2.enumeraciones.TipoCuentaContable;
import com.asinfo.as2.enumeraciones.TipoEstadoFinanciero;
import com.asinfo.as2.enumeraciones.ValoresCalculo;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioCuentaContable
{
  public abstract void guardar(CuentaContable paramCuentaContable)
    throws ExcepcionAS2Financiero;
  
  public abstract void eliminar(CuentaContable paramCuentaContable);
  
  public abstract CuentaContable buscarPorId(Integer paramInteger);
  
  public abstract List<CuentaContable> buscarPorTipo(TipoCuentaContable paramTipoCuentaContable, int paramInt);
  
  public abstract CuentaContable verificaMovimiento(Integer paramInteger)
    throws ExcepcionAS2Financiero;
  
  public abstract CuentaContable buscarPorCodigo(String paramString, int paramInt)
    throws ExcepcionAS2Financiero;
  
  public abstract List<CuentaContable> buscarPorGrupoNivel(GrupoCuenta paramGrupoCuenta, int paramInt1, int paramInt2);
  
  public abstract BigDecimal obtenerSaldo(Date paramDate1, Date paramDate2, int paramInt1, ValoresCalculo paramValoresCalculo, TipoCalculo paramTipoCalculo, boolean paramBoolean, int paramInt2);
  
  public abstract List<CuentaContable> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<CuentaContable> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract CuentaContable cargarDetalle(int paramInt);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<DetalleAsiento> obtenerCierreCuentas(int paramInt, List<Integer> paramList, Date paramDate1, Date paramDate2)
    throws ExcepcionAS2Financiero;
  
  public abstract List<Object[]> calcularSaldos(Date paramDate1, Date paramDate2, TipoEstadoFinanciero paramTipoEstadoFinanciero, String paramString1, String paramString2, boolean paramBoolean, int paramInt)
    throws ExcepcionAS2Financiero;
  
  public abstract BigDecimal obteneResultadoEjercicio(Date paramDate1, Date paramDate2, String paramString1, String paramString2, boolean paramBoolean, int paramInt1, int paramInt2);
  
  public abstract BigDecimal obtenerSaldo(Date paramDate1, Date paramDate2, int paramInt1, ValoresCalculo paramValoresCalculo, TipoCalculo paramTipoCalculo, boolean paramBoolean, int paramInt2, String paramString1, String paramString2);
  
  public abstract List<DetalleAsiento> obtenerCierreCuentas(List<Integer> paramList, Date paramDate1, Date paramDate2, int paramInt)
    throws ExcepcionAS2Financiero;
  
  public abstract List<CuentaContable> buscarCuentasMovimientoPorCodigo(String paramString, int paramInt)
    throws ExcepcionAS2Financiero;
  
  public abstract List<Object[]> obtenerValores(Date paramDate1, Date paramDate2, int paramInt1, List<CuentaContable> paramList, ValoresCalculo paramValoresCalculo, TipoCalculo paramTipoCalculo, boolean paramBoolean, int paramInt2, String paramString1, String paramString2);
  
  public abstract BigDecimal obtenerSaldoPorConbinacionDimensiones(Date paramDate1, Date paramDate2, CuentaContable paramCuentaContable, ValoresCalculo paramValoresCalculo, TipoCalculo paramTipoCalculo, boolean paramBoolean, int paramInt, DimensionContable paramDimensionContable1, DimensionContable paramDimensionContable2, DimensionContable paramDimensionContable3, DimensionContable paramDimensionContable4, DimensionContable paramDimensionContable5);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable
 * JD-Core Version:    0.7.0.1
 */