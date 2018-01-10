package com.asinfo.as2.financiero.cobros.procesos.servicio;

import com.asinfo.as2.entities.LiquidacionAnticipoCliente;
import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioLiquidacionAnticipoCliente
{
  public abstract LiquidacionAnticipoCliente cargarDetalle(int paramInt);
  
  public abstract void guardar(LiquidacionAnticipoCliente paramLiquidacionAnticipoCliente)
    throws ExcepcionAS2, ExcepcionAS2Financiero;
  
  public abstract void eliminar(LiquidacionAnticipoCliente paramLiquidacionAnticipoCliente);
  
  public abstract LiquidacionAnticipoCliente buscarPorId(Integer paramInteger);
  
  public abstract void contabilizar(LiquidacionAnticipoCliente paramLiquidacionAnticipoCliente)
    throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception;
  
  public abstract void esEditable(LiquidacionAnticipoCliente paramLiquidacionAnticipoCliente)
    throws ExcepcionAS2Financiero;
  
  public abstract void anular(LiquidacionAnticipoCliente paramLiquidacionAnticipoCliente)
    throws ExcepcionAS2, ExcepcionAS2Financiero;
  
  public abstract void actualizarEstado(int paramInt, Estado paramEstado);
  
  public abstract List<LiquidacionAnticipoCliente> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract void validaFechaLiquidacionAnticipo(Date paramDate1, Date paramDate2)
    throws ExcepcionAS2Financiero;
  
  public abstract LiquidacionAnticipoCliente cargarFacturasPendientes(LiquidacionAnticipoCliente paramLiquidacionAnticipoCliente, int paramInt);
  
  public abstract List<LiquidacionAnticipoCliente> getLiquidacionAnticipoClientePorAnticipoCliente(Integer paramInteger);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioLiquidacionAnticipoCliente
 * JD-Core Version:    0.7.0.1
 */