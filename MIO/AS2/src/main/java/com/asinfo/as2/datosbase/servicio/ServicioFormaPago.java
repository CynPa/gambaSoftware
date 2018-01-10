package com.asinfo.as2.datosbase.servicio;

import com.asinfo.as2.entities.FormaPago;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioFormaPago
{
  public abstract void guardar(FormaPago paramFormaPago);
  
  public abstract void eliminar(FormaPago paramFormaPago);
  
  public abstract FormaPago buscarPorId(Integer paramInteger);
  
  public abstract List<FormaPago> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<FormaPago> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  @Deprecated
  public abstract FormaPago buscarPorCodigo(String paramString)
    throws ExcepcionAS2;
  
  public abstract FormaPago cargarDetalle(int paramInt);
  
  public abstract FormaPago devuelveFormaPagoPredeterminada();
  
  public abstract FormaPago buscarFormaPago(Map<String, String> paramMap)
    throws ExcepcionAS2;
  
  public abstract FormaPago formaPagoPorTipoRetencionYPorcentaje(BigDecimal paramBigDecimal, boolean paramBoolean1, boolean paramBoolean2, int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.ServicioFormaPago
 * JD-Core Version:    0.7.0.1
 */