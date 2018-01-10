package com.asinfo.as2.financiero.contabilidad.configuracion.servicio;

import com.asinfo.as2.entities.DimensionContable;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioDimensionContable
{
  public abstract void guardar(DimensionContable paramDimensionContable)
    throws ExcepcionAS2Financiero;
  
  public abstract void eliminar(DimensionContable paramDimensionContable);
  
  public abstract DimensionContable buscarPorId(Integer paramInteger);
  
  public abstract DimensionContable buscarPorCodigo(String paramString1, String paramString2)
    throws ExcepcionAS2;
  
  public abstract DimensionContable verificaMovimiento(Integer paramInteger)
    throws ExcepcionAS2Financiero;
  
  public abstract DimensionContable cargarDetalle(int paramInt);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<DimensionContable> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<DimensionContable> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<DimensionContable> obtenerListaDimensionPadre(DimensionContable paramDimensionContable);
  
  public abstract List<Object[]> getReporteDimensionContable(int paramInt1, int paramInt2);
  
  public abstract List<DimensionContable> obtenerDimensionContablePorUsuario(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean);
  
  public abstract List<DimensionContable> obtenerListaDimensionPadreRecursivo(DimensionContable paramDimensionContable);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDimensionContable
 * JD-Core Version:    0.7.0.1
 */